package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.dao.SpuInfoDescDao;
import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import com.atguigu.gmall.pms.entity.SkuSaleAttrValueEntity;
import com.atguigu.gmall.pms.entity.SpuInfoDescEntity;
import com.atguigu.gmall.pms.service.ProductAttrValueService;
import com.atguigu.gmall.pms.vo.BaseAttrVO;
import com.atguigu.gmall.pms.vo.SpuInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.SpuInfoDao;
import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import com.atguigu.gmall.pms.service.SpuInfoService;
import org.springframework.util.CollectionUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {


    @Autowired
    private SpuInfoDescDao descDao;
    @Autowired
    private ProductAttrValueService attrValueService;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo querySpuPage(QueryCondition condition, Long cid) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();

        //判断分类
        if (cid!=null && cid != 0 ){
            wrapper.eq("catalog_id",cid);
        }

        String key = condition.getKey();
        //判断关键字是否为空
        if(StringUtils.isNotBlank(key)){
            wrapper.and(t ->t.eq("id",key).or().like("spu_name",key));
        }

        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(condition),
                wrapper
        );

        return new PageVo(page);
    }

    @Override
    public void bigSave(SpuInfoVO spuInfoVO) {
        //1.保存spu相关的三张表
        //1.1保存pms_spu_Info信息
        spuInfoVO.setCreateTime(new Date());
        spuInfoVO.setUodateTime(spuInfoVO.getCreateTime());
        this.save(spuInfoVO);

        //1.2保存pms_spu_desc信息
        List<String> spuImges = spuInfoVO.getSpuImages();
        if (!CollectionUtils.isEmpty(spuImges)){
            SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
            spuInfoDescEntity.setSpuId(spuInfoVO.getId());
            spuInfoDescEntity.setDecript(StringUtils.join(spuImges,","));
            this.descDao.insert(spuInfoDescEntity);
        }
        //1.2保存pms_product_attr_value信息
        List<BaseAttrVO> baseAttrs = spuInfoVO.getBaseAttrs();
        if (!CollectionUtils.isEmpty(baseAttrs)){
            List<ProductAttrValueEntity> entities =
                    baseAttrs.stream().map(baseAttr -> {
                        ProductAttrValueEntity attrValueEntity =  baseAttr;
                        attrValueEntity.setSpuId(spuInfoVO.getId());
                        return attrValueEntity;
                    }).collect(Collectors.toList());
            this.attrValueService.saveBatch(entities);
        }

        //2.保存sku的三张表
        //2.1保存pms_sku_Info信息

        //2.2保存pms_sku_images信息

        //2.2保存pms_sale_attr_value信息


        //3.保存营销信息的三张表
        //3.1保存sms_sku_bounds信息

        //3.2保存sms_sku_ladder信息

        //3.2保存sms_sku_full_reduction信息
    }
}