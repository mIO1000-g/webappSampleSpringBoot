package com.sample.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name="MUser")
public class MUser {

	@Id
	@Column(name="user_id")
	public String userId;
	@Column(name="user_name")
	public String userName;
	@Column(name="password")
	public String password;
	@Column(name="autz_level")
	public String autzLevel;
	@Column(name="account_valid_date_end")
	public String accountValidDateEnd;
	@Column(name="account_lock_flag")
	public String accountLockFlag;
	@Column(name="password_last_update")
	public String passwordLastUpdate;
	@Column(name="insert_user", updatable=false)
	public String insertUser;
	@Column(name="insert_date", updatable=false)
	public String insertDate;
	@Column(name="update_user")
	public String updateUser;
	@Column(name="update_date")
	public String updateDate;
}
