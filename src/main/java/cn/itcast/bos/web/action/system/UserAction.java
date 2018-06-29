package cn.itcast.bos.web.action.system;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>{

	//登录操作（认证）
	@Action(value="user_login",results={
			@Result(type=REDIRECT,location="/index.html")
			,@Result(name=INPUT,type=REDIRECT,location="/login.html")
	})
	public String login(){
		//基于shiro进行登录
		
		//1.获取subject对象
		Subject subject = SecurityUtils.getSubject();
		
		//2.创建一个令牌对象
		AuthenticationToken token=new UsernamePasswordToken(model.getUsername(), model.getPassword());
		
		try {
			//3. 登录、认证
			subject.login(token);
			//登录成功
			return SUCCESS;//跳到主页index
		} catch (UnknownAccountException e) {
			//登录失败:帐号不存在
			e.printStackTrace();
			return INPUT;//原来的表单页面
		} catch (IncorrectCredentialsException e) {
			//登录失败：密码不正确
			e.printStackTrace();
			return INPUT;//原来的表单页面
		} catch (AuthenticationException e) {
			//登录失败
			e.printStackTrace();
			return INPUT;//原来的表单页面
		}
		
	}
	
	//用户退出注销
	@Action(value="user_logout",results={
			@Result(type=REDIRECT,location="/login.html")
	})
	public String logout(){
		//目标：清除shiro的登录状态
		SecurityUtils.getSubject().logout();
		/**
		 * 清除的session是org.apache.shiro.session.Session。
		 * 
		 * 1）如果是web应用，该session可以等同于httpSession，存放了用户对象在httpSession中。
		 * 2）非web应用中，该session默认会是map，存放了用户对象在内存中。
		 */
		
		return SUCCESS;
	}
}
