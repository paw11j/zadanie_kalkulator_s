package pl.earnings.calculator.control.exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.earnings.calculator.entity.CurrencyCode;

import java.math.BigDecimal;

@Component
public class CurrencyToPLNConverter {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public CurrencyToPLNConverter(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    public BigDecimal convert(CurrencyCode code, BigDecimal amount) {
        return exchangeRateService.getCurrentExchangeRate(code).multiply(amount);
    }
}
