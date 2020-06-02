/*
 * Copyright (c) 2018 Pavlo Sidelov
 */

package com.flax.finplat.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Calculator executes the base math operations for engine:
 * - addition
 * - subtraction
 * - multiplication
 * - division
 * - normalization
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Calculator {

    private static final int GATESCALE = 2;
    private static final int DEFSCALE = 4;
    private static final int PERCENTSCALE = 6;
    private static final int RATESCALE = 6;
    private static final RoundingMode defRoundingMode = RoundingMode.HALF_UP;
    private static final BigDecimal minusOne = normalize(BigDecimal.valueOf(-1L));
    private static final BigDecimal minCommissionValue = normalize(BigDecimal.valueOf(0.0001));

    public static final BigDecimal zero = normalize(BigDecimal.ZERO);
    public static final BigDecimal one = normalize(BigDecimal.ONE);
    public static final BigDecimal oneHundred = normalize(BigDecimal.valueOf(100L));

    public static BigDecimal add(BigDecimal o1, BigDecimal o2) {
        return normalize(o1.add(o2));
    }

    public static BigDecimal addPercent(BigDecimal o1, BigDecimal o2) {
        return o1.add(o2).setScale(PERCENTSCALE, defRoundingMode);
    }

    public static BigDecimal subtract(BigDecimal o1, BigDecimal o2) {
        return normalize(o1.subtract(o2));
    }

    public static BigDecimal divide(BigDecimal o1, BigDecimal o2) {
        return normalize(o1.divide(o2, DEFSCALE, defRoundingMode));
    }

    public static BigDecimal multiply(BigDecimal o1, BigDecimal o2) {
        return normalize(o1.multiply(o2));
    }

    public static BigDecimal normalize(@NonNull BigDecimal o1) {
        return o1.setScale(DEFSCALE, defRoundingMode);
    }

    public static BigDecimal normalize(@NonNull String amount) {
        return normalize(new BigDecimal(amount));
    }

    public static BigDecimal normalize(double o1) {
        return normalize(BigDecimal.valueOf(o1));
    }

    public static BigDecimal normalizeForGate(String source) {
        return normalizeForGate(new BigDecimal(source));
    }

    public static BigDecimal normalizeForGate(BigDecimal o1) {
        return o1.setScale(GATESCALE, defRoundingMode);
    }

    public static BigDecimal negative(BigDecimal o1) {
        return multiply(o1, minusOne);
    }

    /**
     * Divide percent value (like 1%) by 100 to receive actual value (like 0.01) tobe used in further calculations
     *
     * @param percentValue percent value
     * @return plain value
     */
    public static BigDecimal percentToPlainValue(BigDecimal percentValue) {
        return divide(percentValue, oneHundred);
    }

    /**
     * Set up scale for plain value that represents percents.
     * Example: 0.0005 plain value = 0.05%.
     *
     * @param percentValue plain value representing a percentage
     * @return normalized value
     */
    public static BigDecimal normalizePercent(BigDecimal percentValue) {
        return percentValue.setScale(PERCENTSCALE, defRoundingMode);
    }

    public static BigDecimal normalizeRate(BigDecimal rateValue) {
        return rateValue.setScale(RATESCALE, defRoundingMode);
    }

    public static BigDecimal normalizeCommission(@NonNull BigDecimal val) {
        if (BigDecimal.ZERO.compareTo(val) == 0) {
            return zero;
        }
        if (val.compareTo(BigDecimal.ZERO) > 0 && val.compareTo(minCommissionValue) < 0) {
            return minCommissionValue;
        }
        return normalize(val);
    }

    /**
     * such as commission normalize to Up useful amount should round down;
     *
     * @param val - target calculated value;
     * @return - normalize amount;
     */
    public static BigDecimal normalizeUsefulAmount(BigDecimal val) {
        return val.setScale(DEFSCALE, RoundingMode.HALF_DOWN);
    }

    public static BigDecimal divideForUsefulAmount(BigDecimal o1, BigDecimal o2) {
        return o1.divide(o2, 4, RoundingMode.HALF_DOWN);
    }

    public static BigDecimal reverse(BigDecimal value) {
        return divide(BigDecimal.ONE, value);
    }

    public static MathContext getMathContext() {
        return new MathContext(DEFSCALE, defRoundingMode);
    }


    public static BigInteger normalizeToFraction(BigDecimal amount) {
        return normalizeForGate(amount)
                .movePointRight(2)
                .toBigInteger();
    }

    public static BigDecimal normalizeFromFraction(BigDecimal amount) {
        return normalizeForGate(
                amount.movePointLeft(2)
        );
    }

    public static BigDecimal calculatePercent(BigDecimal value, BigDecimal percentValue) {
        return normalize(
                value.multiply(
                        percentValue.movePointLeft(2)
                )
        );
    }
}
