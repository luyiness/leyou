package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Date:2021/03_1:27 下午
 * @Description：
 */
public interface BrandMapper extends Mapper<Brand> {

    //添加brand
    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertBrandAndCategory(@Param("cid")Long cid,@Param("bid")Long bid);
}
