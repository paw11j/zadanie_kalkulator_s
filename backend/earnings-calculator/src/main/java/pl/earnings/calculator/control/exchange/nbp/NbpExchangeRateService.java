package pl.earnings.calculator.control.exchange.nbp;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.earnings.calculator.control.exception.InvalidNbpResponse;
import pl.earnings.calculator.control.exchange.ExchangeRateService;
import pl.earnings.calculator.entity.CurrencyCode;
import pl.earnings.calculator.entity.exchange.ExchangeRateResponse;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Component
public class NbpExchangeRateService implements ExchangeRateService {

    private final WebTarget nbpWebTarget;

    public NbpExchangeRateService(@Qualifier("nbp-web-target") WebTarget nbpWebTarget) {
        this.nbpWebTarget = nbpWebTarget;
    }

    @Override
    public BigDecimal getCurrentExchangeRate(CurrencyCode code) {
        ExchangeRateResponse response = nbpWebTarget.path("/{code}")
                .resolveTemplate("code", code)
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(ExchangeRateResponse.class);

        return response.getRates()
                .stream()
                .findFirst()
                .orElseThrow(() -> new InvalidNbpResponse("Response not contains rates"))
                .getMid();
    }
}
