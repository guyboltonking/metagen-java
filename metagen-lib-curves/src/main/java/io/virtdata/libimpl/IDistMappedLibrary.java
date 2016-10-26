package io.virtdata.libimpl;

import com.google.auto.service.AutoService;
import io.virtdata.api.Generator;
import io.virtdata.api.GeneratorLibrary;
import io.virtdata.api.specs.SpecData;
import io.virtdata.core.ResolvedFunction;
import io.virtdata.mappers.mapped_discrete.IDistMapper;
import org.apache.commons.math3.distribution.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@AutoService(GeneratorLibrary.class)
public class IDistMappedLibrary implements GeneratorLibrary {

    private static final Logger logger = LoggerFactory.getLogger(IDistMappedLibrary.class);

    @Override
    public String getLibraryName() {
        return "mapto_idist";
    }

    @Override
    public List<ResolvedFunction> resolveFunctions(String spec) {
        List<ResolvedFunction> resolved = new ArrayList<>();
        SpecData specData = SpecData.forSpec(spec);

        Optional<Class<? extends IntegerDistribution>> functionClass = resolveFunctionClass(spec);

        if (functionClass.isPresent()) {
            String[] generatorArgs = specData.getFuncAndArgs();
            generatorArgs[0] = functionClass.get().getCanonicalName();
            try {
                IDistMapper tcd = new IDistMapper(generatorArgs);
                ResolvedFunction resolvedFunction = new ResolvedFunction(tcd, this);
                resolved.add(resolvedFunction);
            } catch (Exception e) {
                logger.error("Error instantiating generator:" + e.getMessage(), e);
            }
        } else {
            logger.debug("Generator class not found: " + spec);
        }
        return resolved;
    }

    @Override
    public List<String> getGeneratorNames() {
        List<String> genNames = new ArrayList<>();
        return Arrays.stream(DiscreteDistributions.values()).map(Enum::toString).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private Optional<Class<? extends IntegerDistribution>> resolveFunctionClass(String generatorSpec) {
        Class<Generator> generatorClass = null;
        String className = SpecData.forSpec(generatorSpec).getFuncName();
        try {
            DiscreteDistributions ddist = DiscreteDistributions.valueOf(className);
            logger.debug("Located continuous distribution:" + ddist.toString() + " for generator type: " + generatorSpec);
            return Optional.ofNullable(ddist.getDistClass());
        } catch (Exception e) {
            logger.debug("Unable to map continuous distribution class " + generatorSpec);
            return Optional.empty();
        }
    }

    private enum DiscreteDistributions {

        mapto_pascal(PascalDistribution.class),
        mapto_binomial(BinomialDistribution.class),
        mapto_zipf(ZipfDistribution.class),
        mapto_hypergeometric(HypergeometricDistribution.class),
        mapto_uniform_integer(UniformIntegerDistribution.class),
        mapto_enumerated_integer(EnumeratedIntegerDistribution.class),
        mapto_geometric(GeometricDistribution.class),
        mapto_poisson(PoissonDistribution.class);

        private final Class<? extends IntegerDistribution> distClass;

        DiscreteDistributions(Class<? extends IntegerDistribution> clazz) {
            this.distClass = clazz;
        }

        public Class<? extends IntegerDistribution> getDistClass() {
            return distClass;
        }

    }

    @Override
    public boolean canParseSpec(String spec) {
        return SpecData.forOptionalSpec(spec).isPresent();
    }

    @Override
    public Optional<ResolvedFunction> resolveFunction(String spec) {
        List<ResolvedFunction> resolvedFunctions = resolveFunctions(spec);
        if (resolvedFunctions.size()==1) {
            return Optional.of(resolvedFunctions.get(0));
        }
        return Optional.empty();
    }

}
