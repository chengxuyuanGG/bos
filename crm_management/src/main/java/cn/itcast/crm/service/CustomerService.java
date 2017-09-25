package cn.itcast.crm.service;

import cn.itcast.crm.domain.Customer;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by Administrator on 2017-9-22.
 */
public interface CustomerService {
    //查询所有未关联客户列表
    @Path("/noassociationcustomer")
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Customer> findNoAssociationCustomer();

    //查询已关联到指定定区的客户列表
    @Path("/associationfixedareacustomer/{fixedareaid}")
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Customer> findHasAssociationFixedAreaCustomer(@PathParam("fixedareaid") String fixedAreaId);

    //将客户关联到定区上,将所有所有客户id拼成字符串1,2,3
    @Path("/associationcustomerstofixedarea")
    @PUT
    public void associationCustomersToFixedArea(@QueryParam("customerIdStr") String customerIdStr, @QueryParam("fixedAreaId") String fixedAreaId);
}
