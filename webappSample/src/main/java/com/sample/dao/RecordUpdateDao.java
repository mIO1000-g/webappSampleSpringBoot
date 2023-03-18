package com.sample.dao;

import java.util.List;
import java.util.Map;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@ConfigAutowireable
@Dao
public interface RecordUpdateDao {

	@Select
	public List<Map<String, Object>> selectDepartmentList();
}
