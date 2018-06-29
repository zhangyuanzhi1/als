package cn.itcast.bos.service.impl.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.dao.base.TakeTimeRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.FixedAreaService;
//定区业务层实现
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
	//注入dao
	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	
	//注入快递员dao
	@Autowired
	private CourierRepository courierRepository;
	
	//注入取派时间dao
	@Autowired
	private TakeTimeRepository takeTimeRepository;
	
	@Override
	public void saveFixedArea(FixedArea fixedArea) {
		fixedAreaRepository.save(fixedArea);

	}
	@Override
	public Page<FixedArea> findFixedAreaListPage(Specification<FixedArea> spec, Pageable pageable) {
		return fixedAreaRepository.findAll(spec, pageable);
	}
	@Override
	public void associationCourierToFixedArea(FixedArea fixedArea, Integer courierId, Integer takeTimeId) {
		//使用hibernate快照更新：持久态对象的关联操作
		//持久态对象
		//定区持久态对象
		FixedArea fa = fixedAreaRepository.findOne(fixedArea.getId());
		//快递员的持久态对象
		Courier courier = courierRepository.findOne(courierId);
		
		//取派时间的持久态对象
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		
		//关联
		//快递员关联取派时间-单向关联
		courier.setTakeTime(takeTime);
	
		//定区关联快递员（因为快递员实体类放弃了外键维护权mapBy，因此，不能使用快递员去关联定区）
		fa.getCouriers().add(courier);
		
		//等flush
		
	}

}
