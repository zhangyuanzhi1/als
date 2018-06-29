package cn.itcast.bos.service.take_delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.take_delivery.WayBill;

public interface WayBillService {

	/**
	 * 快速保存运单
	 * @param wayBill
	 */
	void saveWayBillQuick(WayBill wayBill);

	/**
	 * 分页列表查询
	 * @param pageable
	 * @return
	 */
	Page<WayBill> findWayBillListPage(Pageable pageable);

	/**
	 * 基于es索引库分页列表查询
	 * @param pageable
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	Page<WayBill> findWayBillListPage(Pageable pageable, String fieldName, String fieldValue);

}
