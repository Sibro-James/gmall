package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gmall.pms.dao.AttrDao;
import com.atguigu.gmall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.vo.GroupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.AttrGroupDao;
import com.atguigu.gmall.pms.entity.AttrGroupEntity;
import com.atguigu.gmall.pms.service.AttrGroupService;
import org.springframework.util.CollectionUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationDao relationDao;
    @Autowired
    private AttrDao attrDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public GroupVO queryGroupWithAttrsByCid(Long gid) {
        GroupVO groupVO = new GroupVO();
        //查询group
        AttrGroupEntity group = this.getById(gid);
        BeanUtils.copyProperties(group,groupVO);

        //根据gid查询关联关系，并获取attrIds
        List<AttrAttrgroupRelationEntity> relations = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", gid));
        if(CollectionUtils.isEmpty(relations)){
            return groupVO;
        }else{
            groupVO.setRelations(relations);
        }
        //更具attrIds 获取所有规格参数
        List<Long> attrIds = relations.stream().map(relationEntity -> relationEntity.getAttrId()).collect(Collectors.toList());
        List<AttrEntity> attrEntities = attrDao.selectBatchIds(attrIds);
        groupVO.setAttrEntities(attrEntities);
        return groupVO;
    }

    @Override
    public PageVo queryGroupByPage(QueryCondition params, Long catid) {
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        if (catid != null){
            wrapper.eq("catelog_id",catid);
        }
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
        return new PageVo(page);
    }

}