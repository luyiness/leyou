package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Date:2021/03_1:44 下午
 * @Description：
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id")Long id){
        Brand brand = this.brandService.queryBrandByCid(id);
        if (brand == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brand);
    }

    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid")Long cid){
        List<Brand> brands = this.brandService.queryBrandsByCid(cid);
        if (CollectionUtils.isEmpty(brands)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }

    //查询所有brand（接收参数，返回ResultPage对象）
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(
            @RequestParam(value = "key", required = false)String key,         //搜索框
            @RequestParam(value = "page", defaultValue = "1")Integer page,            //起始页
            @RequestParam(value = "rows", defaultValue = "5")Integer rows,      //每页条数
            @RequestParam(value = "sortBy", required = false)String sortBy,       //默认按什么排序
            @RequestParam(value = "desc", required = false)Boolean desc        //升序
    ){
        PageResult<Brand> result = this.brandService.queryBrandsByPage(key, page, rows, sortBy, desc);

        if (CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    //添加brand（接收json串，无返回值）
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids){  //接收的cid是分类、其余参数封装成brand
        this.brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
