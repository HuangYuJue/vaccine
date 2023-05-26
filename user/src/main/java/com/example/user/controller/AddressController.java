package com.example.user.controller;

import com.example.model.Address;
import com.example.model.Result;
import com.example.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("findAddress")
    public Result findAddress(){
        List<Address> list = addressService.findAddress();
        return Result.ok(list);
    }
}
