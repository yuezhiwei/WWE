package com.nc.item.api;

import com.nc.common.pojo.PageResult;
import com.nc.item.pojo.Brand;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public interface BrandApi {

    @GetMapping("page")
    public PageResult<Brand> queryBrandsByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", required = false) Boolean desc
    );

    @RequestMapping
    public Void saveBrand(Brand brand, @RequestParam("cids") List<Long> cids);

    @RequestMapping("del/{bid}")
    public Void delBrand(@PathVariable("bid") Long bid);

    @GetMapping("cid/{cid}")
    public List<Brand> queryBrandsByCid(@PathVariable("cid") Long cid);

    @GetMapping("{id}")
    public Brand queryBrandById(@PathVariable("id") Long id);

}
