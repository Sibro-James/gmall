package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
public class BaseAttrVO extends ProductAttrValueEntity {
    public void setValueSelected(List<String> select){

        if (CollectionUtils.isEmpty(select)){
            return;
        }
        this.setAttrValue(StringUtils.join(select,","));
    }
}
