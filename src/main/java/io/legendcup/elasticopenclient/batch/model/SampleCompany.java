package io.legendcup.elasticopenclient.batch.model;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Dynamic;

import javax.persistence.Id;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "company-example", createIndex = true, dynamic = Dynamic.TRUE)
public class SampleCompany {
    @Id
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
    
    public SampleCompany(RawSampleCompany rawSampleCompany){
        this.name = getValue(rawSampleCompany.getName());
        this.tradeName = getValue(rawSampleCompany.getTradeName());
        this.englishName = getValue(rawSampleCompany.getEnglishName());
        
        this.category = getValue(rawSampleCompany.getCategory());
        this.scale = getValue(rawSampleCompany.getScale());
        
        this.graduateDate = getValue(rawSampleCompany.getGraduateDate());
        
        this.shapeCode = getValue(rawSampleCompany.getShapeCode());
        
        this.foundationShape = getValue(rawSampleCompany.getFoundationShape());
        this.status = getValue(rawSampleCompany.getStatus());
        this.statusChangeDate = getValue(rawSampleCompany.getStatusChangeDate());
        
        this.publicInstitutionDivision = getValue(rawSampleCompany.getPublicInstitutionDivision());
        
        this.ventureDivision = getValue(rawSampleCompany.getVentureDivision());
        this.recentSectorsCode = getValue(rawSampleCompany.getRecentSectorsCode());
        this.financialSectorsCode = getValue(rawSampleCompany.getFinancialSectorsCode());

        this.groupCode = getValue(rawSampleCompany.getGroupCode());
        this.groupName = getValue(rawSampleCompany.getGroupName());

        this.businessId = getValue(rawSampleCompany.getBusinessId());

        this.openDate = getValue(rawSampleCompany.getOpenDate());
        this.openCode = getValue(rawSampleCompany.getOpenCode());

        this.tradeId = getValue(rawSampleCompany.getTradeId());
        this.stockOpenDate = getValue(rawSampleCompany.getStockOpenDate());
        this.stockCloseDate = getValue(rawSampleCompany.getStockCloseDate());

        this.primaryBankCode = getValue(rawSampleCompany.getPrimaryBankCode());
        this.primaryBankName = getValue(rawSampleCompany.getPrimaryBankName());

        this.accountCode = getValue(rawSampleCompany.getAccountCode());
        this.accountName = getValue(rawSampleCompany.getAccountName());

        this.settlementDate = getValue(rawSampleCompany.getSettlementDate());

        this.homepageUrl = getValue(rawSampleCompany.getHomepageUrl());

        this.email = getValue(rawSampleCompany.getEmail());
        this.updateDate = getValue(rawSampleCompany.getUpdateDate());
        this.businessNumber = getValue(rawSampleCompany.getBusinessNumber());
        this.officeCode = getValue(rawSampleCompany.getOfficeCode());
        this.officeAddress = getValue(rawSampleCompany.getOfficeAddress());
        this.officeAddressDetail = getValue(rawSampleCompany.getOfficeAddressDetail());

        this.poneNumber = getValue(rawSampleCompany.getPoneNumber());
        this.faxNumber = getValue(rawSampleCompany.getFaxNumber());

        this.workerCount = rawSampleCompany.getWorkerCount();

        this.productName = getValue(rawSampleCompany.getProductName());

        this.sectorsCode = getValue(rawSampleCompany.getSectorsCode());

        this.relatedId = getValue(rawSampleCompany.getRelatedId());
        this.relatedOpenDate = getValue(rawSampleCompany.getRelatedOpenDate());

        this.officeRoadCode = getValue(rawSampleCompany.getOfficeRoadCode());
        this.officeRoadAddress = getValue(rawSampleCompany.getOfficeRoadAddress());
        this.officeRoadAddressDetail = getValue(rawSampleCompany.getOfficeRoadAddressDetail());
        this.isRoadAddress = getValue(rawSampleCompany.getIsRoadAddress());
        this.isCheckedAddress = getValue(rawSampleCompany.getIsCheckedAddress());
    }
    
    public String getValue(String value){
        if(StringUtils.isBlank(value)){
            return null;
        }
        return StringUtils.trim(value);
    }
}
