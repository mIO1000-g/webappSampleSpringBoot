select
 m1.employee_id
,m1.name
,m1.gender
,case when m1.gender = 'M' then '男' else '女' end as gender_name 
,m1.birthday
,m1.entering_date
,m1.retirement_date
,m1.department_id
,m2.department_name
,m2.update_date
from m_employee m1
inner join m_department m2
on 1=1
and m1.department_id = m2.department_id
where 1=1
/*%if form.employeeId != null && form.employeeId != "" */
and m1.employee_id = /* form.employeeId */'1'
/*%end*/
/*%if form.name != null && form.name != "" */
and m1.name = /* form.name */'1'
/*%end*/
/*%if form.gender_m && !form.gender_f */
and m1.gender = 'M'
/*%elseif !form.gender_m && form.gender_f */
and m1.gender = 'F'
/*%end*/
/*%if form.enteringYear != null && form.enteringYear != "" */
and substring(m1.entering_date ,1 ,4) = /* form.enteringYear */'1'
/*%end*/
/*%if form.retired */
and m1.retirement_date is not null
/*%end*/
/*%if form.departmentId != null && form.departmentId != "" */
and m1.department_id = /* form.departmentId */'1'
/*%end*/
order by
 m1.employee_id