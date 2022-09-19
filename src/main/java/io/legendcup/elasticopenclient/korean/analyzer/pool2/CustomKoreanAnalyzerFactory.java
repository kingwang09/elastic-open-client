package io.legendcup.elasticopenclient.korean.analyzer.pool2;

import io.legendcup.elasticopenclient.korean.analyzer.CustomKoreanAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

@Slf4j
public class CustomKoreanAnalyzerFactory extends BasePooledObjectFactory<CustomKoreanAnalyzer> {

    private String dictionaryPath;

    public CustomKoreanAnalyzerFactory(String dictionaryPath){
        log.debug("CustomKoreanAnalyzerFactory init. dictionaryPath:{}", dictionaryPath);
        this.dictionaryPath = dictionaryPath;
    }

    @Override
    public CustomKoreanAnalyzer create() throws Exception {
        log.debug("CustomKoreanAnalyzerFactory create..");
        return new CustomKoreanAnalyzer(dictionaryPath);
    }

    @Override
    public PooledObject<CustomKoreanAnalyzer> wrap(CustomKoreanAnalyzer obj) {
        log.debug("CustomKoreanAnalyzerFactory wrap..");
        return new DefaultPooledObject<>(obj);
    }

    @Override
    public void passivateObject(final PooledObject<CustomKoreanAnalyzer> p) throws Exception {
        // The default implementation is a no-op.
        log.debug("CustomKoreanAnalyzerFactory passivateObject...");
    }

    @Override
    public void destroyObject(PooledObject<CustomKoreanAnalyzer> p) throws Exception  {
        // The default implementation is a no-op.
        log.debug("CustomKoreanAnalyzerFactory destroyObject...");
        p.getObject().close();
    }
}
