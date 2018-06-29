package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Standard;

//收派标准的业务接口
public interface StandardService {

	/**
	 * 
	 * 说明：保存一个标准
	 * @param standard
	 * @author 传智.BoBo老师
	 * @time：2018年1月13日 下午4:12:32
	 */
	void saveStandard(Standard standard);

	/**
	 * 
	 * 说明：分页列表查询标准
	 * @param pageable
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2018年1月13日 下午4:55:58
	 */
	Page<Standard> findStandardListPage(Pageable pageable);

	/**
	 * 
	 * 说明：查询所有的标准列表
	 * @return
	 * @author 传智.BoBo老师
	 * @time：2018年1月13日 下午6:16:03
	 */
	List<Standard> findStandardList();

}
