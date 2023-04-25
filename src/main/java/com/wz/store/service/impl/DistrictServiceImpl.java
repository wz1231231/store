package com.wz.store.service.impl;

import com.wz.store.entity.District;
import com.wz.store.mapper.DistrictMapper;
import com.wz.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictMapper districtMapper;
    @Override
    public List<District> getByParent(String parent) {
        return districtMapper.findByParent(parent);
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
