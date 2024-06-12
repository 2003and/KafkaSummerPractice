package uni.dstu.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uni.dstu.kafka.dto.JsonDto;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JsonParserServiceTest {

    @Autowired
    private JsonParserService parser;

    @Test
    void testJsonParsingMandatoryFields() {
        assertThrows(
                JsonMappingException.class,
                () -> parser.parse("{}")
        );

        assertThrows(
                JsonMappingException.class,
                () -> parser.parse("""
                        {"requestMethod": %s}
                        """.formatted(quote("GET")))
        );

        assertDoesNotThrow(
                () -> parser.parse("""
                        {
                          "requestMethod": %s,
                          "url": %s
                        }
                        """.formatted(quote("GET"), quote("https://localhost:8080")))
        );
    }

    @Test
    void testJsonParsing() throws JsonProcessingException {
        final String JSON = """
                {
                  "requestMethod": %s,
                  "url": %s,
                  "body": %s,
                  "httpHeaders": %s,
                  "pathAndQuery": %s
                }
                """.formatted(
                quote("GET"),
                quote("https://localhost:8080"),
                null,
                null,
                null
                );
        assertEquals(parser.parse(JSON), new JsonDto("GET", "https://localhost:8080"));
    }

    private String quote(String str) {
        return String.format("\"%s\"", str);
    }

    private String quote(String[] arr) {
        return Arrays.stream(arr).map(this::quote).collect(Collectors.joining(","));
    }

}
