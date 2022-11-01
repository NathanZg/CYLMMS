package com.cylmms.vo;

import lombok.Getter;

/**
 * @author EKERTREE
 * @Date 2022/11/01 17:41
 **/
@Getter
public class UserVo {
    private String idCard;
    private String name;

    public void setIdCard(String idCard) {
        this.idCard = '%' + idCard + '%';
    }

    public void setName(String name) {
        this.name = '%' + name + '%';
    }
}
