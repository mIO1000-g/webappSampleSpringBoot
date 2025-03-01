package com.sample.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.sample.entity.MUser;

@ConfigAutowireable
@Dao
public interface MUserDao {
	
	@Select
	MUser selectByPk(String userId);
	
}
