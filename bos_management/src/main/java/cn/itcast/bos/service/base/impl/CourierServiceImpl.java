package cn.itcast.bos.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;


@Service
@Transactional
public class CourierServiceImpl implements CourierService {
	
	@Autowired
	private CourierRepository courierRepository;

	@Override
	public void save(Courier courier) {
		courierRepository.save(courier);
	}

	@Override
	public Page<Courier> findByPageable(final Courier courier, Pageable pageable) {
		
		Specification<Courier> specification = new Specification<Courier>() {
			
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> list = new ArrayList<Predicate>();
				// 单标查询,使用CriteriaBuild的like和equal方法
				if(StringUtils.isNoneBlank(courier.getCourierNum())){
					// courierNum = ? 张三
					Predicate q1 = cb.equal(root.get("courierNum").as(String.class), courier.getCourierNum());
					list.add(q1);
				}
				
				if(StringUtils.isNoneBlank(courier.getCompany())) {
					Predicate p2 = cb.like(root.get("company").as(String.class), "%"+courier.getCompany()+"%");
					list.add(p2);
				}
				
				if(StringUtils.isNoneBlank(courier.getType())) {
					Predicate p3 = cb.equal(root.get("type").as(String.class), courier.getType());
					list.add(p3);
				}
				
				// 多表联合查询,根据
				Join<Object, Object> standardRoot = root.join("standard",JoinType.INNER);
				if(courier.getStandard() != null && StringUtils.isNoneBlank(courier.getStandard().getName())) {
					Predicate p4 = cb.like(standardRoot.get("name").as(String.class), "%"+courier.getStandard().getName()+"%");
					list.add(p4);
				}
				
				
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return courierRepository.findAll(specification, pageable);
	}

	@Override
	public void delBacth(String[] idArray) {
		for (String str : idArray) {
			int id = Integer.parseInt(str);
			courierRepository.delDeltag(id);
		}
	}

	@Override
	public void updateBacth(String[] idArray) {
		for (String str : idArray) {
			int id = Integer.parseInt(str);
			courierRepository.updateDeltag(id);
		}
	}

	@Override
	public Courier findById(int intId) {
		return courierRepository.findOne(intId);
	}

}
