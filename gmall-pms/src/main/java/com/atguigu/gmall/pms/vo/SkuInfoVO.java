package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.SkuInfoEntity;
import com.atguigu.gmall.pms.entity.SkuSaleAttrValueEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuInfoVO extends SkuInfoEntity {
    //积分相关字段
    private BigDecimal growBounds;//成长积分
    private BigDecimal buyBounds;//购物积分
    private List<Integer> work;//积分生效情况

    //打折相关字段
    private Integer fullCount;//满几件
    private BigDecimal discount;//打几折
    private BigDecimal price;//折后价
    private Integer ladderAddOther;//是否叠加其他优惠

    //满减相关字段
    private BigDecimal fullPrice;//满多少
    private BigDecimal reducePrice;//减多少
    private Integer fullAddOther;//是否参与其他优惠

    //销售属性
    private List<SkuSaleAttrValueEntity> saleAttrs;


    //图片集合
    private List<String> images;
}
