package com.sample.dao;

import java.util.List;
import java.util.Map;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import com.sample.form.TableReferenceForm;

@ConfigAutowireable
@Dao
public interface TableReferenceDao {

	/**
	 * 明細検索
	 * @param form フォーム
	 * @return 明細データ
	 */
	@Select
	
	public List<Map<String, Object>> selectDetail(TableReferenceForm form);
	
	/**
	 * 明細検索（ページングあり）
	 * @param options 検索オプション
	 * @param form フォーム
	 * @return 明細データ
	 */
	@Select
	
	public List<Map<String, Object>> selectDetailPageable(SelectOptions options, TableReferenceForm form);
}
