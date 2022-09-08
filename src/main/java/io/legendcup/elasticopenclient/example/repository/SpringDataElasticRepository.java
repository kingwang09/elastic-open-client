package io.legendcup.elasticopenclient.example.repository;

import io.legendcup.elasticopenclient.example.model.SpringDataElasticExample;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SpringDataElasticRepository extends PagingAndSortingRepository<SpringDataElasticExample, String> {
}
