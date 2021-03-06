package io.virtdata.basicsmappers.from_long.to_long;

import io.virtdata.api.ThreadSafeMapper;

import java.util.function.LongUnaryOperator;

@ThreadSafeMapper
public class AddCycleRange implements LongUnaryOperator {

    private final CycleRange cycleRange;

    public AddCycleRange(long maxValue) {
        this(0, maxValue);
    }

    public AddCycleRange(long minValue, long maxValue) {
        this.cycleRange = new CycleRange(minValue,maxValue);
    }

    @Override
    public long applyAsLong(long operand) {
        return operand + cycleRange.applyAsLong(operand);
    }
}
