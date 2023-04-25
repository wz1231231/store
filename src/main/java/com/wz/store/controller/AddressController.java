package com.wz.store.controller;

import com.wz.store.entity.Address;
import com.wz.store.service.IAddressService;
import com.wz.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/addresses")
public class AddressController extends BaseController{
    @Autowired
    private IAddressService addressService;
    @RequestMapping("/addNewAddress")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session){
        Integer uid = getUserIdFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(uid,username,address);
        return new JsonResult<>(OK);
    }
}
