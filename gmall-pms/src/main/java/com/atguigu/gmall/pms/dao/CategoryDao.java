package com.atguigu.gmall.pms.dao;

import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author sibro
 * @email sibro@111.com
 * @date 2020-03-06 14:00:04
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
