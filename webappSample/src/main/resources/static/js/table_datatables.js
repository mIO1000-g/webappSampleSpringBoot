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
					{ title: "No."			,data: "no"},
					{ title: "社員ID"		,data: "employeeId"},
					{ title: "社員名"		,data: "name"},
					{ title: "性別"			,data: "gender"},
					{ title: "生年月日"		,data: "birthday"},
					{ title: "入社年月日"	,data: "enteringDate"},
					{ title: "退社年月日"	,data: "retirementDate"},
					{ title: "部署"			,data: "departmentId"},
				],
			});
			
			initialized = true;

		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("失敗");
		});
	});

});