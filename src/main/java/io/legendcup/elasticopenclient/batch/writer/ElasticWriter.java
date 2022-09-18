package io.legendcup.elasticopenclient.batch.writer;

import io.legendcup.elasticopenclient.batch.model.SampleCompany;
import io.legendcup.elasticopenclient.batch.repository.SampleCompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ElasticWriter implements ItemWriter<SampleCompany> {

    @Autowired
    private SampleCompanyRepository sampleCompanyRepository;

    @Override
    public void write(List<? extends SampleCompany> items) throws Exception {
        log.debug("elastic writer: size={}", items.size());
        sampleCompanyRepository.saveAll(items);
    }
}
