package com.cylmms.vo;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @author EKERTREE
 * @Date 2022/11/03 21:33
 **/
@Getter
public class GpVo {
    private String name;
    private String superior;
    private String category;
    private String industry;

    public void setName(String name) {
        if (!StrUtil.isEmpty(name)) {
            this.name = '%' + name + '%';
        }
    }

    public void setSuperior(String superior) {
        if (!StrUtil.isEmpty(superior)) {
            this.superior = '%' + superior + '%';
        }
    }

    public void setCategory(String category) {
        if (!StrUtil.isEmpty(category)) {
            this.category = '%' + category + '%';
        }
    }

    public void setIndustry(String industry) {
        if (!StrUtil.isEmpty(industry)) {
            this.industry = '%' + industry + '%';
        }
    }
}
