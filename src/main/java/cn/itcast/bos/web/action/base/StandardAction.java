package cn.itcast.bos.web.action.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
import cn.itcast.bos.web.action.common.BaseAction;
//@ParentPackage("struts-default")//默认继承的包
//@ParentPackage("json-default")//继承json插件的包，才拥有json类型结果集
//@Namespace("/")//名称空间，访问路径
@Controller//bean组件
@Scope("prototype")//多例
//public class StandardAction extends ActionSupport implements ModelDriven<Standard>{
public class StandardAction extends BaseAction<Standard>{
	/*//声明数据模型对象
	private Standard standard=new Standard();

	@Override
	public Standard getModel() {
		return standard;
	}*/
	
	//注入service
	@Autowired
	private StandardService standardService;
	
	//编写业务方法
	//保存
	@Action(value="standard_add",results={
			//结果集的定义
//			@Result(name="success",type="redirect",location="/pages/base/standard.html")
//			@Result(name=SUCCESS,type=REDIRECT,location="/pages/base/standard.html")
			@Result(type=REDIRECT,location="/pages/base/standard.html")
	})
	public String add(){
		//调用业务层
//		standardService.saveStandard(standard);
		standardService.saveStandard(model);
		
		return SUCCESS;
	}
	
	//接收分页的两个参数
	private int page;//当前页码
	private int rows;//每页最大记录数
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	//分页列表
	/*@Action(value="standard_listPage",results={
//			@Result(name="success",type="json")
//			@Result(name=SUCCESS,type="json")
//			@Result(name=JSON,type=JSON)
	})*/
	@Action("standard_listPage")
	public String listPage(){
		//请求的分页bean对象：可以封装当前页码和每页最大记录数
		//参数1：当前页码的索引zero-based page index 从0开始
		//参数2：每页要显示的最大记录数
		Pageable pageable=new PageRequest(page-1, rows);
		//调用业务层，查询
		//返回的是响应的分页bean对象：存放结果
		Page<Standard> pageResponse = standardService.findStandardListPage(pageable);
		//重新组装数据，因为datagrid要json对象
		Map<String, Object> resultMap=new HashMap<>();
		//1)总记录数
		resultMap.put("total", pageResponse.getTotalElements());
		//2)当前页的记录
		resultMap.put("rows", pageResponse.getContent());
		
		//目标：将map转换成json对象，写入响应。
		//这里使用struts2的json插件，它能自动将root栈顶对象转成json，并写入响应。
		//先将resultMap压入root栈顶
		ActionContext.getContext().getValueStack().push(resultMap);
		//再跳转到json类型的结果集上
		
//		return SUCCESS;
		return JSON;
	}
	
	//列出所有的标准
	@Action("standard_list")
	public String list(){
		//调用业务层查询所有
		List<Standard> standardList= standardService.findStandardList();
		//将列表压入root栈顶
		ActionContext.getContext().getValueStack().push(standardList);
		return JSON;
	}
	

}
