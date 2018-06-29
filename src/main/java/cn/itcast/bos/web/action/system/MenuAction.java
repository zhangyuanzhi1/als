package cn.itcast.bos.web.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.service.system.MenuService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Scope("prototype")
public class MenuAction extends BaseAction<Menu>{
	//注入service
	@Autowired
	private MenuService menuService;

	//列表查询
	@Action("menu_list")
	public String list(){
		List<Menu> list= menuService.findMenuList();
		ActionContext.getContext().getValueStack().push(list);
		return JSON;
	}
	
	//添加
	@Action(value="menu_add",results={
			@Result(type=REDIRECT,location="/pages/system/menu.html")
	})
	public String add(){
		
		menuService.saveMenu(model);
		
		return SUCCESS;
	}
}
