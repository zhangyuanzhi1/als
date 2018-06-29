package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
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

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.CourierService;
import cn.itcast.bos.web.action.common.BaseAction;
//快递员的action
@Controller
@Scope("prototype")
public class CourierAction extends BaseAction<Courier>{
	//注入service
	@Autowired
	private CourierService courierService;

	//保存业务
	@Action(value="courier_add",results={
			@Result(type=REDIRECT,location="/pages/base/courier.html")
	})
	public String add(){
		//调用业务层
		courierService.saveCourier(model);
		
		return SUCCESS;
	}
	
	/*//属性驱动接收分页的两个参数
	private int page;//当前页码
	private int rows;//每页最大记录数
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	*/
	//组合条件分页查询
	@Action("courier_listPage")
	public String listPage(){
		//第一步：封装请求参数
		//1）请求的分页条件bean
		Pageable pageable=new PageRequest(page-1, rows);
		//2)请求的业务条件bean
//		Specification<Courier> spec=null;
		//Specification:封装JPA的Criteria方案
		Specification<Courier> spec=new Specification<Courier>() {//匿名内部类

			@Override
			//参数1：根查询对象，从表的角度来说，是主表 from roottable ,DeteachCrexxx.classname("root")
			//参数2：简单条件拼接对象
			//参数3：复杂条件构造对象-用
			//返回：条件的拼接结果
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				//定义集合
				List<Predicate> andPredicate=new ArrayList<>();//放and的拼接，这里用
				List<Predicate> orPredicate=new ArrayList<>();//放or的拼接,不用
				
				//===================单表
				//工号
				if(StringUtils.isNotBlank(model.getCourierNum())){
					//参数1：路径表达式，类似于属性
					//参数2：属性的值
					//类似于Hibernate：Restictions.eq("属性",属性值)
					//相当sql：courierNum=?
//					Predicate p = cb.equal(root.get("courierNum"), model.getCourierNum());
					Predicate p = cb.equal(root.get("courierNum").as(String.class), model.getCourierNum());
					andPredicate.add(p);
				}
				
				//所属单位
				if(StringUtils.isNotBlank(model.getCompany())){
					//相当于sql：company like ?
					Predicate p = cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%");
					andPredicate.add(p);
				}
				
				//类型
				if(StringUtils.isNotBlank(model.getType())){
					//相当于sql：type like ?
					Predicate p = cb.like(root.get("type").as(String.class), "%"+model.getType()+"%");
					andPredicate.add(p);
				}
				
				//===================多表
				if(model.getStandard()!=null){
					//快递员对象连接收派标准对象
					//参数1：连接的属性
					//参数2：连接的类型，默认是内联，可以省略
//					Join<Courier, Standard> standardJoin = root.join(root.getModel().getSingularAttribute("standard", Standard.class), JoinType.INNER);
					//简化，直接连接某属性，默认是内连接
					Join<Object, Object> standardJoin = root.join("standard");
					
					//收派标准的名称
					if(StringUtils.isNotBlank(model.getStandard().getName())){
						Predicate p = cb.like(standardJoin.get("name").as(String.class), "%"+model.getStandard().getName()+"%");
						andPredicate.add(p);
					}
				}
				
				//积木编程
				//所有条件的整合拼接
//				cb.and(restrictions);//都是and条件
//				cb.or(restrictions);//都是or条件
				
				Predicate andP = cb.and(andPredicate.toArray(new Predicate[0]));
				
				/*
				 * (a and b) or (c or d)
				 * p1 =cb.and(a,b);
				 * p2=cb.or(c,d);
				 * p=cb.or(p1,p2);
				 * 
				 */
				
				
				return andP;
			}
			
		};
		//第二步：调用业务层查询分页结果
		Page<Courier> pageResponse = courierService.findCourierListPage(spec, pageable);
		//第三步：重新组装数据并压入root栈顶
		pushPageDataToValuestackRoot(pageResponse);
		
		//第四步：返回json类型结果集上
		return JSON;
	}
	
	//属性驱动封装ids
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	//批量作废
	@Action("courier_deleteBatch")
	public String deleteBatch(){
		//定义结果map
		Map<String, Object> resultMap=new HashMap<>();
		
		try {
			//调用业务层删除
			courierService.deleteBatch(ids);
			//成功：
			resultMap.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			//失败：
			resultMap.put("result", false);
		}
		
		//将结果map压入root栈顶
		ActionContext.getContext().getValueStack().push(resultMap);
		
		return JSON;
	}
	
	
	//列出所有没有作废的快递员
	@Action("courier_listNoDeltag")
	public String listNoDeltag(){
		
		List<Courier> list= courierService.findCourierListNoDeltag();
		
		//压入root栈顶
		ActionContext.getContext().getValueStack().push(list);
		
		return JSON;
	}

	
}
