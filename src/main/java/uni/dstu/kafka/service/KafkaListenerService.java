package uni.dstu.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import uni.dstu.kafka.dto.JsonDto;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaListenerService {
    private final List<JsonDto> cache = new LinkedList<>();
    private final JsonParserService parser;
    private final HttpClientService httpClient;

    @KafkaListener(topics = "${my-kafka.consumer.topic}", groupId = "${my-kafka.consumer.group-id}")
    public void listen(String json) {
        try {
            JsonDto jsonDto = parser.parse(json);
            httpClient.request(jsonDto);
            cache.add(jsonDto);
            if (cache.size() > 3) {
                cache.removeFirst();
            }
        } catch (JsonProcessingException e) {
            log.error("Error processing kafka json", e);
        } catch (RestClientException e) {
            log.error("Error sending request", e);
        }
    }
}
