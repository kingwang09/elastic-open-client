package io.legendcup.elasticopenclient.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntegerTerm {
    @Field(name = "gte")
    private Integer from;
    @Field(name = "lte")
    private Integer to;
}
