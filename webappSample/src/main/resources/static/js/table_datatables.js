/**
 *
 */

$(function() {

	let initialized = false;

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
			console.log(_form);
			if (initialized) {
				// 繰り返し検索した場合、同一idで再初期化できないため、先にDataTablesを破壊し、かつDOMを削除する。
				$("#mytable").DataTable().destroy();
				$("#mytable > tr").empty();
			}

			let list = data.data;

			// DataTablesの初期化
			$("#mytable").DataTable({
				data: list,
				searching: false,
				lengthChange: false,
				pageLength: 8,
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
							return '<input type="text" name="employeeId" value="' + data + '" readonly>';
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
					{
						title: "",
						data: "newLine",
						width: 0,
						orderable: false,
						visible: true,
						render: function (data, type, row, meta) {
							return '<input type="hidden" name="newLine" value="' + data + '">';
						},
					},
					{
						title: "",
						data: "updateDate",
						width: 0,
						orderable: false,
						visible: true,
						render: function (data, type, row, meta) {
							return '<input type="hidden" name="updateDate" value="' + data + '">';
						},
					},
				],
			});

			initialized = true;

		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("失敗");
		});
	});

	$("#confirmB").on("click", function() {
		
		let count = 0;
		
		let _form = new FormData();
		
		$("#mytable tbody tr").each(function(index, row) {
			// テーブルの行を走査
			if ($(row).find("input:checkbox").is(":checked")) {
				// 選択されている行のみ
				$(row).find("input").each(function(index, element) {
					if (element.type === "checkbox") {
						// チェックボックスは除外
						return;
					}
					console.log(count);
					_form.append("detail[" + count + "]." + element.name, element.value);
				});
				count++;
			}
		});
		console.log(_form);

		$.ajax({
			type: "POST",
			data: _form,
			url: "/webappSample/table_datatables/confirm",
			processData: false,
			contentType: false,
			dataType: "json"

		}).done(function(data, textStatus, jqXHR) {
			console.log(_form);
			alert("成功");
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("失敗");
		});
	});

});
