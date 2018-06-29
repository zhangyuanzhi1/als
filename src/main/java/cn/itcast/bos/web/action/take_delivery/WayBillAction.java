package cn.itcast.bos.web.action.take_delivery;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.service.take_delivery.WayBillService;
import cn.itcast.bos.web.action.common.BaseAction;

//运单action
@Controller
@Scope("prototype")
public class WayBillAction extends BaseAction<WayBill>{
	//注入service
	@Autowired
	private WayBillService wayBillService;
	//快速录入
	@Action("wayBill_addquick")
	public String addquick(){
		Map<String, Object> resultMap=new HashMap<>();
		try {
			//调用业务层
			wayBillService.saveWayBillQuick(model);
			//成功
			resultMap.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			//失败
			resultMap.put("result", false);
		}
		
		//压入root栈顶
		ActionContext.getContext().getValueStack().push(resultMap);
		return JSON;
	}
	
	//属性驱动获取两个查询参数
	private String fieldName;
	private String fieldValue;
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}



	//分页列表查询
	@Action("wayBill_listPage")
	public String listPage(){
		//先封装分页条件对象
//		Pageable pageable=new PageRequest(page-1, rows);
		Pageable pageable=new PageRequest(page-1, rows, new Sort(Direction.DESC, "id"));//倒序排序
		
		//分页结果响应对象
		Page<WayBill> pageResponse=null;
		
		//判断
		if(StringUtils.isBlank(fieldValue)){
			//调用业务层获取分页响应对象---走数据库逻辑
			pageResponse= wayBillService.findWayBillListPage(pageable);
		}else{
			//走es索引库查询逻辑
			pageResponse= wayBillService.findWayBillListPage(pageable,fieldName,fieldValue);
		}
		
		//将分页查询结果压入root栈顶
		pushPageDataToValuestackRoot(pageResponse);
		
		return JSON;
	}
}
