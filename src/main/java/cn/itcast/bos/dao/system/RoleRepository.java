package cn.itcast.bos.dao.system;

import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	//根据好多用户对象查询角色列表
	List<Role> findByUsers(Set<User> users);
	//根据一个用户对象查询角色列表
	List<Role> findByUsers(User user);
	
}
