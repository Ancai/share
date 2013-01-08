//web index page used
function dateLabel(){
	var weeks = ["一", "二", "三", "四", "五", "六", "日"];
	var d = new Date();
	var yy = d.getFullYear();
	var mm = d.getMonth() + 1;
	var dd = d.getDate();
	var ww = d.getDay();
	//var ss = parseInt(d.getTime() / 1000);
	if (yy < 100) yy = "19" + yy;
	var lunar = GetLunarDay(yy, mm, dd);
	//09月28日 周五 农历八月十三
	return mm + "月" + dd + "日 周" + weeks[ww-1] + "[" + lunar + "]";
}

function searchQuery(){
	var sites = [{name: "https://www.google.com.hk/", key: "#hl=zh-CN&newwindow=1&safe=strict&site=&source=hp&q"}, 
	             {name: "http://www.baidu.com/s", key: "wd"}, 
	             {name: "http://www.soso.com/q", key: "w"}, 
	             {name: "http://www.sogou.com/web", key: "query"}, 
	             {name: "http://www.youdao.com/search", key: "q"}, 
	             {name: "http://www.bing.com.cn", key: "q"}, 
	             {name: "http://www.yahoo.cn/s", key: "q"}, 
	             {name: "http://www.gougou.com/search", key: "search"}, 
	             {name: "http://so.360.cn/s", key: "q"}, 
	             {name: "http://www.jike.com/so", key: "q"}, 
	             {name: "http://search.panguso.com/pagesearch.htm", key: "q"}];
	var index = parseInt($("#searchType label input[type=radio]:checked").attr("id").substr(11, 2), 10) - 1;
	window.open(sites[index].name + "?" + sites[index].key + "=" + $("#queryTxt").val()).fullScreen();
	
}

function checkLogin() {//check is login
	if (getCookie(cookieUsername) != null) {
		$("#loginLabel, #registLabel").hide();
		$("#userLabel").html(getCookie(cookieUsername)).show();
		$("#exitLabel").show();
	} else {
		$("#loginLabel, #registLabel").show();
		$("#userLabel").html(getCookie(cookieUsername)).hide();
		$("#exitLabel").hide();
	}
}

function exitSys() {// exit System
	$.ajax({  
        url : basePath + "/index/exit",  
        type : "POST",  
        dataType : "text",  
        success : function(resp) {
        	if ("SUCCESS" == resp) {
        		$("#loginLabel, #registLabel").show();
        		$("#userLabel").hide();
        		$("#exitLabel").hide();
        		delCookie(cookieUsername);
        		delCookie(cookieUserId);
			}
        }  
   });
}


function myWebSite() {
	var data = getAjaxJson(basePath + "/index/isites");
	var dataHtml = "<ul class='famousSiteList'>";
	for ( var i in data) {
		dataHtml += "<li><a data-id='"+ data[i].id +"' href='"+ data[i].url +"' target='_blank'>"+ data[i].name +"</a></li>";
	}
	dataHtml += "</ul>";
	$("#tab2Div").html(dataHtml);
}

function saveISite() {
	$.ajax({
		type: "post",
		url: basePath + "/index/isites/save",
		data: {name: $("#mySite_name").val(), url: $("#mySite_url").val()},
		dataType : "text",
		async: false,
		success: function(resp) {
			if ("SUCCESS" == resp) {
        		$('#mySite').modal('hide');
        		myWebSite();//重新加载网址
        		$("#mysite_Add").attr("disabled", false);
        		$("#mySite_name").val("");
        		$("#mySite_url").val("");
			} else {
				$("#L_formTip").show();
			}
		}
	});
}

var flagDelUI = false;
function delISiteUI() {
	//$("#tab2Div ul li").html($(this).html() + "<i class='icon-remove-circle'></i>");
	var iconHtml = "<i class='icon-remove-circle' title='移除'/>";
	$("#tab2Div ul li").each(function() {
		var index = $(this).html().indexOf("icon-remove-circle");
		if ( index > -1) {
			$(this).html($(this).html().substring(0, index));
			$("#mysite_Remove_icon").attr("class", "icon-remove").parent().attr("title", "移除");
			$("#mysite_Add").show();
		} else {
			$(this).html($(this).html() + iconHtml) ;
			$("#mysite_Remove_icon").attr("class", "icon-ok").parent().attr("title", "完成");
			$("#mysite_Add").hide();
			$(".icon-remove-circle").click(function() { //Add remove event for every link
				if ("SUCCESS" === getAjaxData(basePath + "/index/isites/del", {"id": $(this).prev().attr("data-id")}, "text")) {
					$(this).parent().hide();
				}
			});
		}
	});
}

function linkCSS() {
	$(".row ul li a")
		.attr("target", "_blank")
		.css("color", "#666")
		.hover(function() {
			$(this).css("text-decoration", "underline").css("color", "#f30");
		}, function() {
			$(this).css("text-decoration", "none").css("color", "#666");
		});
}
// start call
$(document).ready(function () {
	linkCSS(); //Link Style
	checkLogin();
    $("#dateLabel").html(dateLabel()); //datetime label in nav bar
    $("#searchBtn").click(function(){searchQuery();}); //search engine
    $("#exitLabel").click(function(){exitSys();}); //exit System
    $("#mySite_tab2").click(function(){myWebSite();}); //load my webSites
    $("#mySite_submit").click(function() {saveISite();}); //Save I Web Site
    $("#mysite_Remove").click(function() {delISiteUI();}); //Remove I Web Site UI
});