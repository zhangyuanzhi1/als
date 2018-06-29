package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Menu;

public interface MenuService {

	/**
	 * 查询所有菜单
	 * @return
	 */
	List<Menu> findMenuList();

	void saveMenu(Menu menu);

}
