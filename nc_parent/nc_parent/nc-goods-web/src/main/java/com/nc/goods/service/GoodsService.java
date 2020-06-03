package com.nc.goods.service;

import com.nc.goods.client.BrandClient;
import com.nc.goods.client.CategoryClient;
import com.nc.goods.client.GoodsClient;
import com.nc.goods.client.SpecificationClient;
import com.nc.item.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    public Map<String, Object> loadData(Long spuId){
        HashMap<String, Object> map = new HashMap<>();

        // 根据spuid查询spu对象
        Spu spu = this.goodsClient.querySpuById(spuId);

        // 查询spudetail
        SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spuId);

        // 查询sku集合  根据型号查询出来他所有套餐
        List<Sku> skus = this.goodsClient.querySkusBySpuId(spuId);

        // 查询分类  多级分类  存入 List<Map<String, Object>> categories
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> names = this.categoryClient.queryNamesByIds(cids);
        List<Map<String, Object>> categories = new ArrayList<>();
        for (int i = 0; i < cids.size(); i++) {
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("id", cids.get(i));
            categoryMap.put("name", names.get(i));
            categories.add(categoryMap);
        }

        // 查询品牌
        Brand brand = this.brandClient.queryBrandById(spu.getBrandId());

        // 根据SPU的CID3 查询出该类的 规格参数组
        List<SpecGroup> groups = this.specificationClient.querySpecsByCid(spu.getCid3());

        // 根据SPU的CID3 查询所有的规格参数  这里进行的全部查询 方便找值
        List<SpecParam> params = this.specificationClient.queryParams(null, spu.getCid3(), null, null);
        Map<Long, String> paramMap = new HashMap<>();
        params.forEach(param -> paramMap.put(param.getId(), param.getName()));

        // 封装spu
        map.put("spu", spu);
        // 封装spuDetail
        map.put("spuDetail", spuDetail);
        // 封装sku集合
        map.put("skus", skus);
        // 分类
        map.put("categories", categories);
        // 品牌
        map.put("brand", brand);
        // 规格参数组
        map.put("groups", groups);
        // 查询特殊规格参数
        map.put("paramMap", paramMap);

        return map;
    }
}
