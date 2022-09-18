package io.legendcup.elasticopenclient.batch.model;

import lombok.*;

@ToString
@Setter
@Getter
public class RawSampleCompany {
    private String id;
    private String name;
    private String tradeName;
    private String englishName;

    private String category;
    private String scale;

    private String graduateDate;

    private String shapeCode;

    private String foundationShape;
    private String status;
    private String statusChangeDate;

    private String publicInstitutionDivision;

    private String ventureDivision;

    private String shapePosition;

    private String recentSectorsCode;
    private String financialSectorsCode;

    private String groupCode;
    private String groupName;

    private String businessId;

    private String openDate;

    private String openCode;

    private String tradeId;
    private String stockOpenDate;
    private String stockCloseDate;

    private String primaryBankCode;
    private String primaryBankName;

    private String accountCode;
    private String accountName;

    private String settlementDate;

    private String homepageUrl;

    private String email;
    private String updateDate;
    private String businessNumber;
    private String officeCode;
    private String officeAddress;
    private String officeAddressDetail;

    private String poneNumber;
    private String faxNumber;

    private Integer workerCount;

    private String productName;

    private String sectorsCode;

    private String relatedId;
    private String relatedOpenDate;

    private String officeRoadCode;
    private String officeRoadAddress;
    private String officeRoadAddressDetail;
    private String isRoadAddress;
    private String isCheckedAddress;

}
