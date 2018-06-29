package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.itcast.bos.domain.base.Standard;
//收派标准的持久层接口
//必须继承org.springframework.data.jpa.repository.JpaRepository
//泛型参数1：实体类
//泛型参数2：主键类型
public interface StandardRepository extends JpaRepository<Standard, Integer>{

	//====属性表达式
	//需求：根据name属性来查询结果
	/*
	 * 方法名是：findByXxx,驼峰命名属性Xxx
	 * 参数的个数和类型必须和属性一致
	 * 返回的结果类型：（结果自动封装）
	 * 如果确定最多返回一个，则可以用实体类型。
	 * 如果返回多个，则必须用集合类型，如list
	 * 
	 */
	Standard findByName(String name);
	
	//需求：模糊匹配名字查询
	List<Standard> findByNameLike(String name);
	
	//=======Query注解
	//要根据id查询name的值
	@Query("select name from Standard where id =?")//JPQL,面向对象，类似于HQL
//	@Query(value="select c_name from T_STANDARD where c_id =?",nativeQuery=true)//SQL,必须打开原生sql开关
	String findNameById(Integer id);
	
	
	//---------参数占位符
	//需求：根据id和name共同查询
	//1)匿名占位符
	@Query("from Standard where id =? and name =?")
	Standard findByIdAndName1(String id, String name);
	//2)命名占位符
	@Query("from Standard where id =:id and name =:name")
	Standard findByIdAndName2(@Param("name")String name,@Param("id")String id);
	//3)JPA占位符
	@Query("from Standard where id =?2 and name =?1")
	Standard findByIdAndName3(String name,String id);
	
	//根据id更新名字
	@Query("update Standard set name =?2 where id =?1")
	@Modifying//让方法变成更新方法，默认Query注解是查询，事务只读！注意增删改需要加该注解！！！！
	void updateNameById(Integer id ,String name);
	
}
