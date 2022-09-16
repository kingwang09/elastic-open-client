package io.legendcup.elasticopenclient.korean.analyzer;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.analysis.ko.KoreanTokenizer;
import org.apache.lucene.analysis.ko.POS;
import org.apache.lucene.analysis.ko.dict.UserDictionary;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public class CustomKoreanAnalyzer {
    private KoreanAnalyzer koreanAnalyzer;

    public CustomKoreanAnalyzer(String dicPath) {
        try {
            log.debug("dicPaht: {}", dicPath);
            Path path = Paths.get(new ClassPathResource(dicPath).getURI());
            int count = Files.readAllLines(path).size();
            log.info("compound Dictionary size : " + count);
            Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            Set<POS.Tag> stopTag = findStopTags();
            koreanAnalyzer = new KoreanAnalyzer(UserDictionary.open(reader), KoreanTokenizer.DecompoundMode.DISCARD, stopTag, false);

        } catch (Exception e) {
            log.info("analyzer setting fail: {}", e.getMessage(), e);
        }
    }

    private Set<POS.Tag> findStopTags(){
        Set<POS.Tag> stopTag = new HashSet<POS.Tag>();
        stopTag.add(POS.Tag.E);
        stopTag.add(POS.Tag.IC);
        stopTag.add(POS.Tag.J);
        stopTag.add(POS.Tag.MAG);
        stopTag.add(POS.Tag.MAJ);
        stopTag.add(POS.Tag.MM);
        stopTag.add(POS.Tag.NA);
        stopTag.add(POS.Tag.NR);
        stopTag.add(POS.Tag.SC);
        stopTag.add(POS.Tag.SE);
        stopTag.add(POS.Tag.SF);
        stopTag.add(POS.Tag.SH);
        stopTag.add(POS.Tag.SL);
        stopTag.add(POS.Tag.SN);
        stopTag.add(POS.Tag.SP);
        stopTag.add(POS.Tag.SSC);
        stopTag.add(POS.Tag.SSO);
        stopTag.add(POS.Tag.SY);
        stopTag.add(POS.Tag.UNA);
        stopTag.add(POS.Tag.UNKNOWN);
        stopTag.add(POS.Tag.VA);
        stopTag.add(POS.Tag.VCN);
        stopTag.add(POS.Tag.VCP);
        //stopTag.add(Tag.VCV);
        stopTag.add(POS.Tag.VV);
        stopTag.add(POS.Tag.VX);
        stopTag.add(POS.Tag.XPN);
        stopTag.add(POS.Tag.XR);
        stopTag.add(POS.Tag.XSA);
        stopTag.add(POS.Tag.XSN);
        stopTag.add(POS.Tag.XSV);
        stopTag.add(POS.Tag.NNBC);
        return stopTag;
    }

    public List<String> getSearchTextList(String value) {
        if (value == null) {
            return new ArrayList<>();
        }
        log.debug("start korean text..");
        long start = System.currentTimeMillis();
        List<String> result = new LinkedList<>();
        try {
            TokenStream tokenStream = this.koreanAnalyzer.tokenStream("result", value);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                CharTermAttribute termAttr = tokenStream.getAttribute(CharTermAttribute.class);
                String term = termAttr + "";
                result.add(term);

//                Set<String> retSet = this.getSynonym(termAttr + "");
//                if (retSet.size() != 0) {
//                    for (String word : retSet) {
//                        if (!term.equals(word) && word.length() > 1) {
//                            result += word + " ";
//                        }
//                    }
//                }
            }
            tokenStream.close();
        } catch (IOException e) {
            log.error("token error: {}", e.getMessage(), e);
        }
        long end = System.currentTimeMillis();
        log.debug("end korean text: {}ms", (end-start));
        return result;
    }
}
