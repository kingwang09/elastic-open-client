package io.legendcup.elasticopenclient.search.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.legendcup.elasticopenclient.search.model.IntegrateCampaign;
import io.legendcup.elasticopenclient.support.model.ElasticResult;
import io.legendcup.elasticopenclient.support.repository.ElasticResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SearchService extends ElasticResultRepository{

    public SearchService(RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) {
        super(restHighLevelClient, objectMapper);
    }

    public ElasticResult<IntegrateCampaign> findInProgress(){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(QueryBuilders.termsQuery("campaignStatus.keyword", "IN_PROGRESS", "SOON_OPEN"));
        searchSourceBuilder.size(10000);
        searchSourceBuilder.query(boolQueryBuilder);

        SearchRequest searchRequest = new SearchRequest(IntegrateCampaign.INDEX_ALIAS);
        searchRequest.source(searchSourceBuilder);

        ElasticResult<IntegrateCampaign> result = search(searchRequest, IntegrateCampaign.class);
        return result;
    }

}
