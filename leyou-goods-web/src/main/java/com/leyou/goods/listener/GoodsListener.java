package com.leyou.goods.listener;

import com.leyou.goods.service.GoodsHtmlService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Date:2021/03_1:12 下午
 * @Description：
 */
@Component
public class GoodsListener {

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.ITEM.SAVE.QUEUE",durable = "true"),   //队列名
            exchange = @Exchange(value = "LEYOU.ITEM.EXCHANGE",     //绑定的交换机名（必须与生产者的一样）
                                ignoreDeclarationExceptions = "true",   //忽略声明异常
                                type = ExchangeTypes.TOPIC),     //设置订阅模型
            key = {"item.insert","item.update"}     //设置RoutingKey
    ))
    public void save(Long id){
        if (id == null){
            return;
        }
        this.goodsHtmlService.createHtml(id);   //最后调用createHtml方法
    }
}
