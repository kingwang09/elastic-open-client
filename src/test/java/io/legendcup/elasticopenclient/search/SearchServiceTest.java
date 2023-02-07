package io.legendcup.elasticopenclient.search;

import io.legendcup.elasticopenclient.search.model.InProgressProjectCountByHashTag;
import io.legendcup.elasticopenclient.search.model.IntegrateCampaign;
import io.legendcup.elasticopenclient.search.repsoitory.InProgressProjectCountByHashTagRepository;
import io.legendcup.elasticopenclient.search.service.SearchService;
import io.legendcup.elasticopenclient.support.model.ElasticResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
public class SearchServiceTest {

    @Autowired
    private SearchService searchService;
    @Autowired
    private InProgressProjectCountByHashTagRepository inProgressProjectCountByHashTagRepository;

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

    @Test
    public void unigramTextTest(){
        String keyword = "안녕 유니그램";
        String result = searchService.makeUnigramText(keyword);
        log.debug("result: {}", result);

        Assertions.assertEquals("안 녕 유 니 그 램", result);
    }

    @Test
    public void findByKeywordInProgreess(){
        String keyword = "가방";
        ElasticResult<IntegrateCampaign> result = searchService.findByKeywordInProgressCount(keyword);
        log.debug("size: {}", result.getTotal());
    }

    @Test
    public void schedule(){
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


        //hashTag로 진행중인 프로젝트 조회
        List<InProgressProjectCountByHashTag> bulkInserts = new LinkedList<>();
        for(String hashTagKeyword: hashKeywords){
            ElasticResult<IntegrateCampaign> inProgressCount = searchService.findByKeywordInProgressCount(hashTagKeyword);
            log.debug("hashTag Keyword={}, inProgressCount={}", hashTagKeyword, inProgressCount.getTotal());
            bulkInserts.add(InProgressProjectCountByHashTag.builder()
                    .hashTag(hashTagKeyword)
                    .count(inProgressCount.getTotal())
                    .build());
        }
        log.debug("save All count: {}", bulkInserts.size());
        inProgressProjectCountByHashTagRepository.saveAll(bulkInserts);
    }
}
