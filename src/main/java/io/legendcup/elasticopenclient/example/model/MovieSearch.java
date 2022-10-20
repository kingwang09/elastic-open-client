package io.legendcup.elasticopenclient.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieSearch {
    private String movieCode;

    private String movieName;

    private LocalDateTime showDate;
}
