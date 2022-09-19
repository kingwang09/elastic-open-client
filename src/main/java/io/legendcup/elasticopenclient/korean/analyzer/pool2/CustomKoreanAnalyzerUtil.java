package io.legendcup.elasticopenclient.korean.analyzer.pool2;

import io.legendcup.elasticopenclient.korean.analyzer.CustomKoreanAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CustomKoreanAnalyzerUtil {
    @Value("${analyzer.compound-path}")
    private String compoundPath;

    private ObjectPool<CustomKoreanAnalyzer> customKoreanAnalyzerObjectPool;

    @PostConstruct
    public void init(){
        log.debug("CustomKoreanAnalyzerUtil init. dic path: {}", compoundPath);
        customKoreanAnalyzerObjectPool = new GenericObjectPool(new CustomKoreanAnalyzerFactory(compoundPath));
    }

    public List<String> getText(String keyword) {
        List<String> result = new ArrayList<>();
        try {
            CustomKoreanAnalyzer customKoreanAnalyzer = customKoreanAnalyzerObjectPool.borrowObject();
            result = customKoreanAnalyzer.getSearchTextList(keyword);
            customKoreanAnalyzerObjectPool.returnObject(customKoreanAnalyzer);
        } catch (Exception e) {
            log.error("executor error: {}",e.getMessage(), e);
        }
        return result;
    }
}
