package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Date:2021/03_1:27 下午
 * @Description：
 */
public interface BrandMapper extends Mapper<Brand> {

    @Select("SELECT b.* from tb_brand b INNER JOIN tb_category_brand cb on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<Brand> selectBrandByCid(Long cid);

    //添加brand
    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertBrandAndCategory(@Param("cid")Long cid,@Param("bid")Long bid);
}
