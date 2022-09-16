package io.legendcup.elasticopenclient.analyzer;

import io.legendcup.elasticopenclient.korean.analyzer.CustomKoreanAnalyzerExecutor;
import io.legendcup.elasticopenclient.korean.analyzer.CustomKoreanAnalyzerPool;
import io.legendcup.elasticopenclient.korean.analyzer.original.OriginalCustomKoreanAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@SpringBootTest
public class CustomKoreanAnalyzerPoolTest {
    @Autowired
    private CustomKoreanAnalyzerExecutor customKoreanAnalyzerExecutor;

    @Autowired
    private OriginalCustomKoreanAnalyzer originalCustomKoreanAnalyzer;

    @Test
    public void test() throws Exception {
        for (int i = 0; i < 20; i++) {
            List<String> result = customKoreanAnalyzerExecutor.getText("개발자천국");
            log.debug("result: {}", result);
        }
    }

    @Test
    public void testMultiThread() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for(int i = 0; i <= 100000; i++) {
            Future<List<String>> future = executorService.submit(() -> customKoreanAnalyzerExecutor.getText("진로 디스펜서"));
            List<String> result = future.get();
            log.debug("final {}-result: {}", i, result);
        }
    }

    @Test
    public void testMultiThreadOriginal() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5000);
        for(int i = 0; i <= 100000; i++) {
            Future<List<String>> future = executorService.submit(() -> originalCustomKoreanAnalyzer.getUniGramToken("진로 디스펜서"));
            List<String> result = future.get();
            log.debug("final {}-result: {}", i, result);
        }

    }

}
