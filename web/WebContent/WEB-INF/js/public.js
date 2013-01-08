//global variable
var basePath = "/web"; //页面的基准路径
var cookieUsername = "currentUser"; //COOKIE: 用户名
var cookieUserId = "userId"; //COOKIE: 用户编号
//改变验证码
function changeCheckcode() {//Refresh  checkCode
	var $img = $("img[id='checkCodeImg']") ;
    $img.attr( "src", basePath + "/checkcode?timeStamp="+new Date().getTime() );
}

//获得后台传递的JSON格式的数据
var getAjaxJson = function(url, params) {
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
//获得后台传递的数据
var getAjaxData = function(url, params, dataType) {
	var jsonData = null;
	$.ajax({
		type: "post",
		url: url,
		data: params,
		dataType: dataType,
		async: false,
		success: function(resp) {
			jsonData = resp;
		}
	});
	return jsonData;
};

//判断是否为空
function isEmpty(val) {
	var flag = false;
	if (null === val ||
			val.length === 0 ||
			val.replace(/(^\s*)|(\s*$)/g, "") === "" //去除前后空格
	   ) {
		flag = true;
	}
	return flag;
}