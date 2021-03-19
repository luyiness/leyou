package com.leyou.goods.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Date:2021/03_8:42 上午
 * @Description：
 */
@Service
public class GoodsHtmlService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TemplateEngine templateEngine;

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsHtmlService.class);

    /**
     * 创建html页面
     *
     * @param spuId
     * @throws Exception
     */
    public void createHtml(Long spuId) {

        PrintWriter writer = null;
        try {
            //1、获取context
            Map<String, Object> spuMap = this.goodsService.loadData(spuId); //获取页面数据
            Context context = new Context();    //创建thymeleaf上下文对象
            context.setVariables(spuMap);   //把数据放入上下文对象

            //2、创建输出流
            File file = new File("/usr/local/var/www/item/" + spuId + ".html");  //输出html页面到nginx下
            writer = new PrintWriter(file);

            //3、执行静态化
            templateEngine.process("item", context, writer);    //执行页面静态化方法

        } catch (Exception e) {
            LOGGER.error("页面静态化出错：{}，"+ e, spuId);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}