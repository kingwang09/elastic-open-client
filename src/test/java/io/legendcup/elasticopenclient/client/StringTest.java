package io.legendcup.elasticopenclient.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Test
    public void index(){
        List<Integer> index = new ArrayList<>();
        index.add(1);
        index.add(2);
        index.add(3);
        index.add(4);
        index.add(5);
        index.add(0, 11);
        log.debug("result: {}", index);
    }
}
