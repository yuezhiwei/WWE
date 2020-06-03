package com.nc.item.api;

import com.nc.common.pojo.PageResult;
import com.nc.item.pojo.Sku;
import com.nc.item.pojo.Spu;
import com.nc.item.pojo.SpuBo;
import com.nc.item.pojo.SpuDetail;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface GoodsApi {


    @GetMapping("spu/page")
    public PageResult<SpuBo> querySpuBoByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );

    @RequestMapping("goods")
    public Void saveOrUpdate(@RequestBody SpuBo spuBo);

    @GetMapping("spu/detail/{spuId}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("spuId") Long spuId);

    @GetMapping("sku/list")
    public List<Sku> querySkusBySpuId(@RequestParam("id") Long spuId);

    @GetMapping("goodsDownOrUp")
    public Void goodsDown(Long id);

    @DeleteMapping("del")
    public Void delById(Long id);

    @GetMapping("spu/{id}")
    public Spu querySpuById(@PathVariable("id") Long id);

    @GetMapping("sku/{id}")
    public Sku querySkuById(@PathVariable("id")Long id);
}
