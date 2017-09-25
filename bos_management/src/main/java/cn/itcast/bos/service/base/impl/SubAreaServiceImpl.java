package cn.itcast.bos.service.base.impl;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.dao.base.SubAreaRepository;
import cn.itcast.bos.dao.base.TakeTimeRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.bos.service.base.SubAreaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
	
	@Autowired
	private SubAreaRepository subAreaRepository;

	@Override
	public void save(SubArea subArea) {
		subAreaRepository.save(subArea);
	}
}
