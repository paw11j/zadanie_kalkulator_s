package pl.earnings.calculator.control.net;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class NetEarningsCalculator {

    private BigDecimal daysInMonth;

    @Autowired
    public NetEarningsCalculator(@Value("${configuration.daysInMonth}") Integer daysInMonth) {
        this.daysInMonth = BigDecimal.valueOf(daysInMonth);
    }

    BigDecimal calculate(BigDecimal dailyRate, BigDecimal incomeTax, BigDecimal fixedCosts) {
        BigDecimal monthlyRate = dailyRate.multiply(daysInMonth);
        BigDecimal incomeTaxAmount = monthlyRate.multiply(incomeTax);
        return monthlyRate.subtract(incomeTaxAmount).subtract(fixedCosts);
    }
}
