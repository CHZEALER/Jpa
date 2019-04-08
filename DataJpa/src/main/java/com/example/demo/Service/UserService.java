package com.example.demo.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import com.example.demo.domin.User;
import com.example.demo.domin.UserRepository;



@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * 按名字查询
	 * @param Username
	 * @return
	 */
	public User findUserByUsername(String Username) {
		return userRepository.findUserByUsername(Username);
	}
	/**
	 * 查询所有
	 * @return
	 */
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	/**
	 * 根据id更新用户名和密码
	 * @param id
	 * @param username
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public int updateU_PById(int id,String username,String password) {
		return userRepository.updateU_PById(id,username,password);
	}
	
	
	/**
	 * 分页查询
	 * @param searchParameters
	 * @return
	 */
	public Map getPage(final Map searchParameters) {
		//初始化数据
		Map map = new HashMap();
		int page = 0;
		int pageSize = 10;
		Page<User> pageList;
		
		//判断数据是否为空，不为空则赋值
		if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("page") != null) {
			page = Integer.parseInt(searchParameters.get("page").toString()) - 1;
		}
		if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("pageSize") != null) {
			pageSize = Integer.parseInt(searchParameters.get("pageSize").toString());
		}
		
		//设置页面数量的最大最小值
		if (pageSize < 1)
			pageSize = 1;
		if (pageSize > 100)
			pageSize = 100;
		
		//判断是否传入sort排序
		List<Map> orderMaps = (List<Map>) searchParameters.get("sort");
		List<Order> orders = new ArrayList<Order>();
		if (orderMaps != null) {
			for (Map m : orderMaps) {
				if (m.get("field") == null)
					continue;
				String field = m.get("field").toString();
				if (!StringUtils.isEmpty(field)) {
					String dir = m.get("dir").toString();
					if ("DESC".equalsIgnoreCase(dir)) {
						orders.add(new Order(Direction.DESC, field));
					} else {
						orders.add(new Order(Direction.ASC, field));
					}
				}
			}
		}
		
		
		//生成pageable
		PageRequest pageable;
		if (orders.size() > 0) {
			pageable = new PageRequest(page, pageSize, new Sort(orders));
		} else {
			Sort sort = new Sort(Direction.ASC, "id");
			pageable = new PageRequest(page, pageSize, sort);
		}
		
		//获取查询条件
		Map filter = (Map) searchParameters.get("filter");
		
		//判断是否有条件
		if (filter != null) {
			final List<Map> filters = (List<Map>) filter.get("filters");
			Specification<User> spec = new Specification<User>() {
				@Override
				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> pl = new ArrayList<Predicate>();
					
					//添加条件到语句
					for (Map f : filters) {
						String field = f.get("field").toString().trim();
						String value = f.get("value").toString().trim();
						if (value != null && value.length() > 0) {
							if ("username".equalsIgnoreCase(field)) {
								pl.add(cb.like(root.<String>get(field), value + "%"));
							} else if ("password".equalsIgnoreCase(field)) {
								pl.add(cb.like(root.<String>get(field), value + "%"));
							} /*else if ("deptUrl".equalsIgnoreCase(field)) {
								pl.add(cb.like(root.<String>get(field), value + "%"));
							} */
						}
					}
					// 查询出未删除的
					/*pl.add(cb.equal(root.<Integer>get("flag"), 1));*/
					return cb.and(pl.toArray(new Predicate[0]));
				}
			};
 
			pageList = userRepository.findAll(spec, pageable);

		} else {
			Specification<User> spec = new Specification<User>() {
				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<Predicate>();
					// 查询出未删除的
					/*list.add(cb.equal(root.<Integer>get("flag"), 1));*/
					return cb.and(list.toArray(new Predicate[0]));
				}
			};
			pageList = userRepository.findAll(spec, pageable);

		}
		map.put("total", pageList.getTotalElements());
		List<User> list = pageList.getContent();
		 
		map.put("users", list);
		return map;
	}
}

