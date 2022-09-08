package io.legendcup.elasticopenclient.example.model;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Dynamic;

import javax.persistence.Id;


@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "spring-data-example", createIndex = true, dynamic = Dynamic.TRUE)
public class SpringDataElasticExample {
    @Id
    private String id;

    private String message;

    //다양한 타입 테스트 필요
}
