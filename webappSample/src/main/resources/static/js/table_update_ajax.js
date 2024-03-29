/**
 *
 */

$(function() {

	let initialized = false;

	// 検索ボタン押下
	$("#searchB").on("click", function() {

		let _form = new FormData($("#form").get(0));
		$.ajax({
			type: "POST",
			data: _form,
			url: "/webappSample/table_datatables/search",
			processData: false,
			contentType: false,
			dataType: "json"

		}).done(function(data, textStatus, jqXHR) {

			if (initialized) {
				// 繰り返し検索した場合、同一idで再初期化できないため、先にDataTablesを破壊し、かつDOMを削除する。
				$("#mytable").DataTable().destroy();
				$("#mytable > tr").empty();
			}

			let list = data.data;

			// DataTables初期化
			initDataTable(list);

			initialized = true;

		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("失敗");
		});
	});

	// 確定ボタン押下
	$("#confirmB").on("click", function() {
		// 検索条件部
		let _form = new FormData($("#form").get(0));

		// 明細部
		let _data = [];

		$("#mytable tbody tr").each(function(index, row) {
			// テーブルの行を走査
			if ($(row).find("input:checkbox").is(":checked")) {
				// 選択されている行のみ
				
				// １行分のオブジェクト
				let _row = {};

				$(row).find("input, select").each(function(index, element) {
					if (element.type === "checkbox") {
						// チェックボックスは除外
						return;
					}
					_row[element.name] =  element.value;
				});
				_data.push(_row);
			}
		});

		$.ajax({
			type: "POST",
			data: JSON.stringify({
				// NOTE:検索条件部の値を取得してセット
				// チェックボックスの値は、checkedの真偽値を取得してセット
				employeeId : $("#employeeId").val(),
				name : $("#name").val(),
				gender_m : $("input[name='gender_m']").prop("checked"),
				gender_f : $("input[name='gender_f']").prop("checked"),
				enteringYear : $("#enteringYear").val(),
				retired : $("input[name='retired']").prop("checked"),
				departmentId : $("#departmentId").val(),
				detail : _data
			}),
			url: "/webappSample/table_update_ajax/confirm",
			contentType: "application/json",
			dataType: "json"

		}).done(function(data, textStatus, jqXHR) {

			// エラー表示を初期化
			$("table span").remove();
			$(".has-error").removeClass("has-error");

			if ("OK" === data.result) {
				// 処理結果OKの場合

				if (initialized) {
					// 繰り返し検索した場合、同一idで再初期化できないため、先にDataTablesを破壊し、かつDOMを削除する。
					$("#mytable").DataTable().destroy();
					$("#mytable > tr").empty();
				}
	
				let list = data.data;
				
				// DataTables初期化
				initDataTable(list);
	
				initialized = true;
				
			} else {
				// 処理結果NGの場合

				for (i = 0; i < data.data.length; i++) {
					// 返却データを走査
					$("#mytable tbody tr").each(function(index, row) {
						// テーブルの行を走査
						if ($(row).find("input[name=employeeId]input[value=" + data.data[i].key + "]").length > 0) {
							// キー（社員ID）が一致する行の場合、対象列の要素を赤くし、単項目チェックエラー内容を付加
							let element = $(row).find("input[name=" + data.data[i].column + "]");
							element.attr("class", "has-error");
							element.after('<span class="error-tooltip-text">' + data.data[i].errorMessage + '</span>');
						}
					});	
				}
			}

			// モーダル表示
			showModal(data.message);

		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("失敗");
		});
	});
});

function initDataTable(list) {

	// DataTablesの初期化
	$("#mytable").DataTable({
		data: list,
		searching: false,
		lengthChange: false,
		pageLength: 8,
		createdRow: function ( row, data, dataIndex, cells ) {
			$(row).find("td").addClass("error-tooltip");
		},
		columns: [
			{
				title: "選択",
				data: null,
				//orderable: false,
				visible: true,
				render: function (data, type, row, meta) {
					return '<input type="checkbox" name="check">';
				},
			},
			{
				title: "No.",
				data: "no",
				visible: true,
			},
			{
				title: "社員ID",
				data: "employeeId",
				visible: true,
				render: function (data, type, row, meta) {
					return '<input type="text" name="employeeId" value="' + data + '" readonly>'
					+ '<input type="hidden" name="newLine" value="' + row.newLine + '">'
					+ '<input type="hidden" name="updateDate" value="' + row.updateDate + '">';
				},
			},
			{
				title: "社員名",
				data: "name",
				visible: true,
				render: function (data, type, row, meta) {
					return '<input type="text" name="name" value="' + data + '">';
				},
			},
			{
				title: "性別",
				data: "gender",
				visible: true,
				render: function (data, type, row, meta) {
					return '<select name="gender">' +
							'<option value="M" '+ (data === "M" ? 'selected' : '')+ '>男</option>' +
							'<option value="F" '+ (data === "F" ? 'selected' : '')+ '>女</option>' +
						'</select>';
				},
			},
			{
				title: "生年月日",
				data: "birthday",
				visible: true,
				render: function (data, type, row, meta) {
					return '<input type="date" name="birthday" value="' + data + '">';
				},
			},
			{
				title: "入社年月日",
				data: "enteringDate",
				visible: true,
				render: function (data, type, row, meta) {
					return '<input type="date" name="enteringDate" value="' + data + '">';
				},
			},
			{
				title: "退社年月日",
				data: "retirementDate",
				visible: true,
				render: function (data, type, row, meta) {
					return '<input type="date" name="retirementDate" value="' + data + '">';
				},
			},
			{
				title: "部署",
				data: "departmentId",
				visible: true,
				render: function(data, type, row, meta) {
					let html = "";
					html += '<select name="departmentId">';
					html += '<option value=""></option>';
					for (let department of departmentList) {
						if (data === department.departmentId) {
							html += '<option value=' + department.departmentId + ' selected>' + department.departmentName + '</option>'
						} else {
							html += '<option value=' + department.departmentId + '>' + department.departmentName + '</option>'
						}
					}
					html += '</select>'
					return html;
				},
			},
		],
	});
}


function showModal(messages) {
	if (Array.isArray(messages)) {
		// メッセージが配列の場合
		$("#messageModalText").text(messages.join("\r\n"));
	} else {
		$("#messageModalText").text(messages);
	}
	$("#messageModal").modal();
}

