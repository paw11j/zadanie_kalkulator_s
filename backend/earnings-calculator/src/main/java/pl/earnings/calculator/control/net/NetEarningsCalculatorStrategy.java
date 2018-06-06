package pl.earnings.calculator.control.net;

import java.math.BigDecimal;

public interface NetEarningsCalculatorStrategy {
    BigDecimal calculate(BigDecimal dailyRate);
}
