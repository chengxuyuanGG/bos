package cn.itcast.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;
import cn.itcast.bos.web.action.common.BaseAction;
import oracle.net.aso.p;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class AreaAction extends BaseAction<Area> {

	// 注入service层
	@Autowired
	private AreaService areaService;

	// 文件上传,提供文件上传的字段
	private File file;
	private String fileFileName;

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	@Action(value = "area_batchImport")
	public String batchImport() {
		
		// 解析.xls和.xlsx文件
		// 文件类型判断
		if (StringUtils.isBlank(fileFileName) || (!fileFileName.endsWith(".xls") && !fileFileName.endsWith(".xlsx"))) {
			
			throw new RuntimeException("别瞎搞......");
		}
		try {
			// 创建集合封装area对象
			List<Area> list = new ArrayList<Area>();
			// 解析Excel文件中的工作簿
			Workbook workbook = null;
			if (fileFileName.endsWith(".xls")) {
				workbook = new HSSFWorkbook(new FileInputStream(file));
			} else if (fileFileName.endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(new FileInputStream(file));
			}
			// 解析Excel中的sheet
			Sheet sheet = workbook.getSheetAt(0);
			// 解析sheet中的每一行
			for (Row row : sheet) {
				// 跳过第一行
				if (row.getRowNum() == 0) {
					continue;
				}
				// 跳过空行
				if (row.getCell(0) == null && StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
					continue;
				}
				Area area = new Area();
				area.setId(row.getCell(0).getStringCellValue());
				area.setProvince(row.getCell(1).getStringCellValue());
				area.setCity(row.getCell(2).getStringCellValue());
				area.setDistrict(row.getCell(3).getStringCellValue());
				area.setPostcode(row.getCell(4).getStringCellValue());
				// 生成简码和城市编码
				String province = area.getProvince();
				String city = area.getCity();
				String district = area.getDistrict();
				province = province.substring(0, province.length() - 1);
				city = city.substring(0, city.length() - 1);
				district = district.substring(0, district.length() - 1);

				String[] head = PinYin4jUtils.getHeadByString(province + city + district);
				StringBuilder sb = new StringBuilder();
				for (String headStr : head) {
					sb.append(headStr);
				}
				String shortCode = sb.toString();
				area.setShortcode(shortCode);
				// 城市编码
				String cityCode = PinYin4jUtils.hanziToPinyin(city, "");
				area.setCitycode(cityCode);
				list.add(area);
			}

			areaService.saveBatch(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	

	@Action(value = "area_pageQuery", results = { @Result(name = "success", type = "json") })
	public String pageQuery() {
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Area> page = areaService.pageQuery(model , pageable);
		
		pushPageDatakToValueStack(page);
		return SUCCESS;
	}
}
