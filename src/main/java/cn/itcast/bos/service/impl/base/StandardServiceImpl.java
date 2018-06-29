package cn.itcast.bos.service.impl.base;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.StandardRepository;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
//收派标准的业务层实现
@Service
@Transactional//事务
public class StandardServiceImpl implements StandardService {
	//注入dao
	@Autowired
	private StandardRepository standardRepository;
	
	
	@Override
	@RequiresRoles({"base1"})//配置必须有某个角色，才能执行该方法
	public void saveStandard(Standard standard) {
		//调用dao
		standardRepository.save(standard);
		
		//伪代码--代码级别的权限控制-有代码侵入性
		System.out.println("我是代码。。。");
		
		//获取subject
		Subject subject = SecurityUtils.getSubject();
		
		//===认证
		if(subject.isAuthenticated()){
			System.out.println("我是代码。。。认证后才能执行");
		}
		
		
		//===授权
		//基于布尔值的判断
		//角色
		if(subject.hasRole("base")){
			System.out.println("我是代码。。.");
		}
		//功能权限
		if(subject.isPermitted("courier:add")){
			System.out.println("我是代码。。.");
		}
		//基于异常的判断
		//角色
		try {
			subject.checkRole("base");
			System.out.println("我是代码。。.");
		} catch (AuthorizationException e) {
			e.printStackTrace();
		}
		//功能权限
		try {
			subject.checkPermission("courier:add");;
			System.out.println("我是代码。。.");
		} catch (AuthorizationException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public Page<Standard> findStandardListPage(Pageable pageable) {
		return standardRepository.findAll(pageable);
	}
	@Override
	public List<Standard> findStandardList() {
		return standardRepository.findAll();
	}

}
