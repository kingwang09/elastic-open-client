package io.legendcup.elasticopenclient.search.model;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Dynamic;

import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "hashtag-project-count", createIndex = true, dynamic = Dynamic.TRUE)
public class InProgressProjectCountByHashTag {
    @Id
    private String id;

    private String hashTag;

    private Long count;
}
