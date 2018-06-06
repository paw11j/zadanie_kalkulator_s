package pl.earnings.calculator.control.exchange.nbp;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.earnings.calculator.control.exception.InvalidNbpResponseException;
import pl.earnings.calculator.control.exchange.ExchangeRateService;
import pl.earnings.calculator.entity.CurrencyCode;
import pl.earnings.calculator.entity.exchange.ExchangeRateResponse;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Optional;

@Component
public class NbpExchangeRateService implements ExchangeRateService {

    private final WebTarget nbpWebTarget;

    public NbpExchangeRateService(@Qualifier("nbp-web-target") WebTarget nbpWebTarget) {
        this.nbpWebTarget = nbpWebTarget;
    }

    @Override
    public BigDecimal getCurrentExchangeRate(CurrencyCode code) {
        Response response = callRequestOrThrow(code);
        ExchangeRateResponse exchangeRateResponse = extractExchangeRateResponseOrThrow(response);
        return Optional.ofNullable(exchangeRateResponse.getRates()).orElseThrow(() -> new InvalidNbpResponseException("Response not contains rates"))
                .stream()
                .findFirst()
                .orElseThrow(() -> new InvalidNbpResponseException("Response not contains rates"))
                .getMid();
    }

    private Response callRequestOrThrow(CurrencyCode code) {
        Response response = nbpWebTarget.path("/{code}")
                .resolveTemplate("code", code)
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        if (Response.Status.OK != response.getStatusInfo().toEnum()) {
            throw new InvalidNbpResponseException(
                    "Invalid response code. Message:" + response.readEntity(String.class));
        }
        return response;
    }

    private ExchangeRateResponse extractExchangeRateResponseOrThrow(Response response) {
        try {
            return response.readEntity(ExchangeRateResponse.class);
        } catch (ProcessingException | IllegalStateException e) {
            throw new InvalidNbpResponseException("Cannot read response content", e);
        }
    }
}
