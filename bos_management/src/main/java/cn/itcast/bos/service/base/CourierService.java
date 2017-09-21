package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Courier;

public interface CourierService {

	public void save(Courier courier);

	public Page<Courier> findByPageable(Courier courier, Pageable pageable);

	public void delBacth(String[] idArray);

	public void updateBacth(String[] idArray);

	public Courier findById(int intId);
}
