package cn.itcast.bos.web.action.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
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

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
import oracle.net.aso.s;

@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

	private Standard standard = new Standard();

	@Autowired
	private StandardService standardService;

	@Override
	public Standard getModel() {
		return standard;
	}

	@Action(value = "standard_save", results = {
			@Result(name = "success", type = "redirect", location = "/pages/base/standard.html") })
	public String save() {
		
		standardService.save(standard);
		return SUCCESS;
	}

	private int page;
	private int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Action(value="standard_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery() {
		// 创建pageAble
		Pageable pageable = new PageRequest(page-1, rows);
		
		// 根据pageable查询数据数据
		Page<Standard> page = standardService.findPageData(pageable);
		
		int totalPage = (int) page.getTotalElements();
		List<Standard> content = page.getContent();
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", totalPage);
		result.put("rows", content);
		
		ServletActionContext.getContext().getValueStack().push(result);
		
		return SUCCESS;
	}
	
	@Action(value="standad_findAll",results={@Result(name="success",type="json")})
	public String findAll() {
		List<Standard> standards = standardService.findAll();
		ActionContext.getContext().getValueStack().push(standards);
		return SUCCESS;
	}
	
	private String standardId;

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
	
	@Action(value="standard_findById",results={@Result(name="success",type="json")})
	public String findById() {
		int id = Integer.parseInt(standardId);
		Standard standard = standardService.findById(id);
		ActionContext.getContext().getValueStack().push(standard);
		return SUCCESS;
	}
}
