package cn.itcast.bos.service.impl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.TakeTimeRepository;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.TakeTimeService;

@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
	//注入daoR
	@Autowired
	private TakeTimeRepository takeTimeRepository;

	@Override
	public List<TakeTime> findTakeTimeListNoDel() {
		// TODO Auto-generated method stub
		return takeTimeRepository.findByStatus("1");
	}

}
