package uni.dstu.kafka.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uni.dstu.kafka.dto.JsonDto;
import uni.dstu.kafka.util.ParseUtil;

@Service
public class HttpClientService {
    public String request(JsonDto jsonDto) {

        RestTemplate restTemplate = new RestTemplate();

        HttpMethod httpMethod = HttpMethod.valueOf(jsonDto.getRequestMethod());

        String url = (jsonDto.getPathAndQuery() != null)
                ? jsonDto.getUrl() + jsonDto.getPathAndQuery()
                : jsonDto.getUrl();

        HttpHeaders headers = ParseUtil.getHttpHeaders(jsonDto);

        HttpEntity<Object> entity = new HttpEntity<>(jsonDto.getBody(), headers);

        return restTemplate.exchange(url, httpMethod, entity, String.class).getBody();
    }

}
