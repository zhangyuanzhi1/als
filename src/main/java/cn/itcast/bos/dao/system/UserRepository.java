package cn.itcast.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.system.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	//根据用户名查询用户
	User findByUsername(String username);
}
