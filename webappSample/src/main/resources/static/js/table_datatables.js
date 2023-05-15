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

			if (initialized) {
				// 繰り返し検索した場合、同一idで再初期化できないため、先にDataTablesを破壊し、かつDOMを削除する。
				$("#mytable").DataTable().destroy();
				$("#mytable > tr").empty();
			}

			// DataTablesの初期化
			$("#mytable").DataTable({
				data: data,
				searching: false,
				lengthChange: false,
				pageLength: 8,
				columns: [
					{
						title: "No.",
						data: "no",
						visible: true,
					},
					{
						title: "社員ID",
						data: "employeeId",
						visible: true,
					},
					{
						title: "社員名",
						data: "name",
						visible: true,
						render: function (data, type, row, meta) {
							return '<input type="text" name="detail[' + meta.row + '].name" value="' + data + '">';
						},
					},
					{
						title: "性別",
						data: "gender",
						visible: true,
						render: function (data, type, row, meta) {
							return '<select name="detail[' + meta.row + '].gender">' +
									'<option value=""></option>' +
									'<option value="M">男</option>' +
									'<option value="F">女</option>' +
								'</select>';
						},
					},
					{
						title: "生年月日",
						data: "birthday",
						visible: true,
						render: function (data, type, row, meta) {
							return '<input type="date" name="detail[' + meta.row + '].birthday" value="' + data + '">';
						},
					},
					{
						title: "入社年月日",
						data: "enteringDate",
						visible: true,
					},
					{
						title: "退社年月日",
						data: "retirementDate",
						visible: true,
					},
					{
						title: "部署",
						data: "departmentId",
						visible: true,
					},
					{
						title: "",
						data: "newLine",
						width: 0,
						orderable: false,
						visible: true,
						render: function (data, type, row, meta) {
							return '<input type="hidden" name="detail[' + meta.row + '].newLine" value="' + data + '">';
						},
					},
					{
						title: "",
						data: "updateDate",
						width: 0,
						orderable: false,
						visible: true,
						render: function (data, type, row, meta) {
							return '<input type="hidden" name="detail[' + meta.row + '].updateDate" value="' + data + '">';
						},
					},
				],
			});
			
			initialized = true;

		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("失敗");
		});
	});

});