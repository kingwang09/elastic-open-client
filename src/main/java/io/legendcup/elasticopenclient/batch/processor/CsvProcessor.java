package io.legendcup.elasticopenclient.batch.processor;

import io.legendcup.elasticopenclient.batch.model.RawSampleCompany;
import io.legendcup.elasticopenclient.batch.model.SampleCompany;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CsvProcessor implements ItemProcessor<RawSampleCompany, SampleCompany> {
    //@Autowired
    //private SampleCompanyRepository sampleCompanyRepository;

    @Override
    public SampleCompany process(RawSampleCompany item) throws Exception {
        log.debug("processor: {}", item);
        //sampleCompanyRepository.save(new SampleCompany(item));
        return new SampleCompany(item);
    }
}
