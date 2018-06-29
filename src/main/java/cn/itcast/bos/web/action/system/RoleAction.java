package cn.itcast.bos.web.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.service.system.RoleService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	//注入service
	@Autowired
	private RoleService roleService;

	@Action("role_list")
	public String list(){
		List<Role> list= roleService.findRole();
		ActionContext.getContext().getValueStack().push(list);
		return JSON;
	}
}
