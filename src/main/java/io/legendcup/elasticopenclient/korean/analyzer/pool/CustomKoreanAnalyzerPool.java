package io.legendcup.elasticopenclient.korean.analyzer.pool;

import io.legendcup.elasticopenclient.korean.analyzer.CustomKoreanAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool.BasePoolableObjectFactory;

@Slf4j
public class CustomKoreanAnalyzerPool extends BasePoolableObjectFactory<CustomKoreanAnalyzer> {

    private String compoundPath;

    public CustomKoreanAnalyzerPool(String compoundPath){
        this.compoundPath = compoundPath;
    }

    @Override
    public CustomKoreanAnalyzer makeObject() throws Exception {
        CustomKoreanAnalyzer result = new CustomKoreanAnalyzer(compoundPath);
        log.debug("make object: {}", result);
        return result;
    }


}
