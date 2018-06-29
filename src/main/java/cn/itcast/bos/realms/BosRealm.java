package cn.itcast.bos.realms;

import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.itcast.bos.dao.system.PermissionRepository;
import cn.itcast.bos.dao.system.RoleRepository;
import cn.itcast.bos.dao.system.UserRepository;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
//自定义realm，用来提供安全数据
@Component("bosRealm")
public class BosRealm extends AuthorizingRealm{
	
	//注入userdao
	@Autowired
	private UserRepository userRepository;
	
	//注入角色dao
	@Autowired
	private RoleRepository roleRepository;
	
	//注入功能权限dao
	@Autowired
	private PermissionRepository permissionRepository;

	//提供授权的安全数据
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权数据提供中。。。");
		
		//==========写死授权数据
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		//设置拥有的功能权限
//		authorizationInfo.addStringPermission("courier:list");
//		//设置拥有的角色权限
//		authorizationInfo.addRole("base");
		
		//==========动态获取授权数据
		//第一步：获取当前登录用户
		//方法1：工具类
//		User user=(User)SecurityUtils.getSubject().getPrincipal();
		//方法2：参数对象获取
		User user=(User) principals.getPrimaryPrincipal();
		//第二步：判断用户类型(登录名)
		if("admin".equals(user.getUsername())){
			//超管：具备所有的角色和功能权限
			//角色
			List<Role> roleList = roleRepository.findAll();
			for (Role role : roleList) {
				//添加角色
				authorizationInfo.addRole(role.getKeyword());
			}
			
			//功能权限
			List<Permission> permissionList = permissionRepository.findAll();
			for (Permission permission : permissionList) {
				//添加功能权限
				authorizationInfo.addStringPermission(permission.getKeyword());
			}
			
		}else{
			//普通用户：必须通过登录名查询相应的角色和功能权限
			//角色
			List<Role> roleList = roleRepository.findByUsers(user);
			for (Role role : roleList) {
				//添加角色
				authorizationInfo.addRole(role.getKeyword());
				//根据角色导航查询功能权限
				Set<Permission> permissionSet = role.getPermissions();
				for (Permission permission : permissionSet) {
					//添加功能权限
					authorizationInfo.addStringPermission(permission.getKeyword());
				}
			}
		}
		
		
		return authorizationInfo;
		//没有提供授权数据
//		return null;
	}

	//提供认证的安全数据
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证数据提供中。。。");
		//目标：从数据源获取用户相关数据
		//1.获取登录名
		String username = ((UsernamePasswordToken)token).getUsername();
		//2.根据登录名到数据库查询用户对象
		User user = userRepository.findByUsername(username);
		
		//3.判断
		if(null==user){
			//用户名不存在-对象不存在
			return null;
		}
		
		//用户名存在，则要封装认证信息对象，返回给安全管理器
		//参数1：首长、当事人、主角-用户对象，将来shiro会将其放入“Session”
		//参数2：凭证，这里就是真实密码
		//参数3：realm对象的唯一名字，不能重复,这里父类提供了实现，类名的随机名
		AuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo
				(
				user, 
				user.getPassword(), 
				super.getName()
				);
		
		return authenticationInfo;
	}

}
