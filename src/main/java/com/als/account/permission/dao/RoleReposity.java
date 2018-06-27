package com.als.account.permission.dao;

import com.als.account.permission.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleReposity extends JpaRepository<RoleEntity,Long> ,JpaSpecificationExecutor<RoleEntity>{
   /*@Query(value = "select r from acc_role r where r.name like '%?1%'")
    List<RoleEntity> findByNameLike(String name);*/
}
