package com.als.account.permission.service.imp;

import com.als.account.permission.dao.RoleReposity;
import com.als.account.permission.dao.entity.RoleEntity;
import com.als.account.permission.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleReposity roleReposity;

    @Override
    public List<RoleEntity> queryRole() {

        return roleReposity.findAll();
    }

    @Override
    public List<RoleEntity> findAll() {
        List<RoleEntity> roleEntityList = roleReposity.findAll();
        return roleEntityList;
    }



    @Override
    public void createRole(RoleEntity roleEntity) {
        roleReposity.save(roleEntity);
    }

    @Override
    public Optional<RoleEntity> queryById(Long id) {
        Optional<RoleEntity> role = roleReposity.findById(id);
        return role;
    }

   @Override
    public void updateRole(RoleEntity roleEntity) {
        Optional<RoleEntity> oneRole = roleReposity.findById(roleEntity.getId());
        //Optional<RoleEntity> oneRole = roleReposity.findOne(roleEntity.getRoleId());
        RoleEntity roleEntity1 = oneRole.get();
       roleEntity1.setName(roleEntity.getName());
        roleEntity1.setCreateBy(roleEntity.getCreateBy());
        roleEntity1.setDescription(roleEntity.getDescription());
        roleEntity1.setCreateDate(roleEntity.getCreateDate());
        roleEntity1.setUpdateBy(roleEntity.getUpdateBy());
        roleEntity1.setUpdateDate(roleEntity.getUpdateDate());
        roleEntity1.setUsers(roleEntity.getUsers());
        roleEntity1.setEnable(roleEntity.getEnable());
    }

    @Override
    public List<RoleEntity> findByNameLike(String name) {
       /* List<RoleEntity> roleEntityList=roleReposity.findByNameLike(name);*/
        return null;
    }


}
