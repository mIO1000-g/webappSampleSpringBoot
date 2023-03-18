package com.sample.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name="m_employee")
public class Employee {
	@Id
	@Column(name="employee_id")
	public String employeeId;
	@Column(name="name")
	public String name;
	@Column(name="gender")
	public String gender;
	@Column(name="birthday")
	public String birthday;
	@Column(name="entering_date")
	public String enteringDate;
	@Column(name="retirement_date")
	public String retirementDate;
	@Column(name="department_id")
	public String departmentId;
	@Column(name="insert_user")
	public String insertUser;
	@Column(name="insert_date")
	public String insertDate;
	@Column(name="update_user")
	public String updateUser;
	@Column(name="update_date")
	public String updateDate;
}
