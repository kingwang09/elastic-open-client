package io.legendcup.elasticopenclient.korean.analyzer.original;

import com.google.common.collect.SetMultimap;
import com.google.common.collect.TreeMultimap;
import io.legendcup.elasticopenclient.support.util.ResourceReader;
import io.legendcup.elasticopenclient.support.util.TextUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.analysis.ko.KoreanTokenizer;
import org.apache.lucene.analysis.ko.POS;
import org.apache.lucene.analysis.ko.POS.Tag;
import org.apache.lucene.analysis.ko.dict.UserDictionary;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Component
public class OriginalCustomKoreanAnalyzer {

    private KoreanAnalyzer koreanAnalyzer;
    private NGramTokenizer uniGramTokenizer;
    private NGramTokenizer biGramTokenizer;
    private SetMultimap<String, String> synonymMap;

    @Autowired
    OriginalCustomKoreanAnalyzer(
            @Value("${analyzer.compound-classpath}") Resource compoundResource
            , @Value("${analyzer.synonyms-classpath}") Resource synonymsResource
    ) {
        this.koreanAnalyzer = this.makeKoreanAnalyzer(compoundResource);
        this.synonymMap = this.makeSynonym(synonymsResource);
        this.uniGramTokenizer = new NGramTokenizer(1, 1);
        this.biGramTokenizer = new NGramTokenizer(2, 2);
    }

    public KoreanAnalyzer makeKoreanAnalyzer(String dicPath) {
        KoreanAnalyzer koreanAnalyzer = null;
        try {
            Path path = Paths.get(new ClassPathResource(dicPath).getURI());
            int count = Files.readAllLines(path).size();
            log.info("compound Dictionary size : " + count);
            Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            Set<POS.Tag> stopTag = findStopTags();
            koreanAnalyzer = new KoreanAnalyzer(UserDictionary.open(reader), KoreanTokenizer.DecompoundMode.DISCARD, stopTag, false);

        } catch (Exception e) {
            log.info("analyzer setting fail");
        }
        return koreanAnalyzer;
    }

    /**
     * 명사 추출 stoptags
     * @return
     */
    private Set<POS.Tag> findStopTags(){
        Set<POS.Tag> stopTag = new HashSet<POS.Tag>();
        stopTag.add(Tag.E);
        stopTag.add(Tag.IC);
        stopTag.add(Tag.J);
        stopTag.add(Tag.MAG);
        stopTag.add(Tag.MAJ);
        stopTag.add(Tag.MM);
        stopTag.add(Tag.NA);
        stopTag.add(Tag.NR);
        stopTag.add(Tag.SC);
        stopTag.add(Tag.SE);
        stopTag.add(Tag.SF);
        stopTag.add(Tag.SH);
        stopTag.add(Tag.SL);
        stopTag.add(Tag.SN);
        stopTag.add(Tag.SP);
        stopTag.add(Tag.SSC);
        stopTag.add(Tag.SSO);
        stopTag.add(Tag.SY);
        stopTag.add(Tag.UNA);
        stopTag.add(Tag.UNKNOWN);
        stopTag.add(Tag.VA);
        stopTag.add(Tag.VCN);
        stopTag.add(Tag.VCP);
        //stopTag.add(Tag.VCV);
        stopTag.add(Tag.VV);
        stopTag.add(Tag.VX);
        stopTag.add(Tag.XPN);
        stopTag.add(Tag.XR);
        stopTag.add(Tag.XSA);
        stopTag.add(Tag.XSN);
        stopTag.add(Tag.XSV);
        stopTag.add(Tag.NNBC);
        return stopTag;
    }

