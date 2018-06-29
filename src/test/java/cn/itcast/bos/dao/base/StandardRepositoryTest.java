package cn.itcast.bos.dao.base;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bos.domain.base.Standard;

//收派标准的测试用例
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {
	//注入要测试的bean
	@Autowired//根据类型注入
	private StandardRepository standardRepository;
	
	
	//保存
	@Test
	public void testSave(){
		Standard standard=new Standard();
		standard.setId(1);
		standard.setName("小件取派员");
		standardRepository.save(standard);
	}
	
	//查询所有
	@Test
	public void testFindAll(){
		List<Standard> list = standardRepository.findAll();
		System.out.println(list);
	}
	//根据name查询匹配
	@Test
	public void testindByName(){
		Standard standard = standardRepository.findByName("小件取派员");
		System.out.println(standard);
	}
	//根据name模糊查询
	@Test
	public void testFindByNameLike(){
		List<Standard> list = standardRepository.findByNameLike("%取%");
		System.out.println(list);
	}
	//根据id查询name
	@Test
	public void testFindNameById(){
		String name = standardRepository.findNameById(1);
		System.out.println(name);
	}
	//根据id更新name
	@Test
	public void testUpdateNameById(){
		standardRepository.updateNameById(1, "大件取派员");
	}
	
	
	
}
