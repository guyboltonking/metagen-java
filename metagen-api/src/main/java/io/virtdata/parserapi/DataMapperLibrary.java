package io.virtdata.parserapi;

import io.virtdata.core.DataMapperFunctionMapper;
import io.virtdata.core.ResolvedFunction;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * A DataMapperLibrary is an independently loadable library of data mapping functions.
 * </p>
 */
public interface DataMapperLibrary {

    /**
     * <p>Return the name for this data mapper implementation, as it can be used in spec strings, etc.</p>
     *
     * @return Simple lower-case canonical library name
     */
    String getLibraryName();

    /**
     * <p>Find the implementation for and construct an instance of a data mapper function, as described.</p>
     *
     * @param spec A specifier that describes the type and or parameterization of a data mapping function instance.
     * @param <T>  The result type produced by the data mapping function.
     * @return An optional data mapping function instance
     */
    default <T> Optional<DataMapper<T>> getDataMapper(String spec) {
        if (canParseSpec(spec)) {
            Optional<ResolvedFunction> resolvedFunction = resolveFunction(spec);
            return resolvedFunction
                    .map(ResolvedFunction::getFunctionObject)
                    .map(DataMapperFunctionMapper::map);
        }
        return Optional.empty();
    }

    /**
     * DataMapper Libraries are required to test specifier strings in order to determine
     * whether or not the library could possibly find matching functions.
     * This allows varying types of specifiers to be used that are library specific,
     * allowing an ad-hoc form of syntax layering.
     *
     * @param spec a data mapping function spec
     * @return a tagged Specifier option if successful
     */
    boolean canParseSpec(String spec);

    Optional<ResolvedFunction> resolveFunction(String spec);

    /**
     * @param specifier A specifier that describes the type and parameterization of a data mapping function instance.
     *                  The type of specifier will be specific to your library implementation. You can use SpecData by default.
     * @return a list of function instances
     */
    List<ResolvedFunction> resolveFunctions(String specifier);

    /**
     * <p>Get the list of known data mapping function names.</p>
     *
     * @return list of data mapping function names that can be used in specifiers
     */
    List<String> getDataMapperNames();

}