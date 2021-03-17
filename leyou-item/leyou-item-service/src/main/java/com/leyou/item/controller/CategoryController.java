package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Date:2021/03_10:37 下午
 * @Description：
 */
@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("names")
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){

        List<String> names = this.categoryService.queryNamesByIds(ids);
        if (CollectionUtils.isEmpty(names)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }

    /**
     * 根据父id查询子节点
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid",defaultValue = "0") Long pid) {

        if (pid == null || pid.longValue() < 0) {

            return ResponseEntity.badRequest().build();     //响应400，相当于ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<Category> categories = this.categoryService.queryCategoriesByPid(pid);
        if (CollectionUtils.isEmpty(categories)) {

            return ResponseEntity.notFound().build();   //响应404
        }
        return ResponseEntity.ok(categories);
    }
}