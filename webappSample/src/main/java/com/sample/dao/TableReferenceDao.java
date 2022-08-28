package com.sample.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TableReferenceDao {

	public List<HashMap<String, Object>> selectDetail() {
		
		List<HashMap<String, Object>> list = new ArrayList<>();
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("no", 1);
		map.put("employeeId", "1234567890");
		map.put("name", "千田未央");
		map.put("gender", "F");
		map.put("genderName", "女");
		map.put("birthday", "19890912");
		map.put("enteringDate", "20140401");
		map.put("retirementDate", "20230401");
		map.put("departmentId", "1001");
		map.put("departmentName", "開発");
		map.put("updateDate", "20220826000000");

		list.add(map);
		
		return list;
	}
}
