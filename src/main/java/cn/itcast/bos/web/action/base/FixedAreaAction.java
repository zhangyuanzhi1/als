package cn.itcast.bos.web.action.base;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.crm.domain.Customer;
import cn.itcast.utils.Constants;
//定区action
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea>{
	//注入service
	@Autowired
	private FixedAreaService fixedAreaService;

	//保存一个定区
	@Action(value="fixedArea_add",results={
			@Result(type=REDIRECT,location="/pages/base/fixed_area.html")
	})
	public String add(){
		fixedAreaService.saveFixedArea(model);
		return SUCCESS;
	}
	
	//属性驱动接收
	private String keyWords;//分区关键字
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}


	//组合条件分页查询
	@Action("fixedArea_listPage")
	public String listPage(){
		//目标：使用Specification的方案进行组合条件分页查询
		//第一步：封装参数和条件
		//1)分页条件
		Pageable pageable=new PageRequest(page-1, rows);
		//2)业务条件
		Specification<FixedArea> spec=new Specification<FixedArea>() {
			
			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				//直接构造出具有and或or的关系的Predicate
				Predicate conjunctionPredicate = cb.conjunction();//and
				Predicate disjunctionPredicate = cb.disjunction();//or
				
				
				//====单表
				//定区编号
				if(StringUtils.isNotBlank(model.getId())){
					conjunctionPredicate.getExpressions().add(
							cb.equal(root.get("id").as(String.class), model.getId())
					);
				}
				//所属单位
				if(StringUtils.isNotBlank(model.getCompany())){
					conjunctionPredicate.getExpressions().add(
							cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%")
					);		
				}
				//====多表
				/*if(model.getSubareas()!=null){
					Join<Object, Object> subareaJoin = root.join("subareas");//默认内连接
					//判断某个字段是否存在
					if(StringUtils.isNotBlank(keyWords)){
						cb.like(subareaJoin.get("keyWords").as(String.class), "%"+keyWords+"%");
					}
					
				}*/
				
				return conjunctionPredicate;
			}
		};
		//第二步：调用业务层查询
		Page<FixedArea> pageResponse= fixedAreaService.findFixedAreaListPage(spec,pageable);
		//第三步：将分页数据重新组装并压入root栈顶，交给json类型结果集
		pushPageDataToValuestackRoot(pageResponse);
		
		return JSON;
	}
	
	//列出没有关联任何定区的客户列表
	@Action("fixedArea_listCustomerListNoFixedAreaId")
	public String listCustomerListNoFixedAreaId(){
		//调用WebService：rest接口
		Collection<? extends Customer> list = WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
		.path("/nofixedareaid")
		.accept(MediaType.APPLICATION_JSON)
		.getCollection(Customer.class);
		
		//压入root栈顶
		ActionContext.getContext().getValueStack().push(list);
		//转换为json数组
		return JSON;
	}
	
	//列出已经关联选中定区的客户列表
	@Action("fixedArea_listCustomerListByFixedAreaId")
	public String listCustomerListByFixedAreaId(){
		//调用WebService：rest接口
		Collection<? extends Customer> list = WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
		.path("/fixedareaid")
		.path("/"+model.getId())
		.accept(MediaType.APPLICATION_JSON)
		.getCollection(Customer.class);
		
		//压入root栈顶
		ActionContext.getContext().getValueStack().push(list);
		//转换为json数组
		
		return JSON;
	}
	
	//属性驱动封装获取客户ids
	private String[] customerIds;
	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}


	/**
	 * 定区关联客户的更新操作
	 * @return
	 */
	@Action(value="fixedArea_associationCustomersToFixedArea",results={
			@Result(type=REDIRECT,location="/pages/base/fixed_area.html")
	})
	public String associationCustomersToFixedArea(){
		//处理客户编号s为逗号分割字符串
		String cIds = StringUtils.join(customerIds, ",");//如果一个都没有，则结果"null"
		
		//调用WebService的更新方法
		WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
		.path("/fixedareaid")
		.path("/"+model.getId())
		.path("/"+cIds)//"null"
		.put(null);
		
		return SUCCESS;
	}
	
	
	//属性驱动获取快递员id和时间id
	private Integer courierId;
	private Integer takeTimeId;
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}
	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}

	//定区关联快递员
	@Action(value="fixedArea_associationCourierToFixedArea",results={
			@Result(type=REDIRECT,location="/pages/base/fixed_area.html")
	})
	public String associationCourierToFixedArea(){
		
		//调用业务层
		fixedAreaService.associationCourierToFixedArea(model,courierId,takeTimeId);
		
		return SUCCESS;
	}
	
	
}
