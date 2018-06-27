package com.als.account.permission.dao.entity;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="acc_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id; // 主键
    @Column(name = "GENDER")
    private String gender; // 性别
    @Column(name = "PASSWORD")
    private String password; // 密码
    @Column(name = "REMARK")
    private String remark; // 备注
    @Column(name = "STATUS")
    private String status; // 状态
    @Column(name = "TELEPHONE")
    private String telephone; // 联系电话
    @Column(name = "USERNAME", unique = true)
    private String username; // 登陆用户名
    @Column(name = "NICKNAME")
    private String nickname; // 真实姓名
    @Column(name="create_by")
    private String createBy;
    @Column(name="update_by")
    private String updateBy;
    //与role相关联
    @ManyToMany
    @JoinTable(name = "ACC_USER_ROLE", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
    private Set<RoleEntity> roles = new HashSet<RoleEntity>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
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
}
