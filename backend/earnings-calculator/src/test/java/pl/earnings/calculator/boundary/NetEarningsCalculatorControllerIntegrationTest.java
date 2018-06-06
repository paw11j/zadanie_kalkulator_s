package pl.earnings.calculator.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.earnings.calculator.entity.NetEarningAmount;

import java.math.BigDecimal;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "nbp.api.baseUrl=http://localhost:10000/", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class NetEarningsCalculatorControllerIntegrationTest {


    private static final String EUR_NBP_RESPONSE = "{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"rates\":[{\"no\":\"107/A/NBP/2018\",\"effectiveDate\":\"2018-06-05\",\"mid\":4.2804}]}";
    private static final String GPB_NBP_RESPONSE = "{\"table\":\"A\",\"currency\":\"funt szterling\",\"code\":\"GBP\",\"rates\":[{\"no\":\"107/A/NBP/2018\",\"effectiveDate\":\"2018-06-05\",\"mid\":4.8913}]}";

    private static final Integer MOCK_PORT = 10000;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private ClientAndServer mockServer;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockServer = ClientAndServer.startClientAndServer(MOCK_PORT);
    }

    @After
    public void shutdown() {
        mockServer.stop();
    }

    @Test
    public void shouldCalculateValueForDE_whenInputHasCorrectFormat() throws Exception {

        mockNbpResponse("/EUR", EUR_NBP_RESPONSE);

        String response = callCalculatorAndExpectCorrectlyResponse("100", "DE");

        NetEarningAmount amount = objectMapper.readValue(response, NetEarningAmount.class);
        Assert.assertThat(amount.getAmount(), greaterThan(BigDecimal.valueOf(0)));
        mockServer.verify(HttpRequest.request().withMethod("GET").withPath("/EUR"), VerificationTimes.exactly(1));
    }

    @Test
    public void shouldCalculateValueForUK_whenInputHasCorrectFormat() throws Exception {

        mockNbpResponse("/GBP", GPB_NBP_RESPONSE);

        String response = callCalculatorAndExpectCorrectlyResponse("100", "UK");

        NetEarningAmount amount = objectMapper.readValue(response, NetEarningAmount.class);
        Assert.assertThat(amount.getAmount(), greaterThan(BigDecimal.valueOf(0)));
        mockServer.verify(HttpRequest.request().withMethod("GET").withPath("/GBP"), VerificationTimes.exactly(1));
    }

    @Test
    public void shouldCalculateValueForPL_whenInputHasCorrectFormat() throws Exception {

        String response = callCalculatorAndExpectCorrectlyResponse("100", "PL");

        NetEarningAmount amount = objectMapper.readValue(response, NetEarningAmount.class);
        Assert.assertThat(amount.getAmount(), greaterThan(BigDecimal.valueOf(0)));
        mockServer.verify(HttpRequest.request(), VerificationTimes.exactly(0));
    }

    @Test
    public void shouldResponseBadRequest_whenInputHasOnlyOneDecimalNumber() throws Exception {

        callCalculatcallCalculatorAndExpectBadResponse("100.0", "PL");
        mockServer.verify(HttpRequest.request(), VerificationTimes.exactly(0));
    }

    @Test
    public void shouldResponseBadRequest_whenInputHasMoreThanTwoDecimalNumber() throws Exception {

        callCalculatcallCalculatorAndExpectBadResponse("100.123", "PL");
        mockServer.verify(HttpRequest.request(), VerificationTimes.exactly(0));
    }

    private String callCalculatorAndExpectCorrectlyResponse(String dailyGrossEarnings, String countryCode) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .get("/earnings")
                .param("dailyGrossEarnings", dailyGrossEarnings)
                .param("countryCode", countryCode))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    private void callCalculatcallCalculatorAndExpectBadResponse(String dailyGrossEarnings, String countryCode) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/earnings")
                .param("dailyGrossEarnings", dailyGrossEarnings)
                .param("countryCode", countryCode))
                .andExpect(status().isBadRequest());
    }

    private void mockNbpResponse(String requestPath, String responseBody) {
        mockServer.when(HttpRequest.request()
                .withMethod("GET")
                .withPath(requestPath))
                .respond(HttpResponse.response()
                        .withHeaders(new Header(CONTENT_TYPE, APPLICATION_JSON))
                        .withStatusCode(200)
                        .withBody(new JsonBody(responseBody)));
    }
}