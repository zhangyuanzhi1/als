package com.als.account.permission.dao.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ACC_PERMISSION")
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name; // 权限名称
    @Column(name = "CODE")
    private String code; // 权限关键字，用于权限控制
    @Column(name = "DESCRIPTION")
    private String description; // 描述
    @Column(name = "MENUID")
    private int menuid;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name="create_by")
    private String createBy;
    @Column(name="update_by")
    private String updateBy;

    @ManyToMany(mappedBy = "permissions")
    private Set<RoleEntity> roles = new HashSet<RoleEntity>(0);
   // private int pid;

    @ManyToMany
    @JoinTable(name = "ACC_PERMISSION_MENU", joinColumns = {
            @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
            @JoinColumn(name = "MENU_ID", referencedColumnName = "ID") })
    private Set<MenuEntity> menus = new HashSet<MenuEntity>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMenuid() {
        return menuid;
    }

    public void setMenuid(int menuid) {
        this.menuid = menuid;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public Set<MenuEntity> getMenus() {
        return menus;
    }

    public void setMenus(Set<MenuEntity> menus) {
        this.menus = menus;
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
