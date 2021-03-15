package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Date:2021/03_1:28 下午
 * @Description：
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public List<Brand> queryBrandsByCid(Long cid) {

        return this.brandMapper.selectBrandByCid(cid);
    }

    public PageResult<Brand> queryBrandsByPage(String key,Integer page,Integer rows,String sortBy,Boolean desc){
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(key)){       //搜索功能
            criteria.andLike("name","%" + key + "%").orEqualTo("letter",key);
        }

        PageHelper.startPage(page,rows);    //分页

        if (StringUtils.isNotBlank(sortBy)){    //排序
            example.setOrderByClause(sortBy + " " + (desc?"desc":"asc"));
        }

        List<Brand> brands = this.brandMapper.selectByExample(example); //总查询

        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        return new PageResult<>(brandPageInfo.getTotal(),brandPageInfo.getList());
    }

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        this.brandMapper.insertSelective(brand);    //插入数据到brand表

        cids.forEach(cid -> {
            this.brandMapper.insertBrandAndCategory(cid, brand.getId());    //插入cid和bid 到category表
        });
    }
}
