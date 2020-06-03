package com.nc.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nc.common.pojo.PageResult;
import com.nc.item.mapper.*;
import com.nc.item.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private Logger logger = LoggerFactory.getLogger(GoodsService.class);

    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();


        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }

        PageHelper.startPage(page,rows);

        List<Spu> spus = this.spuMapper.selectByExample(example);

        List<SpuBo> spuBos = new ArrayList<>();
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);
        spus.forEach(spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu, spuBo);
            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names, "/"));
            spuBo.setBname(this.brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());
            spuBos.add(spuBo);
            spuBo=null;

        });

        return new PageResult<>(pageInfo.getTotal(),spuBos);
    }

    @Transactional
    public void saveOrUpdate(SpuBo spuBo) {
        if (Objects.isNull(spuBo.getId())){
            spuBo.setSaleable(true);
            spuBo.setValid(true);
            spuBo.setCreateTime(new Date());
            spuBo.setLastUpdateTime(spuBo.getCreateTime());
            this.spuMapper.insertSelective(spuBo);
            SpuDetail spuDetail = spuBo.getSpuDetail();
            spuDetail.setSpuId(spuBo.getId());
            this.spuDetailMapper.insertSelective(spuDetail);

            sendMsg("insert",spuBo.getId());

        }else {
            spuBo.setLastUpdateTime(spuBo.getCreateTime());
            List<Sku> skus = this.querySkusBySpuId(spuBo.getId());
            if (!CollectionUtils.isEmpty(skus)){
                List<Long> ids = skus.stream().map(s -> s.getId()).collect(Collectors.toList());
                Example example = new Example(Stock.class);
                example.createCriteria().andIn("skuId", ids);
                this.stockMapper.deleteByExample(example);
                Sku record = new Sku();
                record.setSpuId(spuBo.getId());
                this.skuMapper.delete(record);
            }
            spuBo.setLastUpdateTime(new Date());
            spuBo.setCreateTime(null);
            spuBo.setValid(null);
            spuBo.setSaleable(null);
            this.spuMapper.updateByPrimaryKeySelective(spuBo);
            this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());

            sendMsg("update",spuBo.getId());
        }

        saveSkuAndStock(spuBo);
    }

    private void saveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus().forEach(sku -> {
            // 新增sku
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);

            // 新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }

    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        return spuDetailMapper.selectByPrimaryKey(spuId);
    }

    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(sku);
        skus.forEach(s -> {
            Stock stock = this.stockMapper.selectByPrimaryKey(s.getId());
            s.setStock(stock.getStock());
        });
        return skus;
    }
    @Transactional
    public void goodsDownOrUp(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        spu.setSaleable(!spu.getSaleable());
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Transactional
    public void del(Long id) {
        Sku sku = new Sku();
        sku.setSpuId(id);
        List<Sku> skus = skuMapper.select(sku);
        skus.forEach(sku1 -> {
            stockMapper.deleteByPrimaryKey(sku1.getId());
            skuMapper.delete(sku1);
        });
        spuMapper.deleteByPrimaryKey(id);
        spuDetailMapper.deleteByPrimaryKey(id);
    }

    public Spu querySpuById(Long id) {
        return this.spuMapper.selectByPrimaryKey(id);
    }


    public void sendMsg(String type,Long id){
        try {
            this.amqpTemplate.convertAndSend("item." + type, id);
        } catch (Exception e) {
            logger.error("{}商品消息发送异常，商品id：{}", type, id, e);
        }
    }

    public Sku querySkuById(Long id) {
        return skuMapper.selectByPrimaryKey(id);
    }
}