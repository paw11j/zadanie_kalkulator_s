package pl.earnings.calculator.control;

import org.springframework.stereotype.Service;
import pl.earnings.calculator.control.exchange.validator.AmountValidator;
import pl.earnings.calculator.control.net.NetEarningsCalculatorStrategyProvider;
import pl.earnings.calculator.entity.CountryCode;
import pl.earnings.calculator.entity.NetEarningAmount;

import java.math.BigDecimal;

@Service
public class NetEarningsCalculatorService {

    private final NetEarningsCalculatorStrategyProvider provider;
    private final AmountValidator amountValidator;

    public NetEarningsCalculatorService(NetEarningsCalculatorStrategyProvider provider,
                                        AmountValidator amountValidator) {
        this.provider = provider;
        this.amountValidator = amountValidator;
    }

    public NetEarningAmount calculateNetEarningAsPLN(BigDecimal dailyGrossEarnings, CountryCode countryCode) {
        amountValidator.validate(dailyGrossEarnings);
        BigDecimal netMonthlyEarnings = provider.provide(countryCode).calculate(dailyGrossEarnings);
        return new NetEarningAmount(netMonthlyEarnings);
    }
}
