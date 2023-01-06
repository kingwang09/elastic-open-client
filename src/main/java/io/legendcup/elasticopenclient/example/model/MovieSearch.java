package io.legendcup.elasticopenclient.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieSearch {
    private String movieCd;

    private String movieNm;

    private String movieNmEn;

    private Integer prdtYear;

    private String openDt;

    private String typeNm;

    private String prdtStatNm;

    private String nationAlt;

    private String[] genreAlt;

    private String repNationNm;
    private String repGenreNm;

    private List<Map<String, Object>> directors;
    private List<Map<String, Object>> companys;
}
