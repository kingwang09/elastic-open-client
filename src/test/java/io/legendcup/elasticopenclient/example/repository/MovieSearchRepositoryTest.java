package io.legendcup.elasticopenclient.example.repository;

import io.legendcup.elasticopenclient.example.model.MovieSearch;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class MovieSearchRepositoryTest {

    @Autowired
    private MovieSearchRepository movieSearchRepository;

    @Test
    public void test(){
        List<MovieSearch> list = movieSearchRepository.getAll();
        for(MovieSearch search : list){
            log.debug("data: {}", search);
        }
    }
}
