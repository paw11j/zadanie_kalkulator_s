package pl.earnings.calculator.control.exchange.nbp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.concurrent.TimeUnit;

@Configuration
public class NbpWebTargetBean {

    private String baseUrl;

    private Integer timeout;
    private ObjectMapper objectMapper;

    @Autowired
    public NbpWebTargetBean(@Value("${nbp.api.baseUrl}") String baseUrl,
                            @Value("${nbp.api.timeout}") Integer timeout,
                            ObjectMapper objectMapper) {
        this.baseUrl = baseUrl;
        this.timeout = timeout;
        this.objectMapper = objectMapper;
    }

    @Bean("nbp-web-target")
    public WebTarget provideNbpWebTarget() {
        return ClientBuilder
                .newBuilder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .build()
                .register(new JacksonJaxbJsonProvider(objectMapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS))
                .target(baseUrl);

    }
}
