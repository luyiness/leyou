package com.leyou.item.service;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date:2021/03_4:11 下午
 * @Description：
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam record = new SpecParam();
        record.setGroupId(gid);
        record.setCid(cid);
        record.setGeneric(generic);
        record.setSearching(searching);
        return this.specParamMapper.select(record);
    }

    /**
     * 根据分类id查询分组
     */
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return this.groupMapper.select(specGroup);
    }

    /**
     * 根据条件查询规格参数
     */
    public List<SpecParam> queryParams(Long gid) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        return this.paramMapper.select(param);
    }
}
