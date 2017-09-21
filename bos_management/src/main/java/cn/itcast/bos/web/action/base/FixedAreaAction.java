package cn.itcast.bos.web.action.base;

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

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.bos.web.action.common.BaseAction;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {

	@Autowired
	private FixedAreaService fixedAreaservice;
	
	@Action(value="fixed_save",results={@Result(name="success",type="redirect",location="./pages/base/fixed_area.html")})
	public String save() {
		//System.out.println(model);
		fixedAreaservice.save(model);
		return SUCCESS;
	}
	
	@Action(value="fixed_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery() {
		// 创建pageable对象
		Pageable pageable = new PageRequest(page-1, rows);
		
		// 调用service层查询数据
		Page<FixedArea> page = fixedAreaservice.pageQuery(model,pageable);
		
		// 将数据压栈
		pushPageDatakToValueStack(page);
		return SUCCESS;
	}
}
