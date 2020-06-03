package com.nc.item.service;

import com.nc.item.mapper.CategoryMapper;
import com.nc.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    public List<Category> queryCategoriesByPid(Long pid) {

        Category category = new Category();

        category.setParentId(pid);

        return this.categoryMapper.select(category);

    }

    public List<Category> queryByBrandId(Long bid) {
        return this.categoryMapper.queryByBrandId(bid);
    }

    public List<String> queryNamesByIds(List<Long> asList) {
        List<Category> list = this.categoryMapper.selectByIdList(asList);
        List<String> names = new ArrayList<>();
        for (Category category : list) {
            names.add(category.getName());
        }
        return names;
    }
}
