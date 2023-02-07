package io.legendcup.elasticopenclient.search.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegrateCampaign {

    public static final String INDEX_ALIAS = "integrate_search-alias";

    private Integer campaignId;

    private String campaignStatus;

    private String hashTagsForSearch;
}
