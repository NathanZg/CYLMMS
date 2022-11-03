package com.cylmms.vo;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @author EKERTREE
 * @Date 2022/11/01 17:41
 **/
@Getter
public class UserVo {
    private String idCard;
    private String name;

    private String duty;

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

    public void setDuty(String duty) {
        if (!StrUtil.isEmpty(duty)) {
            this.duty = '%' + duty + '%';
        }
    }
}
