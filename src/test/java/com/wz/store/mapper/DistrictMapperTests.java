package com.wz.store.mapper;

import com.wz.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictMapperTests {

    @Autowired
    private DistrictMapper districtMapper;
    @Test
    public void findByParent(){
        System.out.println(districtMapper.findByParent("210100"));
    }
    @Test
    public void findNameByCode(){
        String nameByCode = districtMapper.findNameByCode("610000");
        System.out.println(nameByCode);
    }

}
