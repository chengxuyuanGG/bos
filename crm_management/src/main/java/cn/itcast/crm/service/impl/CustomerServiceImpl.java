package cn.itcast.crm.service.impl;

import cn.itcast.crm.dao.impl.CustomerRepository;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * Created by Administrator on 2017-9-23.
 */

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    //注入Dao
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<Customer> findNoAssociationCustomer() {
        //fixedAreaId is null
        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findHasAssociationFixedAreaCustomer(String fixedAreaId) {
        //ficedAreaId is ?
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void associationCustomersToFixedArea(String customerIdStr, String fixedAreaId) {
        //解除关联动作
        customerRepository.clearFixedAreaId(fixedAreaId);
        //切割字符串1,2,3
        if (StringUtils.isBlank(customerIdStr)) {
            return;
        }
        String[] customerIdArray = customerIdStr.split(",");
        for (String idStr: customerIdArray
             ) {
            Integer id = Integer.parseInt(idStr);
            customerRepository.updateFixedAreaId(fixedAreaId,id);
        }
    }
}
