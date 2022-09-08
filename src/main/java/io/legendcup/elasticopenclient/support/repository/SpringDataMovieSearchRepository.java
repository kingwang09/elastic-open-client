package io.legendcup.elasticopenclient.support.repository;

import io.legendcup.elasticopenclient.example.model.SpringDataMovieSearch;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SpringDataMovieSearchRepository extends PagingAndSortingRepository<SpringDataMovieSearch, String> {
}
