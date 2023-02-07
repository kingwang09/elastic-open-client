package io.legendcup.elasticopenclient.search.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.legendcup.elasticopenclient.search.model.IntegrateCampaign;
import io.legendcup.elasticopenclient.support.model.ElasticResult;
import io.legendcup.elasticopenclient.support.repository.ElasticResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public ElasticResult<IntegrateCampaign> findByKeywordInProgressCount(String keyword){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(QueryBuilders.termsQuery("campaignStatus.keyword", "IN_PROGRESS", "SOON_OPEN"));

        if (StringUtils.isNotBlank(keyword)) {
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(makeUnigramText(keyword))
                    .fields(getSearchForUniGramFields())
                    .type(MultiMatchQueryBuilder.Type.PHRASE)
                    .operator(Operator.AND)
                    .tieBreaker(1.0f));
        }

        searchSourceBuilder.size(10000);
        searchSourceBuilder.query(boolQueryBuilder);

        SearchRequest searchRequest = new SearchRequest(IntegrateCampaign.INDEX_ALIAS);
        searchRequest.source(searchSourceBuilder);

        ElasticResult<IntegrateCampaign> result = search(searchRequest, IntegrateCampaign.class);
        return result;
    }

    public Map<String, Float> getSearchForUniGramFields() {
        Map<String, Float> fieldMap = new HashMap<>();
        fieldMap.put("titleUniGram", 1.0f);
        fieldMap.put("makerUniGram", 1.0f);
        fieldMap.put("hashTagUniGram", 1.0f);
        fieldMap.put("coreMessageUniGram", 1.0f);
        return fieldMap;
    }


    public String makeUnigramText(String keyword){
        List<String> tokens = Arrays.stream(keyword.split("")).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        log.debug("tokens: {}", tokens);
        return tokens.stream().collect(Collectors.joining(" "));
    }


}
