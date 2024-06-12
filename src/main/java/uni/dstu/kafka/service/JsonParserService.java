package uni.dstu.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uni.dstu.kafka.dto.JsonDto;

@Service
@RequiredArgsConstructor
public class JsonParserService {

    private final ObjectMapper mapper;

    public JsonDto parse(String json) throws JsonProcessingException {
        JsonDto jsonDto = mapper.readValue(json, JsonDto.class);
        return jsonDto;
    }
}
