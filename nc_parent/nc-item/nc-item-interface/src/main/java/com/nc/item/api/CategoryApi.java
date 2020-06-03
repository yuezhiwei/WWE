package com.nc.item.api;

import com.nc.item.pojo.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public interface CategoryApi {

    @GetMapping("list")
    public List<Category> queryCategoriesByPid(@RequestParam(value = "pid", defaultValue = "0") Long pid);

    @GetMapping("bid/{bid}")
    public List<Category> queryByBrandId(@PathVariable("bid") Long bid);

    @GetMapping("names")
    public List<String> queryNamesByIds(@RequestParam("ids") List<Long> ids);

}
