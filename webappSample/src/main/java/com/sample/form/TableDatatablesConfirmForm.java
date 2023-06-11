package com.sample.form;

import java.util.List;

import javax.validation.Valid;

import lombok.Data;

@Data
public class TableDatatablesConfirmForm {

	@Valid
	List<TableDatatablesRecord> detail;

}
