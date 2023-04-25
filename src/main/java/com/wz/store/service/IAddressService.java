package com.wz.store.service;

import com.wz.store.entity.Address;

public interface IAddressService {
    /**
     *  添加收货地址
     * @param uid 用户id
     * @param username 创建人
     * @param address 地址信息
     */
    void addNewAddress(Integer uid, String username , Address address);
}
