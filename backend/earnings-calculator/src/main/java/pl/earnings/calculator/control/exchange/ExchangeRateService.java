package pl.earnings.calculator.control.exchange;

import pl.earnings.calculator.entity.CurrencyCode;

import java.math.BigDecimal;

public interface ExchangeRateService {
    BigDecimal getCurrentExchangeRate(CurrencyCode code);
}
