package pl.earnings.calculator.control.net;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.earnings.calculator.control.exception.InvalidCountryCodeException;
import pl.earnings.calculator.control.exchange.CurrencyToPLNConverter;
import pl.earnings.calculator.entity.CountryCode;
import pl.earnings.calculator.entity.CurrencyCode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class NetEarningsCalculatorStrategyProvider {

    private NetEarningsCalculator netEarningsCalculator;
    private CurrencyToPLNConverter currencyToPLNConverter;
    private Map<CountryCode, NetEarningsCalculatorStrategy> map;

    @Autowired
    public NetEarningsCalculatorStrategyProvider(NetEarningsCalculator netEarningsCalculator,
                                                 CurrencyToPLNConverter currencyToPLNConverter) {
        this.netEarningsCalculator = netEarningsCalculator;
        this.currencyToPLNConverter = currencyToPLNConverter;
        initialize();
    }

    private void initialize() {
        map = new HashMap<>();
        map.put(CountryCode.UK, dailyRate -> {
            BigDecimal netEarningAmount = netEarningsCalculator.calculate(dailyRate, new BigDecimal("0.25"), new BigDecimal(600));
            return currencyToPLNConverter.convert(CurrencyCode.GBP, netEarningAmount);
        });
        map.put(CountryCode.DE, dailyRate -> {
            BigDecimal netEarningAmount = netEarningsCalculator.calculate(dailyRate, new BigDecimal("0.20"), new BigDecimal(800));
            return currencyToPLNConverter.convert(CurrencyCode.EUR, netEarningAmount);
        });
        map.put(CountryCode.PL, dailyRate -> netEarningsCalculator.calculate(dailyRate, new BigDecimal("0.19"), new BigDecimal(1200)));
        map.put(null, dailyRate -> {
            throw new InvalidCountryCodeException("Country code not specified");
        });
    }

    public NetEarningsCalculatorStrategy provide(CountryCode code) {
        return Optional.ofNullable(map.get(code)).orElseThrow(() -> new InvalidCountryCodeException("Country code " + code + " not supported"));
    }
}
