package com.als.account.permission.dao.entity;


import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name="acc_dept")
public class DeptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long deptId;
    @Column(name="dept_name")
    private String deptName;
    @Column(name="dept_manager")
    private String deptManager;
    @Column(name = "create_by")
    private String createBy;//添加人
    @Column(name = "update_by")
    private String updateBy;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;//更新时间

    /*@Basic
    @Column(name = "dept_id")*/
    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
  /*  @Basic
    @Column(name = "dept_name")*/
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
   /* @Basic
    @Column(name = "dept_manager")*/
    public String getDeptManager() {
        return deptManager;
    }

    public void setDeptManager(String deptManager) {
        this.deptManager = deptManager;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
