package com.leyou.search.listener;

import com.leyou.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Date:2021/03_1:33 下午
 * @Description：
 */
@Component
public class GoodsListener {

    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.SEARCH.SAVE.QUEUE",durable = "true"),   //队列名
            exchange = @Exchange(value = "LEYOU.ITEM.EXCHANGE",     //绑定的交换机名（必须与生产者的一样）
                    ignoreDeclarationExceptions = "true",   //忽略声明异常
                    type = ExchangeTypes.TOPIC),     //设置订阅模型
            key = {"item.insert","item.update"}     //设置RoutingKey
    ))
    public void save(Long id) throws IOException {
        if (id == null){
            return;
        }
        this.searchService.save(id);   //最后调用createHtml方法
    }
}
