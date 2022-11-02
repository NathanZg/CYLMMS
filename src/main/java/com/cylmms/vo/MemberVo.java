package com.cylmms.vo;

import cn.hutool.core.util.StrUtil;
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
        if (!StrUtil.isEmpty(idCard)) {
            this.idCard = '%' + idCard + '%';
        }
    }

    public void setName(String name) {
        if (!StrUtil.isEmpty(name)) {
            this.name = '%' + name + '%';
        }
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
