package io.legendcup.elasticopenclient.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
public class StringTest {

    @Test
    public void test(){
        String value = "20051128";
        String format = "yyyyMMdd";
        LocalDate result = LocalDate.parse(value, DateTimeFormatter.ofPattern(format));
        log.debug("result : {}", result);
    }
}
