package com.wz.store.mapper;

import com.wz.store.entity.District;

import java.util.List;

public interface DistrictMapper {
    /**
     * 根据父代号查询区域信息
     * @param parent 父代号
     * @return 某个父区域下的所以区域列表
     */
    List<District> findByParent(String parent);


    String findNameByCode(String code);
}
