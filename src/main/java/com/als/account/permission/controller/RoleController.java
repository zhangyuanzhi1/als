package com.als.account.permission.controller;


import com.als.account.permission.dao.entity.RoleEntity;
import com.als.account.permission.service.RoleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Api(description = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController extends BasicController {
    /* @Autowired
     private RoleReposity RoleReposity;
     */
    @Autowired
    private RoleService roleService;
    // private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    // static Map<Integer, RoleEntity> users = Collections.synchronizedMap(new HashMap<Integer, RoleEntity>());

    @ApiOperation(value = "查询角色", notes = "查询角色列表")
    @ApiResponses(@ApiResponse(code = 200, message = "查询角色"))
    @RequestMapping(path = "queryrole", method = RequestMethod.GET)
    public List<RoleEntity> queryRole() {
        //logger.info("开始查询角色信息");
        // List<RoleEntity> roleEntities = roleService.queryRole();
        List<RoleEntity> roleEntities = roleService.findAll();
        return roleEntities;

    }

    @ApiOperation(value = "创建角色")
    @ApiImplicitParam(name = "roleEntity", value = "用户详细实体role", required = true, dataType = "roleEntity")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void postUser(@RequestBody RoleEntity roleEntity) {
        /*RoleEntity role1 = new RoleEntity();*/
        roleService.createRole(roleEntity);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RoleEntity queryRoleById(@PathVariable Long id) {
        Optional<RoleEntity> a = roleService.queryById(id);
        //boolean present = a.isPresent();
        //RoleEntity roleEntity = a.orElse(new RoleEntity());
        RoleEntity roleEntity = a.get();
        return roleEntity;
        //new ResponseEntity<>(roleService.queryById(id), HttpStatus.OK);

    }

    /* @RequestMapping(method = RequestMethod.POST)
     public Object create(HttpServletRequest request) {
         return "POST a user";
     }*/
    @ApiOperation(value = " 更新角色")
    @ApiImplicitParam(name = "roleEntity", value = "用户详细实体role", required = false, dataType = "roleEntity")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody RoleEntity roleEntity) {

        roleService.updateRole(roleEntity);
        return "put a roleEntity";
    }
   /* @ApiOperation(value = " 通过姓名查询角色")
    @ApiImplicitParam(name = "roleEntity", value = "用户详细实体role", required = true, dataType = "Stirng")
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public List<RoleEntity> findRoleByName(@PathVariable String name){
        List<RoleEntity> roleEntityList= roleService.findByNameLike("%"+name+"%");

        return roleEntityList;
    }*/
   @ApiOperation(value = "条件查询角色", notes = "条件查询角色列表")
   @ApiResponses(@ApiResponse(code = 200, message = "条件查询角色"))
   @RequestMapping(path = "pagequery", method = RequestMethod.GET)
    public String pageQuery(@PathVariable int page
                            ,@PathVariable int rows){
      // PageRequest pageRequest = new PageRequest(page - 1, rows);
       Specification<RoleEntity> specification=new Specification<RoleEntity>(){
           @Override
           public Predicate toPredicate(Root<RoleEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
               Predicate predicate=null;
               
               return null;
           }
       };
    return null;
   }
}
