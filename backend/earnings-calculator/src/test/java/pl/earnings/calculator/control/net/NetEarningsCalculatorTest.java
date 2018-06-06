package pl.earnings.calculator.control.net;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NetEarningsCalculatorTest {

    private static final int DAYS_IN_MONTH = 22;

    private NetEarningsCalculator calculator = new NetEarningsCalculator(DAYS_IN_MONTH);

    @Test
    public void shouldCalculateCorrectlyValue_whenDailyRateIsInteger() {
        BigDecimal result = calculator.calculate(new BigDecimal(20), new BigDecimal("0.10"), new BigDecimal(150));
        Assert.assertEquals(result, new BigDecimal("246.00"));
    }

    @Test
    public void shouldCalculateCorrectlyValue_whenDailyRateIsFloat() {
        BigDecimal result = calculator.calculate(new BigDecimal("37.37"), new BigDecimal("0.14"), new BigDecimal(123));
        Assert.assertEquals(result.setScale(2, RoundingMode.HALF_UP), new BigDecimal("584.04"));
    }

    @Test
    public void shouldCalculateCorrectlyValue_whenAllParametersAreZero() {
        BigDecimal result = calculator.calculate(new BigDecimal(0), new BigDecimal(0), new BigDecimal(0));
        Assert.assertEquals(result, new BigDecimal(0));
    }

    @Test
    public void shouldCalculateCorrectlyValue_whenDailyRateIsZero() {
        BigDecimal result = calculator.calculate(new BigDecimal(0), new BigDecimal("0.10"), new BigDecimal(100));
        Assert.assertEquals(result, new BigDecimal("-100.00"));
    }

    @Test
    public void shouldCalculateCorrectlyValue_whenIncomeTaxIsZero() {
        BigDecimal result = calculator.calculate(new BigDecimal(1000), new BigDecimal(0), new BigDecimal(100));
        Assert.assertEquals(result, new BigDecimal(21900));
    }

    @Test
    public void shouldCalculateCorrectlyValue_whenFixedCostsAreZero() {
        BigDecimal result = calculator.calculate(new BigDecimal(1000), new BigDecimal("0.15"), new BigDecimal(0));
        Assert.assertEquals(result, new BigDecimal("18700.00"));
    }
}