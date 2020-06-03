package com.nc.search.test;

import com.nc.item.pojo.Category;
import com.nc.search.NcSearchServiceApplication;
import com.nc.search.client.CategoryClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = NcSearchServiceApplication.class)
@RunWith(SpringRunner.class)
public class TestDemo {

    @Autowired
    private CategoryClient categoryClient;


    @Test
    public void test(){
        List<Category> categories = categoryClient.queryByBrandId(14026L);
        categories.forEach(System.out::println);
    }
}
