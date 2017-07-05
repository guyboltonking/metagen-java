package io.virtdata.int_int;

import io.virtdata.api.ThreadSafeMapper;

import java.util.function.IntUnaryOperator;

@ThreadSafeMapper
public class AddHashRange implements IntUnaryOperator {

    private final io.virtdata.int_int.HashRange hashRange;

    public AddHashRange(int maxValue) {
        this(0, maxValue);
    }

    public AddHashRange(int minValue, int maxValue) {
        this.hashRange = new io.virtdata.int_int.HashRange(minValue, maxValue);
    }

    @Override
    public int applyAsInt(int operand) {
        return operand + hashRange.applyAsInt(operand);
    }
}
