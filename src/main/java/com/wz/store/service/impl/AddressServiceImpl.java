package com.wz.store.service.impl;

import com.wz.store.entity.Address;
import com.wz.store.mapper.AddressMapper;
import com.wz.store.service.IAddressService;
import com.wz.store.service.IDistrictService;
import com.wz.store.service.ex.AddressCountLimitException;
import com.wz.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer count = addressMapper.countByUid(uid);
        System.out.println(maxCount);
        if (count >= maxCount){
            throw new AddressCountLimitException("用户收货地址超出上限");
        }
        //对address对象中的数据进行补全:省市区
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        address.setUid(uid);
        address.setIsDefault(count == 0 ? 1:0);//1表示默认，0表示不是默认
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());

        Integer rows = addressMapper.insert(address);
        if (rows != 1){
            throw new InsertException("插入用户的收货地址产生未知异常");
        }


    }
}
