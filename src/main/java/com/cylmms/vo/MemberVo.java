package com.cylmms.vo;

import lombok.Getter;

/**
 * @author EKERTREE
 * @Date 2022/11/01 16:20
 **/
@Getter
public class MemberVo {
    private String idCard;
    private String name;
    private Integer minAge;
    private Integer maxAge;
    private String politicsStatus;
    private String affiliated;

    public void setIdCard(String idCard) {
        this.idCard = '%' + idCard + '%';
    }

    public void setName(String name) {
        this.name = '%' + name + '%';
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }

    public void setAffiliated(String affiliated) {
        this.affiliated = affiliated;
    }
}
