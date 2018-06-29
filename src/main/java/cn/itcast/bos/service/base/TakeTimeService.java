package cn.itcast.bos.service.base;

import java.util.List;

import cn.itcast.bos.domain.base.TakeTime;

public interface TakeTimeService {

	/**
	 * 查询没有删除的列表
	 * @return
	 */
	List<TakeTime> findTakeTimeListNoDel();

}
