package io.legendcup.elasticopenclient.support.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.legendcup.elasticopenclient.support.model.ElasticResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.InternalAggregations;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.internal.InternalSearchResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class ElasticResultRepository {

    protected final RestHighLevelClient restHighLevelClient;
    protected final ObjectMapper objectMapper;

    protected SearchResponse getSearchResponse(String indexName, SearchSourceBuilder searchSourceBuilder) {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        try {
            return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException | ElasticsearchException e) {
            return this.emptyResponse();
        }
    }

    protected <T> ElasticResult<T> search(final SearchRequest searchRequest, Class<T> clz) {
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            int reqSize = searchRequest.source().size();
            int hitCnt = searchHits.getHits().length;
            boolean isLast = 0 == hitCnt || reqSize > hitCnt;
            return ElasticResult.<T>builder()
                    .cursor(isLast ? null : searchHits.getHits()[hitCnt - 1].getSortValues())
                    .isLast(isLast)
                    .list(convertList(searchResponse.getHits(), clz))
                    .total(getTotalCount(searchResponse))
                    .build();
        } catch (IOException | ElasticsearchException e) {
            log.error("search {} error {}", clz.getSimpleName(), e);
            return ElasticResult.empty();
        }
    }

    protected <T> List<T> convertList(final SearchHits searchHits, Class<T> clz) {
        if (searchHits == null || searchHits.getHits().length == 0) {
            return Collections.emptyList();
        }

        List<T> list = new LinkedList<>();
        for (SearchHit hit : searchHits.getHits()) {
            T data = objectMapper.convertValue(hit.getSourceAsMap(), clz);
            list.add(data);
        }
        return list;
    }


    protected long getTotalCount(SearchResponse res) {
        if (res == null || res.getHits() == null) {
            return 0;
        }
        return res.getHits().getTotalHits().value;
    }

    protected SearchResponse emptyResponse() {
        SearchHits searchHits = new SearchHits(new SearchHit[0], new TotalHits(0L, TotalHits.Relation.EQUAL_TO), Float.NaN);
        InternalSearchResponse internalSearchResponse = new InternalSearchResponse(searchHits,
                InternalAggregations.EMPTY, null, null, false, null, 0);
        return new SearchResponse(internalSearchResponse, null, 0, 0, 0, 0,
                ShardSearchFailure.EMPTY_ARRAY, new SearchResponse.Clusters(1, 0, 1));
    }

}
