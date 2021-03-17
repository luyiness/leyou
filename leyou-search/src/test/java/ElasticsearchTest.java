import com.leyou.LeyouSearchApplication;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Spu;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date:2021/03_8:39 下午
 * @Description：
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeyouSearchApplication.class)
public class ElasticsearchTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    @Test
    public void createIndex(){

        this.elasticsearchTemplate.createIndex(com.leyou.pojo.Goods.class);    // 创建索引

        this.elasticsearchTemplate.putMapping(com.leyou.pojo.Goods.class);    // 配置映射

        Integer page = 1;
        Integer rows = 100;

        do {    //do while循环，每循环一次就是一页
            PageResult<SpuBo> pageResult = this.goodsClient.querySpuByPage(null, null, page, rows);        // 要分批查询spuBo，若直接查全部返回值太大
            List<com.leyou.pojo.Goods> goodsList = pageResult.getItems().stream().map(spuBo -> {        // 遍历spubo集合转化为List<Goods>
                //pageResult.getItems()是获取当前页的数据；
                try {
                    return this.searchService.buildGoods((Spu) spuBo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            this.goodsRepository.saveAll(goodsList);    //导入索引库

            rows = pageResult.getItems().size();        // 获取当前页的行数（数据条数）
            page++;        // 每次循环页码加1

        } while (rows == 100);   //每页100行，rows到了100 下一页（下一次循环）；若没到100、则就是最后一页，退出循环。
    }
}
