package com.als.account.permission.dao.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ACC_MENU")
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name; // 角色名称
    @Column(name = "DESCRIPTION")
    private String description; // 描述
    @Column(name = "PAGE")
    private String page; // 访问路径
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name="create_by")
    private String createBy;
    @Column(name="update_by")
    private String updateBy;
    //与权限多对多关联
    @ManyToMany(mappedBy = "menus")
    private Set<PermissionEntity> permissions = new HashSet<PermissionEntity>(0);
    //自关联
    @OneToMany(mappedBy = "parentMenu")
    private Set<MenuEntity> childrenMenus = new HashSet<MenuEntity>();
    @ManyToOne
    @JoinColumn(name = "PID")
    private MenuEntity parentMenu;

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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
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

    public Set<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionEntity> permissions) {
        this.permissions = permissions;
    }

    public Set<MenuEntity> getChildrenMenus() {
        return childrenMenus;
    }

    public void setChildrenMenus(Set<MenuEntity> childrenMenus) {
        this.childrenMenus = childrenMenus;
    }

    public MenuEntity getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(MenuEntity parentMenu) {
        this.parentMenu = parentMenu;
    }

}
