package io.legendcup.elasticopenclient.example.repository;

import io.legendcup.elasticopenclient.example.model.IntegerTerm;
import io.legendcup.elasticopenclient.example.model.SpringDataMovieSearch;
import io.legendcup.elasticopenclient.support.repository.SpringDataMovieSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@SpringBootTest
public class SpringDataMovieSearchRepositoryTest {

    @Autowired
    private SpringDataMovieSearchRepository repository;

    @Test
    public void saveTest(){
        SpringDataMovieSearch movieSearch = SpringDataMovieSearch.builder()
                .movieName("공조2")
                .movieComment("공조2를 보고 싶다.")
                .ipAddress("127.0.0.1")
                .showDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .playtime(180)
                .productRange(IntegerTerm.builder()
                        .from(10)
                        .to(100)
                        .build())
                .isDeleted(false)
                .build();

        repository.save(movieSearch);

        log.debug("movie-search data: {}", movieSearch);

        Assertions.assertNotNull(movieSearch.getId());

    }
}
