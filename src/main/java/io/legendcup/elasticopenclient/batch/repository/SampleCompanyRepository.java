package io.legendcup.elasticopenclient.batch.repository;

import io.legendcup.elasticopenclient.batch.model.SampleCompany;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SampleCompanyRepository extends PagingAndSortingRepository<SampleCompany, String> {
}
