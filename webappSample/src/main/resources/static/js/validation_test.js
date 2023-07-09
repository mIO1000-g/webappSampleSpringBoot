/**
 * 単票更新
 */
$(function() {

	$("#validateB").on("click", function() {
		console.log("クリック");
		let form = document.getElementById("form");
		form.action = "/webappSample/validation_test/validate";
		form.submit();
	});
	
		$("#smartValidateB").on("click", function() {
		console.log("クリック");
		let form = document.getElementById("form");
		form.action = "/webappSample/validation_test/smart_validate";
		form.submit();
	});
});