package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Area;

public interface AreaService {

	void saveBatch(List<Area> list);

	Page<Area> pageQuery(Area area, Pageable pageable);

}