    public SetMultimap<String, String> makeSynonym(String synonymPath) {
        final SetMultimap<String, String> synonymMap = TreeMultimap.create();

        final List<String> lines;
        try {
            Path path = Paths.get(new ClassPathResource(synonymPath).getURI());
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            log.info("synonym Dictionary size : " + lines.size());
            for (String line : lines) {
                String[] words = line.split(",");//StringUtils.split(line, ",");
                if (words != null && words.length > 1) {
                    synonymMap.putAll(words[0], Arrays.asList(words));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("synonym setting fail");
        }
        return synonymMap;
    }

    private KoreanAnalyzer makeKoreanAnalyzer(Resource dictionaryResource) {
        KoreanAnalyzer koreanAnalyzer = null;
        try {
            int count = ResourceReader.asList(dictionaryResource).size();
            log.info("Resource compound Dictionary size : " + count);
            Reader reader = new InputStreamReader(dictionaryResource.getInputStream(), StandardCharsets.UTF_8);
            Set<POS.Tag> stopTag = findStopTags();
            koreanAnalyzer = new KoreanAnalyzer(UserDictionary.open(reader), KoreanTokenizer.DecompoundMode.DISCARD, stopTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("analyzer setting fail");
        }
        return koreanAnalyzer;
    }

    private SetMultimap<String, String> makeSynonym(Resource synonymResource) {
        final SetMultimap<String, String> synonymMap = TreeMultimap.create();

        final List<String> lines;
        try {
            lines = ResourceReader.asList(synonymResource);
            log.info("Resource synonym Dictionary size : " + lines.size());
            for (String line : lines) {
                String[] words = StringUtils.split(line, ",");
                if (words != null && words.length > 1) {
                    synonymMap.putAll(words[0], Arrays.asList(words));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("synonym setting fail");
        }
        return synonymMap;
    }

    public String makeSearchText(String value) {
        if (value == null) {
            return null;
        }
        String result = "";
        try {
            TokenStream tokenStream = this.koreanAnalyzer.tokenStream("result", value);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                CharTermAttribute termAttr = tokenStream.getAttribute(CharTermAttribute.class);
                String term = termAttr + "";
                result += termAttr + " ";

                Set<String> retSet = this.getSynonym(termAttr + "");
                if (retSet.size() != 0) {
                    for (String word : retSet) {
                        if (!term.equals(word) && word.length() > 1) {
                            result += word + " ";
                        }
                    }
                }
            }
            tokenStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String makeUniGramText(String text) {
        if(StringUtils.isBlank(text)) return "";
        List<String> uniGramTokens = this.getUniGramToken(TextUtil.makeSearchTextLike(text));
        return uniGramTokens.stream().collect(Collectors.joining(" "));
    }

    public List<String> getUniGramToken(String text) {
        long start = System.currentTimeMillis();
        List<String> list = new ArrayList<String>();
        try {
            CharTermAttribute charTermAttribute = uniGramTokenizer.addAttribute(CharTermAttribute.class);
            uniGramTokenizer.setReader(new StringReader(text));
            uniGramTokenizer.reset();
            while (uniGramTokenizer.incrementToken()) {
                list.add(charTermAttribute.toString());
            }
            uniGramTokenizer.end();
            uniGramTokenizer.close();
        } catch (Exception e) {
            log.warn(String.format("Failure in creating tokens (%s)", text), e);
            //list = Arrays.stream(text.split("")).filter(StringUtils::isNotBlank).collect(Collectors.toList());
            throw new RuntimeException(e.getMessage());
        }
        long end = System.currentTimeMillis();
        log.debug("time: {}ms",(end-start));
        return list;
    }

    private Set<String> getSynonym(String word) {
        Set<String> EMPTY_SET = new HashSet<>();
        if (word == null || word.length() == 0) {
            return new HashSet<>();
        }
        word = word.toLowerCase();
        if (synonymMap == null || synonymMap.size() == 0) {
            return EMPTY_SET;
        }
        for (String key : synonymMap.keySet()) {
            Set<String> synonyms = synonymMap.get(key);
            if (key.equalsIgnoreCase(word) || synonyms.contains(word)) {
                return synonyms;
            }
        }
        return EMPTY_SET;
    }
}
