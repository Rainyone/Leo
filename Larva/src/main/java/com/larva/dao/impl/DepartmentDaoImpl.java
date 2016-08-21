package com.larva.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.larva.dao.IDepartmentDao;
import com.larva.model.Department;
import com.mini.core.dao.IMiniDao;
import com.mini.core.dao.MiniDao;

/**
 * @author sxjun
 * @time 2015/9/2 10:10
 */
@Repository("departmentDao")
public class DepartmentDaoImpl extends MiniDao implements IDepartmentDao {
    
    /**
     * 获取所有部门
     *
     * @return
     */
    public List<Department>  selectPage(int limit,int pageNo) {
    	return this.paginate("select * from department order by `order` asc", pageNo, limit, Department.class);
    }

    /**
     * 获取所有部门
     *
     * @return
     */
    public List<Department> selectAll() {
    	return this.findList("select * from department order by `order` asc",Department.class);
    }

    /**
     * 获取部门
     *
     * @param list
     * @param id
     * @return
     */
    public Department get(List<Department> list, String id) {
        for (Department department : list) {
            if (id.equals(department.getId())) {
                return department;
            }
        }
        return null;
    }

    /**
     * 创建部门
     * @param department
     * @return
     */
    public int create(Department department) {
        return this.insert(department);
    }

    /**
     * 删除部门
     * @param depId
     * @return
     */
    public int delete(String depId) {
        return this.deleteById(Department.class,depId);
    }

    /**
     * 更新部门
     * @param department
     * @return
     */
    public int update(Department department) {
        return this.update(department);
    }
}
