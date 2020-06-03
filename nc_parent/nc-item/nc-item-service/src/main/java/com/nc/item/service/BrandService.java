package com.nc.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nc.common.pojo.PageResult;
import com.nc.item.mapper.BrandMapper;
import com.nc.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

@Service
public class BrandService {


    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        PageHelper.startPage(page, rows);
        if (StringUtils.isNotBlank(sortBy) && !Objects.isNull(desc)) {
            example.setOrderByClause(sortBy + "  " + (desc ? "desc" : "asc"));
        }
        List<Brand> brands = this.brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        return new PageResult<Brand>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        if (Objects.isNull(brand.getId())) {
            this.brandMapper.insertSelective(brand);
            cids.forEach(cid -> this.brandMapper.insertCategoryAndBrand(cid, brand.getId()));
        } else {
            brandMapper.updateByPrimaryKey(brand);
            brandMapper.deleteCategoryAndBrand(brand.getId());
            cids.forEach(cid -> this.brandMapper.insertCategoryAndBrand(cid, brand.getId()));
        }
    }
    @Transactional
    public void delByBid(Long bid) {
        Brand brand = new Brand();
        brand.setId(bid);
        this.brandMapper.delete(brand);
        brandMapper.deleteCategoryAndBrand(brand.getId());
    }

    public List<Brand> queryBrandsByCid(Long cid) {
        return this.brandMapper.selectBrandByCid(cid);
    }

    public Brand queryBrandById(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }
}
