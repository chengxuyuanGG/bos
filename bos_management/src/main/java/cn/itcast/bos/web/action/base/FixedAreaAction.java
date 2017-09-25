package cn.itcast.bos.web.action.base;

import cn.itcast.crm.domain.Customer;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
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

import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {

    @Autowired
    private FixedAreaService fixedAreaservice;

    @Action(value = "fixed_save", results = {@Result(name = "success", type = "redirect", location = "./pages/base/fixed_area.html")})
    public String save() {
        //System.out.println(model);
        fixedAreaservice.save(model);
        return SUCCESS;
    }

    @Action(value = "fixed_pageQuery", results = {@Result(name = "success", type = "json")})
    public String pageQuery() {
        // 创建pageable对象
        Pageable pageable = new PageRequest(page - 1, rows);

        // 调用service层查询数据
        Page<FixedArea> page = fixedAreaservice.pageQuery(model, pageable);

        // 将数据压栈
        pushPageDatakToValueStack(page);
        return SUCCESS;
    }

    //查询未关联定区客户列表
    @Action(value = "fixedArea_findNoAssociationCustomer", results = {@Result(name = "success", type = "json")})
    public String findNoAssociationCustomer() {
        //使用webClient调用webService接口
        Collection<? extends Customer> collection = WebClient.create("http://localhost:9998/crm_management/service/customerService/noassociationcustomer")
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        ActionContext.getContext().getValueStack().push(collection);
        return SUCCESS;
    }

    //查询关联当前定区的客户列表
    @Action(value = "fixedArea_findHasAssociationFixedAreaCustomer", results = {@Result(name = "success", type = "json")})
    public String findHasAssociationFixedAreaCustomer() {
        //使用webClient调用webService接口
        Collection<? extends Customer> collection = WebClient.create("http://localhost:9998/crm_management/service/customerService/associationfixedareacustomer/" + model.getId())
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        ActionContext.getContext().getValueStack().push(collection);
        return SUCCESS;
    }

    //属性驱动
    private String[] customerIds;

    public void setCustomerIds(String[] customerIds) {
        this.customerIds = customerIds;
    }

    //关联客户到定区
    @Action(value = "fixedArea_associationCustomersToFixedArea", results = {@Result(name = "success", type = "redirect",
            location = "./pages/base/fixed_area.html")})
    public String associationCustomersToFixedArea() {
        String customerIdStr = StringUtils.join(customerIds, ",");
        WebClient.create("http://localhost:9998/crm_management/service/customerService/associationcustomerstofixedarea?" +
                "customerIdStr=" + customerIdStr + "&fixedAreaId=" + model.getId()).put(null);
        return SUCCESS;
    }

    //属性驱动
    private Integer courierId;
    private Integer takeTimeId;

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public void setTakeTimeId(Integer takeTimeId) {
        this.takeTimeId = takeTimeId;
    }

    //关联快递员到定区
    @Action(value = "fixedArea_associationCourierToFixedArea",
            results = {@Result(name = "success",type = "redirect",
                    location = "./pages/base/fixed_area.html")})
    public String associationCourierToFixedArea(){
        //调用业务层,定区关联快递员
        fixedAreaservice.associationCourierToFixedArea(model,courierId,takeTimeId);
        return SUCCESS;
    }

    //查询所有定区显示到分区页面
    @Action(value = "fixedArea_findAll",results = {@Result(name = "success",type = "json")})
    public String findAll(){

        //查询所有fixedArea对象
        List<FixedArea> list = fixedAreaservice.findAll();
        //压栈
        ActionContext.getContext().getValueStack().push(list);
        return SUCCESS;
    }
}
