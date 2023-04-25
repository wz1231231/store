package com.wz.store.service;

import com.wz.store.entity.District;

import java.util.List;

public interface IDistrictService {
    /**
     * 根据父代号来查询区域信息(省市区)
     * @param parent 父代号
     * @return 多个区域信息
     */
    List<District> getByParent(String parent);

    String getNameByCode(String code);
}
