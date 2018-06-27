package com.als.account.permission.service;

import com.als.account.permission.dao.entity.DeptEntity;

import java.util.List;

public interface DeptService {
    void addDept(DeptEntity deptEntity);

    List<DeptEntity> findAllDept();

    void updateDept(DeptEntity deptEntity);
}
