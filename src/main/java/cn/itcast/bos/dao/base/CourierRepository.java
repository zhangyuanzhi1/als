package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Courier;

//快递员的持久层接口
//要使用Specification方案，必须继承JpaSpecificationExecutor接口
public interface CourierRepository extends JpaRepository<Courier, Integer>
,JpaSpecificationExecutor<Courier>{
	
	
	//根据id更新删除状态
	@Query("update Courier set deltag =?2 where id =?1")
	@Modifying
	void updateDeltagById(Integer id ,Character deltag);
	
	
	//根据删除标记来查询快递员列表
	List<Courier> findByDeltag(Character deltag);
}
