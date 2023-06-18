package com.sample.dao;

import java.util.List;
import java.util.Map;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.sample.form.TableUpdateAjaxForm;


@ConfigAutowireable
@Dao
public interface TableUpdateAjaxDao {

	@Select
	public List<Map<String, Object>> selectDepartmentList();
	
	@Select
	public List<Map<String, Object>> selectDetail(TableUpdateAjaxForm form);

}
