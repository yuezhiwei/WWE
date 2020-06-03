package com.nc.goods.service;

import com.nc.goods.utils.ThredUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

@Service
public class GoodsHtmlService {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TemplateEngine templateEngine;

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsService.class);

    public void createHtml(Long id){
        PrintWriter writer = null;
        try {
            File file = new File("F:\\vue-learn\\tools\\nginx-1.14.0\\html\\"+id+".html");
            Map<String, Object> map = goodsService.loadData(id);
            Context context = new Context();
            context.setVariables(map);
            writer = new PrintWriter(file);
            if (file.exists()){
                file.createNewFile();
            }
            templateEngine.process("item",context,writer);
        }catch (Exception e){
            LOGGER.error("静态化{}出错了,"+e,id);
        }finally {
            if (writer!=null){
                writer.close();
            }
        }
    }


    public void synCreateHtml(Long id){
        ThredUtils.synCreateHtml(()->createHtml(id));
    }
    public void deleteHtml(Long id) {
        File file = new File("F:\\vue-learn\\tools\\nginx-1.14.0\\html\\"+id+".html");
        file.deleteOnExit();
    }
}
