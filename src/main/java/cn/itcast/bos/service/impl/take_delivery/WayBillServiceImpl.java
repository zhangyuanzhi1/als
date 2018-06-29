package cn.itcast.bos.service.impl.take_delivery;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.WayBillRepository;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.indexdao.take_delivery.WayllBillEsRepository;
import cn.itcast.bos.service.take_delivery.WayBillService;

@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {
	//注入SpringDataJPA的运单dao
	@Autowired
	private WayBillRepository wayBillRepository;
	//注入SpringDataElasticSearch的运单dao
	@Autowired
	private WayllBillEsRepository wayllBillEsRepository;

	@Override
	public void saveWayBillQuick(WayBill wayBill) {
		//1）向数据库保存或更新数据
		wayBillRepository.save(wayBill);//数据库生成id
		//2）向es索引库保存或更新数据
		wayllBillEsRepository.save(wayBill);
	}

	@Override
	public Page<WayBill> findWayBillListPage(Pageable pageable) {
		return wayBillRepository.findAll(pageable);
	}

	@Override
	public Page<WayBill> findWayBillListPage(Pageable pageable, String fieldName, String fieldValue) {
		
		//目标：基于es查询数据
		//查询条件
//		QueryBuilder queryBuilder=null;
		//组合条件对象
		BoolQueryBuilder queryBuilder=new BoolQueryBuilder();
		//具体条件1：精确匹配
		QueryBuilder query1=new TermQueryBuilder(fieldName, fieldValue);
		queryBuilder.should(query1);
		QueryBuilder query2=new WildcardQueryBuilder(fieldName, "*"+fieldValue+"*");
//		queryBuilder.should(query2);
		
		//查询返回page对象
		return wayllBillEsRepository.search(queryBuilder, pageable);
	}

}
