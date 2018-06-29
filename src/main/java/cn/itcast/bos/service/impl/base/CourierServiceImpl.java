package cn.itcast.bos.service.impl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;

@Service
@Transactional
public class CourierServiceImpl implements CourierService {
	//注入dao
	@Autowired
	private CourierRepository courierRepository;

	@Override
	public void saveCourier(Courier courier) {
		courierRepository.save(courier);

	}

	@Override
	public Page<Courier> findCourierListPage(Specification<Courier> spec, Pageable pageable) {
		return courierRepository.findAll(spec, pageable);
	}

	@Override
	public void deleteBatch(String ids) {
		//逻辑删除-更新操作-更新标识
		/*
		 * hibernate:update方法，快照更新
		 * SpringDataJPA：save方法（和update差不多）
		 * 这里直接写语句
		 */
		//先按逗号分割
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			courierRepository.updateDeltagById(Integer.parseInt(id), '1');
		}
		
	}

	@Override
	public List<Courier> findCourierListNoDeltag() {
		return courierRepository.findByDeltag('0');
	}

}
