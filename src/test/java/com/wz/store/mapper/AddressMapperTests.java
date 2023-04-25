package com.wz.store.mapper;

import com.wz.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;
    @Test
    public void insert(){
        Address address = new Address();
        address.setName("李四");
        address.setUid(12);
        address.setProvinceName("湖北省");
        address.setProvinceCode("123000");
        address.setCityName("武汉市");
        address.setCityCode("123100");
        address.setAreaName("鼓楼区");
        address.setAreaCode("123111");
        address.setZip("100011");
        address.setAddress("东方大道");
        address.setPhone("15571666325");
        address.setTel("123456");
        address.setTag("家");
        addressMapper.insert(address);
    }
    @Test
    public void countByUid(){
        System.out.println(addressMapper.countByUid(12));
    }

}
