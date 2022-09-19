package io.legendcup.elasticopenclient.korean.controller;

import io.legendcup.elasticopenclient.korean.analyzer.pool.CustomKoreanAnalyzerExecutor;
import io.legendcup.elasticopenclient.korean.analyzer.original.OriginalCustomKoreanAnalyzer;
import io.legendcup.elasticopenclient.korean.analyzer.pool2.CustomKoreanAnalyzerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/korean")
public class KoreanAnalyzerRestController {

    private final OriginalCustomKoreanAnalyzer originalCustomKoreanAnalyzer;
    private final CustomKoreanAnalyzerExecutor koreanAnalyzerExecutor;
    private final CustomKoreanAnalyzerUtil customKoreanAnalyzerUtil;


    @GetMapping
    public String get(@RequestParam String keyword){
        return originalCustomKoreanAnalyzer.makeUniGramText(keyword);
    }

    @GetMapping("/pool")
    public String pool(@RequestParam String keyword){
        return koreanAnalyzerExecutor.getText(keyword).stream().collect(Collectors.joining(","));
    }

    @GetMapping("/pool2")
    public String pool2(@RequestParam String keyword){
        return customKoreanAnalyzerUtil.getText(keyword).stream().collect(Collectors.joining(","));
    }
}
