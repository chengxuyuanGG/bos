package cn.itcast.bos.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {
	
	@Autowired
	private AreaRepository arearepository;

	@Override
	public void saveBatch(List<Area> list) {
		arearepository.save(list);
	}

	@Override
	public Page<Area> pageQuery(final Area area, Pageable pageable) {
		Specification<Area> specification = new Specification<Area>() {
			
			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 单表查询
				List<Predicate> list = new ArrayList<Predicate>();
				
				if(StringUtils.isNotBlank(area.getProvince())) {
					Predicate p1 = cb.like(root.get("province").as(String.class), "%"+area.getProvince()+"%");
					list.add(p1);
				}
				
				if(StringUtils.isNotBlank(area.getProvince())) {
					Predicate p2 = cb.like(root.get("city").as(String.class), "%"+area.getCity()+"%");
					list.add(p2);
				}
				
				if(StringUtils.isNotBlank(area.getProvince())) {
					Predicate p3 = cb.like(root.get("district").as(String.class), "%"+area.getDistrict()+"%");
					list.add(p3);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return arearepository.findAll(specification, pageable);
	}

}
