package io.virtdata.libimpl.continuous;

import io.virtdata.api.DataMapper;
import org.apache.commons.math4.stat.descriptive.DescriptiveStatistics;
import org.assertj.core.data.Offset;
import org.testng.annotations.Test;

import java.util.Formatter;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@Test
public class RealDistributionsValuesTest {


    @Test
    public void testComputedNormal() {
        RunData runData = iterateMapper(new RealDistributions().getDoubleDataMapper(
                "compute_normal(10.0,2.0)").orElse(null), 1000000);
        System.out.println(runData.toString());
        assertThat(runData.getFractionalPercentile(0.5D))
                .isCloseTo(10.0D, Offset.offset(0.01D));
        assertThat(runData.getFractionalPercentile(0.4D))
                .isCloseTo(9.49D, Offset.offset(0.01D));
        assertThat(runData.getFractionalPercentile(0.3D))
                .isCloseTo(8.95D, Offset.offset(0.01D));
    }

    @Test
    public void testInterpolatedNormal() {
        RunData runData = iterateMapper(new RealDistributions().getDoubleDataMapper(
                "interpolate_normal(10.0,2.0)").orElse(null), 1000000);
        System.out.println(runData.toString());
        assertThat(runData.getFractionalPercentile(0.5D))
                .isCloseTo(10.0D, Offset.offset(0.01D));
        assertThat(runData.getFractionalPercentile(0.4D))
                .isCloseTo(9.49D, Offset.offset(0.01D));
        assertThat(runData.getFractionalPercentile(0.3D))
                .isCloseTo(8.95D, Offset.offset(0.01D));
    }

    @Test
    public void testComputedUniform() {
        RunData runData = iterateMapper(new RealDistributions().getDoubleDataMapper("compute_uniform_real(0.0,100.0)").orElse(null), 1000000);
        assertThat(runData.getFractionalPercentile(0.33D))
                .isCloseTo(33.33D, Offset.offset(1.0D));
        assertThat(runData.getFractionalPercentile(0.5D))
                .isCloseTo(50.0D, Offset.offset(1.0D));
        assertThat(runData.getFractionalPercentile(0.78D))
                .isCloseTo(78.0D, Offset.offset(1.0D));
        System.out.println(runData.toString());
    }

    @Test
    public void testInterpolatedUniform() {
        RunData runData = iterateMapper(
                new RealDistributions().getDoubleDataMapper("interpolate_uniform_real(0.0,100.0)").orElse(null), 1000000);
        assertThat(runData.getFractionalPercentile(0.33D))
                .isCloseTo(33.33D, Offset.offset(1.0D));
        assertThat(runData.getFractionalPercentile(0.5D))
                .isCloseTo(50.0D, Offset.offset(1.0D));
        assertThat(runData.getFractionalPercentile(0.78D))
                .isCloseTo(78.0D, Offset.offset(1.0D));
        System.out.println(runData.toString());
    }

    @Test
    public void testInterpolatedMappedUniform() {
        DataMapper<Double> mapper = new RealDistributions().getDoubleDataMapper("interpolate_mapto_uniform_real(0.0,100.0)").orElse(null);
        RunData runData = iterateMapper(mapper,10000000);
        assertThat(runData.getFractionalPercentile(0.999D))
                .isCloseTo(0.0D, Offset.offset(1.0D));

        assertThat(mapper.get(Long.MAX_VALUE)).isCloseTo(100.0D, Offset.offset(1.0D));

    }

    private RunData iterateMapper(DataMapper<Double> mapper, int iterations) {
        assertThat(mapper).isNotNull();

        double samples[] = new double[iterations];

        long time_generating = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            samples[i] = mapper.get(i);
        }
        long time_generated = System.nanoTime();

        double ms = (double) (time_generated - time_generating) / 1000000.0D;
        return new RunData(iterations, samples, ms);
    }

    private static class RunData {
        public String spec;
        public int iterations;
        public double[] samples;
        public double ms;

        public RunData(int iterations, double[] samples, double ms) {

            this.iterations = iterations;
            this.samples = samples;
            this.ms = ms;
        }
        private DescriptiveStatistics stats;
        private DescriptiveStatistics getStats() {
            if (stats==null) {
                stats = new DescriptiveStatistics(samples);
            }
            return stats;
        }
        public double getFractionalPercentile(double point) {
            return getStats().getPercentile(point*100.0D);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            DescriptiveStatistics s1 = new DescriptiveStatistics(samples);
            Formatter f = new Formatter(sb, Locale.US);
            f.format("exec time %5fms\n", ms);
            f.format("iterations: %d\n", iterations);
            f.format("iterations/ms: %5f\n", (iterations/ms));
            for (int i = 10; i < 100; i += 10) {
                double pctile = (double) i;
                f.format("pctile %4d  %4f\n", i, s1.getPercentile(pctile));
            }
            return sb.toString();
        }

    }

}