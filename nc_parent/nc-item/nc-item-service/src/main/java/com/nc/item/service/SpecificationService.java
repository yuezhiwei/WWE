package com.nc.item.service;

import com.nc.item.mapper.SpecGroupMapper;
import com.nc.item.mapper.SpecParamMapper;
import com.nc.item.pojo.SpecGroup;
import com.nc.item.pojo.SpecParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

@Service
public class SpecificationService {
    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return specGroupMapper.select(specGroup);
    }

    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam record = new SpecParam();
        record.setGroupId(gid);
        record.setCid(cid);
        record.setGeneric(generic);
        record.setSearching(searching);
        return this.specParamMapper.select(record);
    }

    @Transactional
    public void saveOrUpdateParam(SpecParam specParam) {
       if (Objects.isNull(specParam.getId())){
           specParamMapper.insert(specParam);
       }else {
           specParamMapper.updateByPrimaryKey(specParam);
       }
    }

    @Transactional
    public void deleteParamById(Long id) {
        specParamMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void saveOrUpdateGroup(SpecGroup specGroup) {
        if (Objects.isNull(specGroup.getId())){
            specGroupMapper.insert(specGroup);
        }else {
            specGroupMapper.updateByPrimaryKey(specGroup);
        }
    }
    @Transactional
    public void deleteGroupById(Long id) {
        specGroupMapper.deleteByPrimaryKey(id);
        Example example = new Example(SpecParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("groupId",id);
        specParamMapper.deleteByExample(example);
    }

    public List<SpecGroup> querySpecsByCid(Long cid) {
        List<SpecGroup> groups = this.queryGroupsByCid(cid);
        groups.forEach(g -> g.setParams(this.queryParams(g.getId(), null, null, null)));
        return groups;
    }
}