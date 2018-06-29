package cn.itcast.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.web.action.common.BaseAction;
//区域action
@Controller
@Scope("prototype")
public class AreaAction extends BaseAction<Area>{
	
	//属性驱动接收文件
	private File upload;//真实的文件对象，注意该名字必须和页面上file元素的name的名字一样！！！！
	private String uploadContentType;//文件的mime类型，name+ContentType
	private String uploadFileName;//真实的文件名,name+FileName
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	//注入service
	@Autowired
	private AreaService areaService;

	//导入数据
	@Action("area_importData")
	public String importData(){
		System.out.println("上传的文件是："+upload);
		System.out.println("上传的文件类型是："+uploadContentType);
		System.out.println("上传的文件名字是："+uploadFileName);
		
		//定义list，存放实体对象
		List<Area> areaList=new ArrayList<>();
		
		//定义map，存放结果
		Map<String, Object> resultMap=new HashMap<>();
		
		//目标：解析（读）excel
		try {
			//记忆技巧：你平时怎么读excel，代码就怎么写。
			//这里解析97的格式，（2007格式api一样的，就首字母不一样）
			//1)打开工作簿
			HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(upload));
			//2)打开工作簿中的表
//			workbook.getSheet("Sheet1");//根据名字来读
			HSSFSheet sheet = workbook.getSheetAt(0);//按照索引来读0-based
			//3)一行一行读取
			//直接循环表（由行组成的）
			for (Row row : sheet) {
				//跳过第一行（标题）
				if(row.getRowNum()==0){//获取行号，从0开始
					continue;
				}
				
				//正式获取行中的格的数据
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				
				//创建一个对象
				Area area=new Area();
				area.setId(id);
				area.setProvince(province);
				area.setCity(city);
				area.setDistrict(district);
				area.setPostcode(postcode);
				
				
				//处理区域简码和城市编码
				
				//处理省市区字符串，去掉最后一个字
				String provinceStr = StringUtils.substring(province, 0, -1);
				String cityStr = StringUtils.substring(city, 0, -1);
				String districtStr = StringUtils.substring(district, 0, -1);
				//汉字转拼音技术jpinyin
				//区域简码：上海上海浦东新-SHSHPDX
				String shortcode=PinyinHelper.getShortPinyin(provinceStr+cityStr+districtStr).toUpperCase();
				area.setShortcode(shortcode);
				
				//城市编码:上海--shanghai
				String citycode=PinyinHelper.convertToPinyinString(cityStr, "", PinyinFormat.WITHOUT_TONE);
				area.setCitycode(citycode);
				
				
				areaList.add(area);
			}
			
			//将excel的数据全部都读到list去了，
			//调用业务层保存即可
			areaService.saveArea(areaList);
			//成功
			resultMap.put("result", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			//失败
			resultMap.put("result", false);
		}
		
		//map压入root栈顶
		ActionContext.getContext().getValueStack().push(resultMap);
		
		//{'result':true}
		return JSON;
	}
	

}
