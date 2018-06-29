package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.FixedArea;

//定区业务层接口
public interface FixedAreaService {

	/**
	 * 
	 * 说明：保存一个定区
	 * @param fixedArea
	 * @author 传智.BoBo老师
	 * @time：2018年1月16日 下午4:41:54
	 */
	void saveFixedArea(FixedArea fixedArea);

	/**
	 * 
	 * 说明：组合条件分页查询
	 * @param spec
	 * @param pageable
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2018年1月16日 下午5:08:32
	 */
	Page<FixedArea> findFixedAreaListPage(Specification<FixedArea> spec, Pageable pageable);

	/**
	 * 定区关联快递员
	 * @param fixedArea
	 * @param courierId
	 * @param takeTimeId
	 */
	void associationCourierToFixedArea(FixedArea fixedArea, Integer courierId, Integer takeTimeId);

}
