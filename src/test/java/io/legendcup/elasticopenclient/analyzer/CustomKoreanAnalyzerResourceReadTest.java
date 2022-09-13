package io.legendcup.elasticopenclient.analyzer;

import io.legendcup.elasticopenclient.korean.analyzer.CustomKoreanAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@Slf4j
@SpringBootTest
public class CustomKoreanAnalyzerResourceReadTest {

    @Autowired
    private CustomKoreanAnalyzer customKoreanAnalyzer;

    @Value("${analyzer.compound-path}")
    private String compoundPath;

    @Test
    public void testCompoundResource() {
        final String value = "신박한 가방";
        String result = this.makeSearchText(customKoreanAnalyzer.makeKoreanAnalyzer(compoundPath), value);
        log.debug("result: {}", result);
        //assertEquals(this.makeSearchText(customKoreanAnalyzer.getKoreanAnalyzer(), value),
        //        this.makeSearchText(customKoreanAnalyzer.makeKoreanAnalyzer(compoundPath), value));
    }


    public String makeSearchText(KoreanAnalyzer koreanAnalyzer, String value) {
        if (value == null) {
            return null;
        }
        String result = "";
        try {
            TokenStream tokenStream = koreanAnalyzer.tokenStream("result", value);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                CharTermAttribute termAttr = tokenStream.getAttribute(CharTermAttribute.class);
                result += termAttr + " ";
            }
            log.info("result {}", result);
            tokenStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
