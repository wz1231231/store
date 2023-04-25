package com.wz.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictServiceImplTests {
    @Autowired
    private IDistrictService districtService;
    @Test
    public void getByParent(){
        System.out.println(districtService.getByParent("86"));
    }
}
