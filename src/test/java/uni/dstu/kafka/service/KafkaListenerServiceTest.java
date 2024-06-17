package uni.dstu.kafka.service;


import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Body;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import wiremock.org.eclipse.jetty.http.HttpStatus;

import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:29092"})
@WireMockTest(httpPort = KafkaListenerServiceTest.PORT)
class KafkaListenerServiceTest {

    protected static final int PORT = 12345;

    @Value("${my-kafka.consumer.topic}")
    private String topic;

    private static final String REQUEST_PATH = "/json/echo";
    private static final String JSON = """
                {
                "requestMethod": "GET",
                "url": "http://localhost:%d%s"
                }
            """.formatted(PORT, REQUEST_PATH);

    private static final String RESPONSE_JSON = """
            {"status": "OK"}
            """;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private CacheService cacheService;

    @Test
    void testEmbeddedKafkaIntegration() {

        stubFor(
                any(anyUrl())
                        .willReturn(
                                aResponse()
                                        .withResponseBody(new Body(RESPONSE_JSON))
                                        .withStatus(HttpStatus.OK_200)
                        )
        );

        kafkaTemplate.send(topic, JSON);

        Awaitility.await()
                .atMost(Duration.ofSeconds(10L))
                .pollInterval(Duration.ofSeconds(1L))
                .until(() -> !cacheService.getCacheEntries().isEmpty());

        WireMock.verify(getRequestedFor(urlEqualTo(REQUEST_PATH)));
    }
}
