package pl.earnings.calculator.control.exchange.nbp;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.earnings.calculator.control.exception.InvalidNbpResponseException;
import pl.earnings.calculator.entity.CurrencyCode;

import java.math.BigDecimal;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest(properties = "nbp.api.baseUrl=http://localhost:10000/", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class NbpExchangeRateServiceIntegrationTest {

    private static final String VALID_NBP_RESPONSE = "{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"rates\":[{\"no\":\"107/A/NBP/2018\",\"effectiveDate\":\"2018-06-05\",\"mid\":4.2804}]}";
    private static final String INVALID_RESPONSE = "{}";
    private static final Integer MOCK_PORT = 10000;


    @Autowired
    private NbpExchangeRateService nbpExchangeRateService;

    private ClientAndServer mockServer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        mockServer = ClientAndServer.startClientAndServer(MOCK_PORT);
    }

    @After
    public void shutdown() {
        mockServer.stop();
    }

    @Test
    public void shouldReturnCorrectly_whenServerRespondValidResponse() {
        mockValidNbpResponse();

        BigDecimal rate = nbpExchangeRateService.getCurrentExchangeRate(CurrencyCode.EUR);

        Assert.assertNotNull(rate);
    }

    @Test
    public void shouldThrow_whenServerRespondInvalidResponse() {
        mockInvalidNbpResponseWithBadJson();

        thrown.expect(InvalidNbpResponseException.class);
        thrown.expectMessage("Response not contains rates");

        nbpExchangeRateService.getCurrentExchangeRate(CurrencyCode.EUR);
    }

    @Test
    public void shouldThrow_whenServerRespondResponseWithNoOkCode() {
        mockInvalidNbpResponseWithBadCode();

        thrown.expect(InvalidNbpResponseException.class);
        thrown.expectMessage("Invalid response code.");

        nbpExchangeRateService.getCurrentExchangeRate(CurrencyCode.EUR);
    }

    @Test
    public void shouldThrow_whenServerRespondResponseWithBadContentType() {
        mockInvalidNbpResponseWithContentType();

        thrown.expect(InvalidNbpResponseException.class);
        thrown.expectMessage("Cannot read response content");

        nbpExchangeRateService.getCurrentExchangeRate(CurrencyCode.EUR);
    }

    private void mockValidNbpResponse() {
        mockNbpResponse(200, VALID_NBP_RESPONSE, APPLICATION_JSON);
    }

    private void mockInvalidNbpResponseWithBadJson() {
        mockNbpResponse(200, INVALID_RESPONSE, APPLICATION_JSON);
    }

    private void mockInvalidNbpResponseWithBadCode() {
        mockNbpResponse(404, INVALID_RESPONSE, APPLICATION_JSON);
    }

    private void mockInvalidNbpResponseWithContentType() {
        mockNbpResponse(200, VALID_NBP_RESPONSE, APPLICATION_XML);
    }

    private void mockNbpResponse(int code, String body, String contentType) {
        mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath("/EUR"))
                .respond(HttpResponse.response()
                        .withHeaders(new Header(CONTENT_TYPE, contentType))
                        .withStatusCode(code)
                        .withBody(new JsonBody(body)));
    }
}