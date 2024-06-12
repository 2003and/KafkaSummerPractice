package uni.dstu.kafka.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import uni.dstu.kafka.dto.JsonDto;
import uni.dstu.kafka.exception.InvalidHttpHeadersException;
import uni.dstu.kafka.service.JsonParserService;

import java.util.Arrays;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParseUtilTest {

    @Autowired
    private JsonParserService parser;

    @Test
    void testHeaderParsing() throws JsonProcessingException {
        final String JSON = """
                {
                  "requestMethod": %s,
                  "url": %s,
                  "body": %s,
                  "httpHeaders": [
                    %s
                  ],
                  "pathAndQuery": %s
                }
                """.formatted(
                quote("GET"),
                quote("https://localhost:8080"),
                null,
                quote("Content-Type: application/json"),
                null
        );
        JsonDto jsonDto = parser.parse(JSON);
        HttpHeaders actualHttpHeaders = new HttpHeaders();
        actualHttpHeaders.add("Content-Type", "application/json");
        assertEquals(ParseUtil.getHttpHeaders(jsonDto), actualHttpHeaders);
    }

    @Test
    void testHeaderParsingFail() throws JsonProcessingException {
        final String JSON = """
                {
                  "requestMethod": %s,
                  "url": %s,
                  "body": %s,
                  "httpHeaders": [
                    %s
                  ],
                  "pathAndQuery": %s
                }
                """.formatted(
                quote("GET"),
                quote("https://localhost:8080"),
                null,
                quote("Content-Type, application/json"),
                null
        );
        JsonDto jsonDto = parser.parse(JSON);
        assertThrows(InvalidHttpHeadersException.class, () -> ParseUtil.getHttpHeaders(jsonDto));
    }

    private String quote(String str) {
        return String.format("\"%s\"", str);
    }

    private String quote(String[] arr) {
        return Arrays.stream(arr).map(this::quote).collect(Collectors.joining(","));
    }

}
