package guru.springframework.msscbeerservice.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import guru.springframework.msscbeerservice.web.model.TestBeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@ActiveProfiles("kebab")
@JsonTest
public class JacksonTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerialize() throws JsonProcessingException {
        TestBeerDto beer = TestBeerDto.builder()
                .beerName("Test Beer")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(11111111L)
                .price(new BigDecimal("1000"))
                .createDate(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC))
                .myLocalDate(LocalDate.now())
                .build();

        String json = objectMapper.writeValueAsString(beer);

        System.out.println(json);
    }

    @Test
    void testDeserialize() throws JsonProcessingException {
        String json = """
               {
                    "id":"7646c07b-d42e-46bd-a60b-3f123879c86f",
                    "last-modified-date":null,
                    "beer-name":"Test Beer",
                    "upc":null,"price":null,
                    "my-local-date":"20240303",
                    "create-date":"2024-03-03 11:03:53+0000"
                }
                """;
        TestBeerDto beerFromJson = objectMapper.readValue(json, TestBeerDto.class);

        System.out.println(beerFromJson);
    }

}
