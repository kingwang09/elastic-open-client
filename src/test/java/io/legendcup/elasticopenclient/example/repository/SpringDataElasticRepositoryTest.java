package io.legendcup.elasticopenclient.example.repository;

import io.legendcup.elasticopenclient.example.model.SpringDataElasticExample;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class SpringDataElasticRepositoryTest {

    @Autowired
    private SpringDataElasticRepository repository;

    @Test
    public void insertTest(){
        SpringDataElasticExample elasticExample = SpringDataElasticExample.builder()
                .message("hello world!!")
                .build();
        repository.save(elasticExample);

        log.debug("insert data: {}", elasticExample);

        Assertions.assertNotNull(elasticExample.getId());
    }
}
