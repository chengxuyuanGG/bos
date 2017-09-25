package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;
import cn.itcast.bos.web.action.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created by Administrator on 2017-9-25.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class SubAreaAction extends BaseAction<SubArea> {

    @Autowired
    private SubAreaService subAreaService;
    @Action(value = "subArea_save",results = {@Result(name = "success",type = "redirect",location = "./pages/base/sub_area.html")})
    public String save(){
        //调用业务层进行添加
        subAreaService.save(model);

        return SUCCESS;
    }
}
