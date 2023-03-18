package com.sample.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import com.sample.entity.Employee;

@ConfigAutowireable
@Dao
public interface EmployeeDao {
	
	@Insert
	int insert(Employee employee);
	
	@Update(excludeNull = true)
	int update(Employee employee);
	
	@Delete
	int delete(Employee employee);
	
	@Select
	Employee selectByPk(String employeeId);
	
	@Select
	Employee selectByPkWithLock(String employeeId);
}
