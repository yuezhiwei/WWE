package com.nc.order.service.api;

import com.nc.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface GoodsService extends GoodsApi {
}
