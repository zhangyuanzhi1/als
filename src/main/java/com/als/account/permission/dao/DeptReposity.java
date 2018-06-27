package com.als.account.permission.dao;


import com.als.account.permission.dao.entity.DeptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeptReposity extends JpaRepository<DeptEntity,Long>,JpaSpecificationExecutor<DeptEntity> {
}
