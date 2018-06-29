package cn.itcast.bos.service.base;

import java.util.List;

import cn.itcast.bos.domain.base.Area;

public interface AreaService {

	/**
	 * 
	 * 说明：批量保存区域
	 * @param areaList
	 * @author 传智.BoBo老师
	 * @time：2018年1月16日 下午3:25:02
	 */
	void saveArea(List<Area> areaList);

}
