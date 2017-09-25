package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubAreaService {

	void save(SubArea subArea);
}
