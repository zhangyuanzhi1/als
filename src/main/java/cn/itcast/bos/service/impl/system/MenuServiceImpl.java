package cn.itcast.bos.service.impl.system;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuRepository;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.service.system.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	//注入dao
	@Autowired
	private MenuRepository menuRepository;

	@Override
	public List<Menu> findMenuList() {
		return menuRepository.findAll();
	}

	@Override
	public void saveMenu(Menu menu) {
		//因为combobox，（不选默认有值）因此需要手动处理一下parentMenu对象
		if(menu.getParentMenu()!=null&&menu.getParentMenu().getId()==0){
			menu.setParentMenu(null);
		}
		
		//保存到数据库
		menuRepository.save(menu);
		
	}

}
