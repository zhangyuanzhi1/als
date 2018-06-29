package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;

//快递员的service
public interface CourierService {

	/**
	 * 
	 * 说明：保存一个快递员
	 * @param courier
	 * @author 传智.BoBo老师
	 * @time：2018年1月13日 下午6:25:55
	 */
	void saveCourier(Courier courier);
	
	/**
	 * 
	 * 说明：组合条件分页查询
	 * @param spec
	 * @param pageable
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2018年1月15日 下午3:47:49
	 */
	Page<Courier> findCourierListPage(Specification<Courier> spec, Pageable pageable);

	/**
	 * 
	 * 说明：批量删除
	 * @param ids
	 * @author 传智.BoBo老师
	 * @time：2018年1月15日 下午5:16:39
	 */
	void deleteBatch(String ids);

	/**
	 * 查询没有作废的快递员
	 * @return
	 */
	List<Courier> findCourierListNoDeltag();

}
