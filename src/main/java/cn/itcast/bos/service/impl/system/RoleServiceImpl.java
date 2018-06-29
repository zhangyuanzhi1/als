package cn.itcast.bos.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.RoleRepository;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.service.system.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	//注入dao
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> findRole() {
		return roleRepository.findAll();
	}

}
