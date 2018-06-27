package com.als.account.permission.service.imp;

import com.als.account.permission.dao.DeptReposity;
import com.als.account.permission.dao.entity.DeptEntity;
import com.als.account.permission.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeptServiceImp implements DeptService {
    @Autowired
    private DeptReposity deptReposity;
    @Override
    public void addDept(DeptEntity deptEntity) {
        deptReposity.save(deptEntity);
    }

    @Override
    public List<DeptEntity> findAllDept() {
        return deptReposity.findAll();
    }

    @Override
    public void updateDept(DeptEntity deptEntity) {
        Optional<DeptEntity> byId = deptReposity.findById(deptEntity.getDeptId());
        DeptEntity deptEntity1 = byId.get();
        deptEntity1.setDeptName(deptEntity.getDeptName());
        deptEntity1.setDeptManager(deptEntity.getDeptManager());
        deptEntity1.setCreateBy(deptEntity.getCreateBy());
        deptEntity1.setUpdateBy(deptEntity.getUpdateBy());
    }
}
