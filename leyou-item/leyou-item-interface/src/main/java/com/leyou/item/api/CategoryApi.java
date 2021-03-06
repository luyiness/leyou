package com.leyou.item.api;

import com.leyou.item.pojo.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: 98050
 * Time: 2018-10-11 20:05
 * Feature:商品分类服务接口
 */
@RequestMapping("category")
public interface CategoryApi {

    /**
     * 根据id，查询分类名称
     */
    @GetMapping("names")
    public List<String> queryNamesByIds(@RequestParam("ids") List<Long> ids);

}
