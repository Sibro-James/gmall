package com.atguigu.gmall.sms.dao;

import com.atguigu.gmall.sms.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author sibro
 * @email sibro@111.com
 * @date 2020-03-09 11:48:54
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
