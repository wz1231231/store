package com.wz.store.controller;


import com.wz.store.entity.District;
import com.wz.store.service.IDistrictService;
import com.wz.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/districts")
public class DistrictController extends BaseController{

    @Autowired
    private IDistrictService districtService;
    @RequestMapping({"/",""})
    public JsonResult<List<District>> getByParent(String parent){
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<>(OK,data);
    }

}
