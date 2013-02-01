//easyui扩展
/***
 * 
 * 
 * @param title 弹出框title
 * @param url  弹出框路径
 * @param height 弹出框高度
 * @param width  弹出框宽度
 * * @param mode 是否是模态窗口
 * @return 
 */
function showWin(title,url,height,width,modal){
 	$("#windowDiv").remove();
	$("body").append("<div id='windowDiv'></div>");
	$("#windowDiv").window({  
	    title:title,
	    width:width,  
	    height:height, 
	    cache: false,
 	    content:"<iframe src='"+url+"' width='100%' height='100%' name='iframeWindow' scrolling='auto' frameborder='0'>",  
	    modal: modal,
	    onClose : function(){
		var $o = $(this).parent();
		$o.next(".window-shadow").remove();
		$o.next(".window-mask").remove();
		$o.remove();
	} 
	});  
}

/***
 * 
 * @param title 提示框title
 * @param message  提示内容
 * @param type   提示图标 @'' @'error @info question warning
 * @return
 */
function alertDialog(title,message,icon){  
 		 if(icon){
			 $.messager.alert(title,message,icon);
		 }else{
			 $.messager.alert(title,message);
		 }
}
/**
 * @param title 确认框标题 
 * @param msg The message text to be showed.
 * @param fn(b): The callback function, when user click Ok button, pass a true value to function, otherwise pass a false to it.
 * @return
*/
function confirmDialog(title, msg, confirmFunction,cancelFunction){
	$.messager.confirm(title, msg , function(r){
		if(r){
			if(	jQuery.isFunction( confirmFunction ) ){
				confirmFunction.call();
			}
		}else{
			if(	jQuery.isFunction( cancelFunction ) ){
				cancelFunction.call();
			}
		}
	});
}
/**
 * @param 操作结果title
 * @param msg 操作结果框显示信息
 * @param state 操作是否成功 true为成功false为失败
 * @return
 */
function showResultMsg(title,msg,state){
	if(state){ 
		  msg = "<center><div style='margin-top:20px'><img src='images/button/success.png' style='height:16px;width:16px'><span style='font-size:13px;font-weight:bold;color:red;padding-left:5px;'>" + msg+"</span></center></div>";
	 }else{
		 msg = "<center><div style='margin-top:20px'><img src='images/button/fail.png' style='height:16px;width:16px'><span style='font-size:13px;font-weight:bold;color:red;padding-left:5px;'>" + msg+"</span></center></div>";
	 }
	 $.messager.show({
		title:title,
		msg:msg,
		timeout:4000,
		showType:'slide',
		style:{
           right:'',
           top:document.body.scrollTop+document.documentElement.scrollTop,
           bottom:''
        }
   });
}

/***
 * 取消弹出框
 * 
 */
function removeWin(){
 	$("#windowDiv").prev().find(".panel-tool-close").click();
}

//分页扩展
$.extend($.fn.datagrid.defaults, {
	pageSize: 15,
	pageList:[15, 30, 45]
});
