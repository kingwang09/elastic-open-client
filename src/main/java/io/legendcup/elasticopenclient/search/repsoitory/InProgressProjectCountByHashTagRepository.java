package io.legendcup.elasticopenclient.search.repsoitory;

import io.legendcup.elasticopenclient.search.model.InProgressProjectCountByHashTag;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InProgressProjectCountByHashTagRepository extends PagingAndSortingRepository<InProgressProjectCountByHashTag, String> {
}
