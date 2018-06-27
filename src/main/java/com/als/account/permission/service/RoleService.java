package com.als.account.permission.service;

import com.als.account.permission.dao.entity.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleEntity> queryRole();

    List<RoleEntity> findAll();




    void createRole(RoleEntity roleEntity);

    Optional<RoleEntity> queryById(Long id);

    void updateRole(RoleEntity roleEntity);

    List<RoleEntity> findByNameLike(String s);
}
