package uni.dstu.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uni.dstu.kafka.dto.JsonDto;

@Service
@RequiredArgsConstructor
public class JsonParser {

    private final ObjectMapper mapper;

    public String parse(String json) throws JsonProcessingException {
        JsonDto jsonDto = mapper.readValue(json, JsonDto.class);

        StringBuilder result = new StringBuilder();

        result.append(jsonDto.getRequestType());
        result.append(' ');
        result.append(jsonDto.getUrl());

        if (jsonDto.getPathAndQuery() != null) {
            result.append(jsonDto.getPathAndQuery());
        }
        return result.toString();
    }
}
