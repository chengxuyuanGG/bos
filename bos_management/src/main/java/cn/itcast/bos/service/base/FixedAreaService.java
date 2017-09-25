package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.FixedArea;

import java.util.List;

public interface FixedAreaService {

	void save(FixedArea model);

	Page<FixedArea> pageQuery(FixedArea model, Pageable pageable);

    void associationCourierToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId);

    List<FixedArea> findAll();
}
