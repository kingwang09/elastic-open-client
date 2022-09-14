package io.legendcup.elasticopenclient.analyzer;

import io.legendcup.elasticopenclient.korean.analyzer.CustomKoreanAnalyzer;
import io.legendcup.elasticopenclient.support.util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@Slf4j
@SpringBootTest
public class CustomKoreanAnalyzerResourceReadTest {

    @Autowired
    private CustomKoreanAnalyzer customKoreanAnalyzer;

    @Value("${analyzer.compound-path}")
    private String compoundPath;

    @Test
    public void testCompoundResource() {
        final String value = "안녕하세요. elasticsearch입니다.";
        String result = this.makeSearchText(customKoreanAnalyzer.makeKoreanAnalyzer(compoundPath), value);
        log.debug("result: {}", result);
        //assertEquals(this.makeSearchText(customKoreanAnalyzer.getKoreanAnalyzer(), value),
        //        this.makeSearchText(customKoreanAnalyzer.makeKoreanAnalyzer(compoundPath), value));
    }

    @Test
    public void testWords() {
        String value = "여러개의 물건";
        String result = this.makeSearchText(customKoreanAnalyzer.makeKoreanAnalyzer(compoundPath), value);
        String expected = "물건";
        Assertions.assertEquals(expected, result);
    }


    @Test
    public void unigramTest(){
        final String value = "신박한 가방";
        List<String> results= customKoreanAnalyzer.getUniGramToken(value);
        for(String v : results){
            log.debug("result: {}", v);
        }
    }

    @Test
    public void textTest(){
        final String value ="신박한 가방 ";
        String result = TextUtil.makeSearchTextLike(value);

        log.debug("result: {}", result);
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
        return result.trim();
    }
}
