package com.sample.dao;

import java.util.List;
import java.util.Map;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.sample.form.TableUpdateForm;


@ConfigAutowireable
@Dao
public interface TableUpdateDao {

	@Select
	public List<Map<String, Object>> selectDepartmentList();
	
	@Select
	public List<Map<String, Object>> selectDetail(TableUpdateForm form);

}
