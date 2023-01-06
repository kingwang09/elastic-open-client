package io.legendcup.elasticopenclient.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.legendcup.elasticopenclient.example.model.MovieSearch;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

//git history가 개인계정으로 남도록
@Slf4j
@SpringBootTest
public class ElasticHighLevelClientTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test() throws IOException {
        SearchRequest searchRequest = new SearchRequest("movie_search_sample");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        //search execute
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //search response
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            // do something with the SearchHit
            MovieSearch data = objectMapper.convertValue(hit.getSourceAsMap(), MovieSearch.class);
            log.debug("data: {}", data);
        }
    }

    @Test
    public void movieTest() throws IOException {
        SearchRequest searchRequest = new SearchRequest("movie_search");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.filter(QueryBuilders.termQuery("movieNm", "곤지암"));

        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);

        //search execute
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //search response
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            // do something with the SearchHit
            MovieSearch data = objectMapper.convertValue(hit.getSourceAsMap(), MovieSearch.class);
            log.debug("data: {}", data);
        }
    }
}
