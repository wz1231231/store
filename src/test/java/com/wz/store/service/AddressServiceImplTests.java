package com.wz.store.service;

import com.wz.store.entity.Address;
import com.wz.store.service.impl.AddressServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceImplTests {
    @Autowired
    private AddressServiceImpl addressServiceImpl;
    @Test
    public void addNewAddress(){
        Address address = new Address();
        addressServiceImpl.addNewAddress(13,"王五",address);
    }
}
