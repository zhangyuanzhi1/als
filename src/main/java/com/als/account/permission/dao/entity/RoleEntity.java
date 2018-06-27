package com.als.account.permission.dao.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="acc_role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name; // 角色名称
    @Column(name = "DESCRIPTION")
    private String description; // 描述
    private Boolean enable;//0不启用 ，1//启用
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name="create_by")
    private String createBy;
    @Column(name="update_by")
    private String updateBy;
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<UserEntity>(0);

    @ManyToMany
    @JoinTable(name = "ACC_ROLE_PERMISSION", joinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
            @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID") })
    private Set<PermissionEntity> permissions = new HashSet<PermissionEntity>(0);
    //菜单与角色相关联
    /*@ManyToMany
    @JoinTable(name = "ACC_ROLE_MENU", joinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
            @JoinColumn(name = "MENU_ID", referencedColumnName = "ID") })
    private Set<MenuEntity> menus = new HashSet<MenuEntity>(0);*/
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public Set<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionEntity> permissions) {
        this.permissions = permissions;
    }
}
