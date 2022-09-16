package io.legendcup.elasticopenclient.analyzer;

import io.legendcup.elasticopenclient.korean.analyzer.CustomKoreanAnalyzerExecutor;
import io.legendcup.elasticopenclient.korean.analyzer.CustomKoreanAnalyzerPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class CustomKoreanAnalyzerPoolTest {
    @Autowired
    private CustomKoreanAnalyzerExecutor customKoreanAnalyzerExecutor;

    @Test
    public void test() throws Exception {
        for (int i = 0; i < 20; i++) {
            List<String> result = customKoreanAnalyzerExecutor.getText("개발자천국");
            log.debug("result: {}", result);
        }


    }
}
