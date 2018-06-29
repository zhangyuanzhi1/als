package cn.itcast.bos.service.impl.take_delivery;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaRepository;
import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.dao.take_delivery.OrderRepository;
import cn.itcast.bos.dao.take_delivery.WorkBillRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.bos.domain.take_delivery.WorkBill;
import cn.itcast.bos.service.take_delivery.OrderService;
import cn.itcast.crm.domain.Customer;
import cn.itcast.utils.Constants;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	//注入dao
	@Autowired
	private OrderRepository orderRepository;
	//注入区域dao
	@Autowired
	private AreaRepository areaRepository;
	
	//注入定区dao
	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	
	
	//注入工单dao
	@Autowired
	private WorkBillRepository workBillRepository;

	@Override
	public void saveOrder(Order order) {
		
		//为了方便测试,只打印数据
		if(1==1){
			System.out.println(order);
			return;
		}
		
		
		//----要将区域的对象变成持久态才可以（需要id）
		//发送区域
		Area sendAreaPersist = areaRepository.findByProvinceAndCityAndDistrict(
				order.getSendArea().getProvince()
				,order.getSendArea().getCity()
				,order.getSendArea().getDistrict()
				);
		
		order.setSendArea(sendAreaPersist);
		//收件区域
		Area recAreaPersist = areaRepository.findByProvinceAndCityAndDistrict(
				order.getRecArea().getProvince()
				,order.getRecArea().getCity()
				,order.getRecArea().getDistrict()
				);
		
		order.setRecArea(recAreaPersist);
		
		//处理一些默认值
		//订单类型，默认2，人工分单
		order.setOrderType("人工分单");
		
		//订单状态，默认是待取件
		order.setStatus("待取件");
		
		//订单号,一般是一定规则生成
		order.setOrderNum(UUID.randomUUID().toString());
		//订单时间,当前时间
		order.setOrderTime(new Date());
		
		//给客户的短信内容，拼接
		order.setSendMobileMsg("您的订单已经收到");
		
		//调用dao保存订单
		orderRepository.save(order);
		
		
		//获取下单的地址：区域+详细地址
		String address=order.getSendArea().getProvince()
				+order.getSendArea().getCity()
				+order.getSendArea().getDistrict()
				+order.getSendAddress();
		
		//=========自动分单================
		//规则1：客户地址完全匹配
		/*
		 * 业务：调用CRM接口，通过客户地址，返回定区编号；
		 * 根据定区编号，查询定区
		 * 根据定区查询关联的快递员
		 * 给快递员下一个工单（新建一个工单数据保存到db）
		 */
		Customer customer=null;
		try {
			customer = WebClient.create(Constants.CRM_MANAGEMENT_URL+"/services/customerservice/customers")
			.path("/fixedareaid/address")
			.path("/"+address)
			.type(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.get(Customer.class);
		} catch (Exception e) {
			e.printStackTrace();
			//log4j
			System.out.println("管理员您好，调用crm接口失败！");
		}
		
		//判断
		if(null!=customer){
			//获取定区编号
			String fixedAreaId = customer.getFixedAreaId();
			if(StringUtils.isNotBlank(fixedAreaId)){
				//获取定区
				FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
				if(null!=fixedArea){
					//根据定区获取快递员:导航查询
					Set<Courier> couriers = fixedArea.getCouriers();
					/*
					 * 后续业务：筛选快递员--维度
					 * 规则1：根据上下班时间
					 * 规则2：根据收派标准
					 * 规格3：有没有请假
					 * 规则4：有没有替班
					 * .....
					 */
					//随便获取一个
					if(null!=couriers){
						//找到快递员
						Courier courier = couriers.iterator().next();
						//1）修改订单数据
						//订单类型，默认2，人工分单
						order.setOrderType("自动分单");
						//订单关联快递员：说明该订单已经被派出去了
						order.setCourier(courier);
						//2）新建一个工单
						saveWorkBill(order, courier);
						
						//当走完一个规则后，后面的规则无需再走。
						return;
					}
					
				}
			}
		}
		
		//规则2:客户下单地址中的关键字匹配规则
		//根据客户的下单的地址寻找区域(略，上面已经实现)
		if(null!=sendAreaPersist){
			//找到区域,根据区域找到下面所有的分区,导航查询
			Set<SubArea> subareas = sendAreaPersist.getSubareas();
			//遍历分区集合，去寻找需要的分区
			for (SubArea subArea : subareas) {
				//寻找中...字符串的包含判断
				if(order.getSendAddress().contains(subArea.getKeyWords())){
					//找到了需要的分区
					//就可以去找定区了
					FixedArea fixedArea = subArea.getFixedArea();
					if(null!=fixedArea){
						//根据定区获取快递员:导航查询
						Set<Courier> couriers = fixedArea.getCouriers();
						/*
						 * 后续业务：筛选快递员--维度
						 * 规则1：根据上下班时间
						 * 规则2：根据收派标准
						 * 规格3：有没有请假
						 * 规则4：有没有替班
						 * .....
						 */
						//随便获取一个
						if(null!=couriers){
							//找到快递员
							Courier courier = couriers.iterator().next();
							//1）修改订单数据
							//订单类型，默认2，人工分单
							order.setOrderType("自动分单");
							//订单关联快递员：说明该订单已经被派出去了
							order.setCourier(courier);
							//2）新建一个工单
							saveWorkBill(order, courier);
							
							//当走完一个规则后，后面的规则无需再走。
							return;
						}
						
					}
					//停止寻找
					break;
				}
			}
			
		}
		
	}

	//下工单
	private void saveWorkBill(Order order, Courier courier) {
		WorkBill workBill=new WorkBill();
//							workBill.setId(id);//主键
		workBill.setType("新");// 工单类型 新,追,销，
		workBill.setPickstate("新单");// 取件状态,是快递员改变
		workBill.setAttachbilltimes(0);;//追单次数,默认是0
		workBill.setBuildtime(new Date());//下工单的时间，当前时间
		workBill.setOrder(order);//工单关联订单
		workBill.setRemark(order.getRemark());//给小哥捎话,数据库的字段的冗余设计
//							workBill.setSmsNumber(smsNumber);//短信序号，工单和短信关联
		workBill.setCourier(courier);//工单关联快递员
		
		//保存
		workBillRepository.save(workBill);
	}

}
