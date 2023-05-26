package com.example.admin.controller;

import com.example.admin.service.*;
import com.example.model.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class AdminApiController {

    @Resource
    private VaccineService vaccineService;
    @Resource
    private UserService userService;
    @Resource
    private WorkerService workerService;
    @Resource
    private AddressService addressService;

    @ApiOperation(value = "根据疫苗id获取疫苗信息")
    @GetMapping("getVaccine/{id}")
    public Vaccine getVaccine(@PathVariable int id){
        Vaccine vaccine = vaccineService.getById(id);
        System.out.println("vaccine:"+vaccine);
        return vaccine;
    }

    @ApiOperation(value = "根据工作人员id获取工作人员信息")
    @GetMapping("getWorkerById/{id}")
    public Worker getWorkerById(@PathVariable int id){
        Worker worker = workerService.getById(id);
        return worker;
    }

    @ApiOperation(value = "根据工作地点id获取工作地点信息")
    @GetMapping("getAddressById/{id}")
    public Address getAddressById(@PathVariable int id){
        Address address = addressService.getById(id);
        return address;
    }

    @ApiOperation("根据疫苗实体类id修改疫苗实体")
    @PostMapping("/updateVaccine")
    public boolean updateVaccine(@RequestBody Vaccine vaccine){
        boolean update = vaccineService.update(vaccine);
        return update;
    }

    @ApiOperation("根据用户名获取用户id")
    @GetMapping("getUserByName/{username}")
    public List getUserByName(@PathVariable String username){
        List<Integer> list = userService.getUserByName(username);
        return list;
    }

    @ApiOperation("根据疫苗名称获取疫苗id")
    @GetMapping("getVaccineByName/{vacname}")
    public List getVaccineByName(@PathVariable String vacname){
        List<Integer> ids = vaccineService.getVaccineByName(vacname);
        return ids;
    }

    @ApiOperation("根据工作人员名称获取工作人员")
    @GetMapping("getWorker/{workerName}")
    public List getWorker(@PathVariable String workerName){
        List<Worker> list = workerService.getWorker(workerName);
        List<Integer> ids = new ArrayList<>();
        for (Worker item : list){
            ids.add(item.getId());
        }
        return ids;
    }

    @ApiOperation("根据接种点名称获取工作人员id")
    @GetMapping("getAddress/{address}")
    public List getAddress(@PathVariable String address){
        List<Integer> list = addressService.getAddress(address);
        return list;
    }
}
