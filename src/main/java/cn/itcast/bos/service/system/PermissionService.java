package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Permission;

public interface PermissionService {

	List<Permission> findPermisssionList();

	void savePermission(Permission permission);

}
