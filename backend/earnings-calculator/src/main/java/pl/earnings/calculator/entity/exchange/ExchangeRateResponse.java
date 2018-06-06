package pl.earnings.calculator.entity.exchange;

import pl.earnings.calculator.entity.CurrencyCode;

import java.util.List;

public class ExchangeRateResponse {
    private List<ExchangeRate> rates;
    private CurrencyCode code;

    public ExchangeRateResponse() {
    }

    public List<ExchangeRate> getRates() {
        return rates;
    }

    public void setRates(List<ExchangeRate> rates) {
        this.rates = rates;
    }

    public CurrencyCode getCode() {
        return code;
    }

    public void setCode(CurrencyCode code) {
        this.code = code;
    }
}
