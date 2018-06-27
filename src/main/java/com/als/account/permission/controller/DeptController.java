package com.als.account.permission.controller;

import com.als.account.permission.dao.entity.DeptEntity;
import com.als.account.permission.service.DeptService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "部门管理")
@RestController
@RequestMapping("/dept")
public class DeptController extends BasicController{
    @Autowired
    private DeptService deptService;
    //新增部门
    @ApiOperation(value = "部门新增",notes = "部门新增")
    @ApiResponses(@ApiResponse(code = 200,message = "部门新增"))
    @RequestMapping(path = "add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addDept(@RequestBody DeptEntity deptEntity){
      if (deptEntity ==null){
          return "请录入数据";
      }
    deptService.addDept(deptEntity);
    return "add new deptEntity";
    }
    //修改部门
    @ApiOperation(value = "部门修改",notes = "部门修改")
    @ApiResponses(@ApiResponse(code = 200,message = "部门修改"))
    @RequestMapping(path = "update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateDept(@RequestBody DeptEntity deptEntity){
        if (deptEntity ==null){
            return "请录入数据";
        }
        deptService.updateDept(deptEntity);
        return "update new deptEntity";
    }
    //查询部门
    @ApiOperation(value = "部门列表",notes = "部门列表")
    @ApiResponses(@ApiResponse(code = 200,message = "部门列表"))
    @GetMapping("/queryDept")
    public List<DeptEntity> findAllDept(){
        List<DeptEntity> list=deptService.findAllDept();
        return list;
    }
    //通过id查询

}
