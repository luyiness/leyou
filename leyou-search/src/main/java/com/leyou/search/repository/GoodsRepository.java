package com.leyou.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Date:2021/03_8:38 下午
 * @Description：
 */
public interface GoodsRepository extends ElasticsearchRepository<com.leyou.pojo.Goods, Long> {
}
