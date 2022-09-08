package io.legendcup.elasticopenclient.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieSearch {
    private String movieNm;
}
