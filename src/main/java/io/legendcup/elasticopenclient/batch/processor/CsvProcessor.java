package io.legendcup.elasticopenclient.batch.processor;

import io.legendcup.elasticopenclient.batch.model.RawSampleCompany;
import io.legendcup.elasticopenclient.batch.model.SampleCompany;
import io.legendcup.elasticopenclient.batch.repository.SampleCompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CsvProcessor implements ItemProcessor<RawSampleCompany, RawSampleCompany> {
    @Autowired
    private SampleCompanyRepository sampleCompanyRepository;

    @Override
    public RawSampleCompany process(RawSampleCompany item) throws Exception {
        log.debug("processor: {}", item);
        sampleCompanyRepository.save(new SampleCompany(item));
        return item;
    }
}
