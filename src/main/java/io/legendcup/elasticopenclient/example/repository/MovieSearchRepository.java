package io.legendcup.elasticopenclient.example.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.legendcup.elasticopenclient.example.model.MovieSearch;
import io.legendcup.elasticopenclient.support.model.ElasticResult;
import io.legendcup.elasticopenclient.support.repository.ElasticResultRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieSearchRepository extends ElasticResultRepository {
    public MovieSearchRepository(RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) {
        super(restHighLevelClient, objectMapper);
    }

    public List<MovieSearch> getAll(){
        SearchRequest searchRequest = new SearchRequest("movie_search");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        ElasticResult<MovieSearch> result = search(searchRequest, MovieSearch.class);
        return (List<MovieSearch>) result.getList();
    }
}
