package io.legendcup.elasticopenclient.example.model;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "spring-data-movie-search", createIndex = false, dynamic = Dynamic.FALSE)
public class SpringDataMovieSearch {
    @Id
    private String id;

    private String movieName;

    private Integer playtime;

    private Boolean isDeleted;

    private String movieComment;

    private String showDate;

    @Field(type = FieldType.Integer_Range)
    private IntegerTerm productRange;

    private String filmLocation;

    private String ipAddress;


    //nested
    //private String actors;

    //companies
    //private Company companies;

    //directors

    public void setShowDate(LocalDateTime localDateTime){
        this.showDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public LocalDateTime getShowDate(){
        if(this.showDate != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(this.showDate, formatter);
            return dateTime;
        }
        return null;
    }

}
