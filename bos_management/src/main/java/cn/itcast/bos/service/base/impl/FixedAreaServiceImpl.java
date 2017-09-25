package cn.itcast.bos.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.dao.base.TakeTimeRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.TakeTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
	
	@Autowired
	private FixedAreaRepository fixedAreaRepository;

	@Autowired
	private TakeTimeRepository takeTimeRepository;

	@Autowired
	private CourierRepository courierRepository;

	@Override
	public void save(FixedArea model) {
		fixedAreaRepository.save(model);
	}

	@Override
	public Page<FixedArea> pageQuery(final FixedArea model, Pageable pageable) {
		
		// 创建查询的条件
		Specification<FixedArea> specification = new Specification<FixedArea>() {
			
			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> list = new ArrayList<Predicate>();
				
				if(StringUtils.isNotBlank(model.getId())) {
					Predicate p1 = cb.equal(root.get("id").as(String.class), model.getId());
					list.add(p1);
				}
				
				if(StringUtils.isNotBlank(model.getCompany())) {
					Predicate p2 = cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%");
					list.add(p2);
				}
				
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		
		return fixedAreaRepository.findAll(specification, pageable);
	}
	//区域关联快递员
	@Override
	public void associationCourierToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId) {
		//根据id得到区域
		FixedArea fixedArea = fixedAreaRepository.findOne(model.getId());
		//根据id得到快递员
		Courier courier = courierRepository.findOne(courierId);
		//根据id得到取排时间
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		//将快递员关联到定区
		fixedArea.getCouriers().add(courier);
		//将取排时间关联到快递员上
		courier.setTakeTime(takeTime);
	}

	@Override
	public List<FixedArea> findAll() {
		return fixedAreaRepository.findAll();
	}

}
