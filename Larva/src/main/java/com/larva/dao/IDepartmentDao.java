package com.larva.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.larva.model.Department;

/**
 * @author sxjun
 * @time 2015/9/2 10:10
 */
public interface IDepartmentDao {
	
	List<Department> selectPage(int limit,int ffset);

    List<Department> selectAll();

    Department get(List<Department> list, String id);

    int create(Department department);

    int delete(String depId);

    int update(Department department);

}
