package cn.itcast.crm.dao.impl;

import cn.itcast.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017-9-23.
 */
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);

    @Query("update Customer set fixedAreaId = ? where id = ?")
    @Modifying
    public void updateFixedAreaId(String fixedAreaId, Integer id);

    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    @Modifying
    public void clearFixedAreaId(String fixedAreaId);
}
