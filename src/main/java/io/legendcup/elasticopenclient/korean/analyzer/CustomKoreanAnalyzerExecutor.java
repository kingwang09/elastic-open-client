package io.legendcup.elasticopenclient.korean.analyzer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class CustomKoreanAnalyzerExecutor {

    @Value("${analyzer.compound-path}")
    private String compoundPath;

    private GenericObjectPool<CustomKoreanAnalyzer> genericObjectPool;

    @PostConstruct
    public void init(){
        log.debug("Analyzer executor dic path: {}", compoundPath);
        genericObjectPool = new GenericObjectPool(new CustomKoreanAnalyzerPool(compoundPath), 10);
    }

    public List<String> getText(String keyword) throws Exception {
        CustomKoreanAnalyzer customKoreanAnalyzer = genericObjectPool.borrowObject();
        List<String> result = customKoreanAnalyzer.getSearchTextList(keyword);
        genericObjectPool.returnObject(customKoreanAnalyzer);
        return result;
    }
}
