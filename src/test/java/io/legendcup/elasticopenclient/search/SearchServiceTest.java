package io.legendcup.elasticopenclient.search;

import io.legendcup.elasticopenclient.search.model.IntegrateCampaign;
import io.legendcup.elasticopenclient.search.service.SearchService;
import io.legendcup.elasticopenclient.support.model.ElasticResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
public class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @Test
    public void test(){
        ElasticResult<IntegrateCampaign> result = searchService.findInProgress();
        log.debug("result size: {}", result.getTotal());
        Set<String> hashKeywords = new HashSet<>();
        for(IntegrateCampaign integrateCampaign : result.getList()){
            log.debug("id={}, hashTags={}", integrateCampaign.getCampaignId(), integrateCampaign.getHashTagsForSearch());
            String hasTagsForSearch = integrateCampaign.getHashTagsForSearch();
            if(hasTagsForSearch != null && StringUtils.isNotBlank(hasTagsForSearch)) {
                String[] hashTags = hasTagsForSearch.split(" ");
                hashKeywords.addAll(Arrays.stream(hashTags).collect(Collectors.toSet()));
            }
        }
        log.debug("hashKeywords size = {}", hashKeywords.size());
    }
}
