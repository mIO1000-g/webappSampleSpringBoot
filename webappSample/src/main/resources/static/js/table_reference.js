/**
 * 一覧参照
 */
//

$(function() {
	// 明細部のリンク風ボタン押下時
	$(".btn-link").on("click", function() {
		let employeeId = $(this).attr("data-employeeId");
		// フォームにパラメータを追加して単票更新画面へ遷移
		let form = document.getElementById("form");
		form.action = "/webappSample/record_update/";
		form.method = "post";
		form.addEventListener("formdata",(e) => {
			let fd = e.formData;
			fd.set("employeeId", employeeId);
			fd.set("screenMode", "2");
		});
		form.submit();
	});
	
	// ページャ押下時
	$("a").on("click", function() {
		// 現在のページインデックスを取得
		let page = $(this).attr("data-page");
		let form = document.getElementById("form");
		form.action = "/webappSample/table_reference/search";
		form.method = "post";
		form.addEventListener("formdata",(e) => {
			let fd = e.formData;
			// 現在のページインデックスをリクエストパラメータに追加
			fd.set("page", page);
		});
		form.submit();
	});
})