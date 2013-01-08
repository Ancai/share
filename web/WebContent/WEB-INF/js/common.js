//被common.jsp引用
function add_event(tr){
	tr.onmouseover = function(){
		tr.className += ' hover';
	};
	tr.onmouseout = function(){
		tr.className = tr.className.replace(' hover', '');
	};
}
 
function stripe(table) {
	var trs = table.getElementsByTagName("tr");
	for(var i=1; i<trs.length; i++){
		var tr = trs[i];
		tr.className = i%2 != 0? 'odd' : 'even';
		add_event(tr);
	}
}

window.onload = function(){
	var tables = document.getElementsByTagName('table');
	for(var i=0; i<tables.length; i++){
		var table = tables[i];
		if(table.className == 'datagrid'){
			stripe(tables[i]);
		}
	}
};
//获得后台传递的JSON格式的数据
var getAjaxData = function(url, params) {
	var jsonData = null;
	$.ajax({
		type: "post",
		url: url,
		data: params,
		dataType: "json",
		async: false,
		success: function(resp) {
			jsonData = resp;
		}
	});
	return jsonData;
};