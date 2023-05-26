package com.example.client;

import com.example.model.Address;
import com.example.model.Result;
import com.example.model.Vaccine;
import com.example.model.Worker;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@FeignClient(name = "admin", url = "http://localhost:1111")
public interface AdminClient {

    @ApiOperation(value = "根据疫苗id获取疫苗信息")
    @GetMapping("/admin/api/getVaccine/{id}")
    public Vaccine getVaccine(@PathVariable("id") int id);

    @ApiOperation("根据疫苗实体类id修改疫苗实体")
    @GetMapping("/admin/api/updateVaccine")
    public boolean updateVaccine(@RequestBody Vaccine vaccine);

    @ApiOperation(value = "根据工作人员id获取工作人员信息")
    @GetMapping("/admin/api/getWorkerById/{id}")
    public Worker getWorkerById(@PathVariable("id") int id);

    @ApiOperation(value = "根据工作地点id获取工作地点信息")
    @GetMapping("/admin/api/getAddressById/{id}")
    public Address getAddressById(@PathVariable("id") int id);

    @ApiOperation("根据用户名获取用户id")
    @GetMapping("/admin/api/getUserByName/{username}")
    public List getUserByName(@PathVariable String username);

    @ApiOperation("根据疫苗名称获取疫苗id")
    @GetMapping("/admin/api/getVaccineByName/{vacname}")
    public List getVaccineByName(@PathVariable("vacname") String vacname);

    @ApiOperation("根据工作人员名称获取工作人员")
    @GetMapping("/admin/api/getWorker/{workerName}")
    public List getWorker(@PathVariable("workerName") String workerName);



    @ApiOperation("根据接种点名称获取信息")
    @GetMapping("/admin/api/getAddress/{address}")
    public List getAddress(@PathVariable("address") String address);

}
