<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/CCP/jsps/public/common.jspf"%>
 <script type="text/javascript" src="${basePath}/scripts/other/UUID.js""></script>
<title>${systemName}》检查结果》我要检查</title>
<style type="text/css">
	.checkDiv {
		padding: 25px 200px 25px 200px;
	}
	.checkDiv .checkTypeDiv {
		margin: 15px 20px 15px 20px;
		padding: 10px 15px 10px 15px;
	}
	.checkDiv .checkCntDiv {
		margin: 15px 20px 15px 20px;
		padding: 10px 15px 10px 15px;
		height: 400px;
	}
	.checkCntDiv .checkItemDiv {
		margin: 10px 50px 10px 68px;
	}
	.checkItemDiv div span {
		width: 180px;
		margin: 5px 0 5px 0;
		float: left;
	}
	.btnDiv {
		margin: 0 15px 0 30px;
	}
	#messageDiv {
		padding: 50px;
	}
	#toolTip_div { 
		width:150px;
		height:250px; 
		background:#B0E0E6; 
		border-radius:10px;
		position:fixed;
		position:absolute; 
		right:30px; 
		top:0px;
	 }
	 #toolTip_div{
		display:none;
	}
	.toolTip{
		display:none;
	}
	.templateSpan {
		margin: 50px 15px 10px 15px;
		padding: 30px 15px 10px 15px;
	}
	#templateDiv{
		padding: 20px 15px 20px 20px;
	}
</style>
<script type="text/javascript">
     $(function(){
   	  	//页面加载时隐藏三个隐藏域
   		$('#hide1, #hide2, #hide3, #messageDiv, #templateDiv').window('close');
		//页面加载时默认将复选框选中
		$(":checkbox[name='checkItem.netRecord.checkType']").attr("checked","true");
		$(":checkbox[name='checkItem.netDevice.checkType']").attr("checked","true");

		//设置Policy的名称
		$("#policyNameInput").val("我要检查-" + UUID.prototype.createUUID());

		var $desktopWindow = $(getDeskTopObj());
	 	var h = $desktopWindow.height() * 0.99 - $("#divTitle").outerHeight();
		$("#center").height(h);

		window.onscroll = window.onresize = window.onload = function(){
			 
			 var odiv = document.getElementById("toolTip_div");
			 var sTop = document.documentElement.scrollTop || document.body.scrollTop;
			 var cHeight= document.documentElement.clientHeight || document.body.clientHeight;
			 var mid = (cHeight - odiv.offsetHeight) / 3; //控制提示框上下位置（当前位置为页面顶端下(1/3整个页面高度)）
			 // odiv.style.top = parseInt(sTop + mid) + "px";   //理论上应该只设这句就可以居中吧
			 
			 //实际上的 各大浏览器只认识下面这if判断
			 if(navigator.appVersion.indexOf("MSIE 6")> -1){
			  odiv.style.top = parseInt(sTop + mid) + "px";
			 }else{
			  odiv.style.top =mid + "px";
			 }
		}

		//模板下拉框
		var dataUrl = "${basePath}/iCheckAction_templateList.do";
		$("#templateCombox").combobox({  
		    url: dataUrl,  
		    valueField: 'key',  
		    textField: 'val',
		    editable: false,
		    onSelect: function(record) {
		    	window.location.href = "${basePath}/iCheckAction_iCheckUI.do?template.id=" + record.key;
		    	$("#delTempBtn").show();
		    	$("#saveTempBtn").show();
		    }
		}); 
		$("#templateCombox").combobox("select","<s:property value='template.id'/>");
		//保存模板
		$("#saveTempBtn").click(function() {
			$("#templateDiv").window('open');
  		});
		$("#tempNameBtn").click(function() {
			$("#templateInput").val($("#tempInput").val());
			$.post('${basePath}/iCheckAction_saveTemplate.do',$("#form").serialize(),function(msg){
				if(msg=='success'){
					$("#templateDiv").window('close');
					showAlertDialog("成功", "模板保存成功！", "info");
					$('#templateCombox').combobox('reload', dataUrl);
					$("#saveTempBtn").show();
					$("#delTempBtn").hide();
				}else{
					showAlertDialog("失败", "模板保存失败！", "info");
				}
			});
		});
		
		//删除模板
		$("#delTempBtn").click(function() {
			$.post('${basePath}/iCheckAction_delTemplate.do', {"template.id": "<s:property value='template.id'/>"}, function() {
				$("#templateCombox").combobox("clear");
				$('#templateCombox').combobox('reload', dataUrl);
			});
		});

		//选择黑白名单
		$(".traceMode").click(function() {
			$('#whichList').removeAttr("name");
	 		var value = $(":radio[name='checkItem.netRecord.mode'][checked]").val();
	 		if(value=='0'){
	 			$('#whichList').attr("name","checkItem.netRecord.whiteList");
	 		}
	 		if(value=='1'){
	 			$('#whichList').attr("name","checkItem.netRecord.blackList");
	 		}
		});
		//定位黑白名单列表
		setTraceList();
 });
   //等待上报数据
 	function waitReport() {
 		$.post("${basePath}/auditAction_iCheckResult.do",
 			{policyName: $("#policyNameInput").val()},
 			function(msg){
 				if ("success" === msg) {
 					window.location.href = "${basePath}/auditAction_viewReportUI.do";
 				} else	{
 					setTimeout("waitReport()", 3000);
 				}
 			});
 	};

   //全选事件
 	function isCheckAll(){
 		var Check = $('#CheckAllbutton').attr("checked");
 		if (Check) {
 			$("input[class='checkAll']").attr("checked",Check);
 		} else {
 			$("input[class='checkAll']").removeAttr("checked");
 		}
 	}
 	function showToolTip(obj){
 		$('#toolTip_div').show();
 		$('#toolTip'+obj).show();
 	}
   	function hideToolTip(){
 		$('#toolTip_div').hide();
 		$('.toolTip').hide();
 	  }
function showInput(){
	var value = $('#selectIP').val();
	if(value=='doubleIP'){
		$('#number').show();
		$('#addInput').append('<input type="text" id="hideInput" name="policyAssign.ipVal1" size="25"/>');
		}
	if(value=='singleIP'){
		$('#number').hide();
		$('#hideInput').remove();
		}			
 }

function subForm(){
	$.post('${basePath}/policyAction_add.do',$("#form").serialize(),function(msg){
		if(msg=='success'){
			$("#messageDiv").window('open');
			$("#messageDiv span").html("<img src='${basePath}/images/loading.gif' title='加载中'>正在查询，请稍候……");
			waitReport();
		}else{
			showAlertDialog("失败", "任务检查失败！", "info");
		}
	});	
};
//选择一个模板时，定位黑白名单
function setTraceList() {
	var value = $(":radio[name='checkItem.netRecord.mode'][checked]").val();
		if(value=='0'){
			$('#whichList').attr("name","checkItem.netRecord.whiteList");
			$('#whichList').val($("#whiteList").val());
		}
		if(value=='1'){
			$('#whichList').attr("name","checkItem.netRecord.blackList");
			$('#whichList').val($("#blackList").val());
		}
}
</script>
</head>
<body>
     <div id="divTitle">
        <div id="divTitle_inline">
             <img src="${basePath}/images/user.png" id="img"></img><span id="menuName">我要检查</span>
        </div>
     </div>
    
<!--    文件内容》白名单-->
     <input type="hidden" id="whiteList" value="<s:property value='checkItem.netRecord.whiteList'/>"/>
<!--     文件内容》黑名单-->
     <input type="hidden" id="blackList" value="<s:property value='checkItem.netRecord.blackList'/>"/>
   <form id="form" style="margin:0;padding:0;">
       <center>
	      <div id="center">
	     	  <div class="checkDiv">
	     	    <div class="checkTypeDiv">
	     	    	<span>使用模板：</span>
	     	    	<span>
	     	    		<input id="templateCombox" value="" style="height: 50px;width: 258px;"> 
	     	    	</span>
	     	    </div>
	     	  	<div class="checkTypeDiv" id="addInput">
	     	  		<span style="display:none;">
	     	  			<input id="policyNameInput" type="text" name="policy.name" value="我要检查"/>
	     	  			<input type="text" name="schedule.mode" value="1"/>
	     	  			<input type="text" name="policyAssign.type" value="IP"/>
	     	  		</span>
	     	  		<span>检查类型：</span>
	     	  		<span>
		     	  		<select onchange="showInput()" id="selectIP">
		     	  			<option value="singleIP">IP地址</option>
		     	  			<option value="doubleIP">IP段</option>
		     	  		</select>
	     	  		</span>
	     	  		<s:textfield name="policyAssign.ipVal1" size="25"/>
	     	  		<span id="number" style="display:none;">
	     	  			至
	     	  		</span>
     	  		</div>
     	  		<div class="checkCntDiv">
     	  			<span>检查内容：</span>
     	  			<span><input type="checkbox" id="CheckAllbutton" onclick="isCheckAll()">全选</span>
     	  			<div class="checkItemDiv">
     	  				<div>
		     	  			<span>
		     	  				<s:checkbox cssClass="checkAll" name="checkItem.baseInfo" /><label onmouseover="showToolTip(1)" onmouseout="hideToolTip()">基本信息</label>
		   	  				</span>
		     	  			<span>
		     	  				<s:checkbox cssClass="checkAll" name="checkItem.userList" /><label onmouseover="showToolTip(2)" onmouseout="hideToolTip()">用户信息</label>
		   	  				</span>
		     	  			<span>
		     	  				<s:checkbox cssClass="checkAll" name="checkItem.process" /><label onmouseover="showToolTip(3)" onmouseout="hideToolTip()">进程信息</label>
		   	  				</span>
	   	  				</div>
	   	  				<div>
	   	  					<span>
		     	  				<s:checkbox cssClass="checkAll" name="checkItem.netPort" /><label onmouseover="showToolTip(5)" onmouseout="hideToolTip()">网络端口连接</label>
		   	  				</span>
		     	  			<span>
		     	  				<s:checkbox cssClass="checkAll" name="checkItem.unPatch" /><label onmouseover="showToolTip(6)" onmouseout="hideToolTip()">未打补丁</label>
		   	  				</span>
		     	  			<span>
		     	  				<s:checkbox cssClass="checkAll" name="checkItem.netRecord.check" /><label onmouseover="showToolTip(9)" onmouseout="hideToolTip()">上网痕迹</label>
		     	  				&nbsp;&nbsp;<a style="color:red;cursor:pointer;" onclick="$('#hide3').window('open')">配置</a>
		   	  				</span>
	   	  				</div>
	   	  				<div>
	   	  					<span>
		     	  				<s:checkbox cssClass="checkAll" name="checkItem.udiskRecord" /><label onmouseover="showToolTip(12)" onmouseout="hideToolTip()">U盘使用记录</label>
		   	  				</span>
		     	  			<span>
		     	  				<s:checkbox cssClass="checkAll" name="checkItem.netDevice.check" /><label onmouseover="showToolTip(8)" onmouseout="hideToolTip()">无线设备</label>
		     	  				&nbsp;&nbsp;<a style="color:red;cursor:pointer;" onclick="$('#hide2').window('open')">配置</a>
		   	  				</span>
		     	  			<span>
		     	  				<s:checkbox cssClass="checkAll" name="checkItem.softList" /><label onmouseover="showToolTip(7)" onmouseout="hideToolTip()">安装软件</label>
		   	  				</span>
	   	  				</div>
	   	  				<div>
	   	  					<span>
		     	  				<s:checkbox cssClass="checkAll" name="checkItem.serviceList" /><label onmouseover="showToolTip(11)" onmouseout="hideToolTip()">服务列表</label>
		   	  				</span>
		     	  			<span>
		     	  				<s:checkbox cssClass="checkAll" name="checkItem.mutiOS" /><label onmouseover="showToolTip(10)" onmouseout="hideToolTip()">多操作系统</label>
		   	  				</span>
		     	  			<span>
		     	  				<s:checkbox cssClass="checkAll" name="checkItem.fileCheck.check" /><label onmouseover="showToolTip(4)" onmouseout="hideToolTip()">文件内容</label>
		     	  				&nbsp;&nbsp;<a style="color:red;cursor:pointer;" onclick="$('#hide1').window('open')">配置</a>
		   	  				</span>
	   	  				</div>
	   	  				<div>
	   	  					<span></span>
	   	  					<span><input type="button" onclick="subForm()" value="立即检查"/></span>
	   	  					<s:if test="template.id != null">
	   	  					<span><input type="button" id="delTempBtn" value="删除模板"/></span></s:if>
	   	  					<s:else>
	   	  					<span><input type="button" id="saveTempBtn" value="保存模板"/></span></s:else>
	   	  				</div>
   	  				</div>
   	  				
     	  		</div>
     	  		<!-- 等待提示窗  -->
     	  		<div id="messageDiv" class="easyui-window" style="width:300px;height:200px"; data-options="title:'等待',modal:true">
     	  			<span></span>
     	  		</div>
     	  		<!-- 保存模板之填写名称  -->
     	  		<input id="templateInput" type="hidden" name="template.name" value=""/>
     	  		<div id="templateDiv" class="easyui-window" style="width:340px;height:110px"; data-options="title:'填写名称',modal:true">
     	  			<span>
     	  				模板名称： <input id="tempInput" type="text" value=""/>
     	  			 	<input id="tempNameBtn" type="button" value="确定"/>
   	  			 	</span>
     	  		</div>
       			</div>   
       				<!-- 隐藏内容（3部分） -->
      	  			<div id="hide1" class="easyui-window" data-options="title:'文件内容检查',inline:true" style="width:600px;height:420px;padding:7px;top:45%;">  
          				<table>
          					<thead><tr><td><h3>文件内容检查子项</h3></td></tr></thead>
							<tr>
								<td>选择盘符</td>
								<td>
									<s:checkboxlist list="#request.diskList" name="keysDisk" listKey="key" listValue="val"/>
								</td>
							</tr>
							<tr>
								<td>文件后缀名称：</td>
								<td>
									<s:checkboxlist list="#request.extNameList" name="keysExtName" listKey="key" listValue="val"/>
								</td>
							</tr>
							<tr>
								<td>结合模式</td>
								<td><s:radio name="checkItem.fileCheck.combineMode" list="%{#{'0':'或','1':'与'}}"></s:radio></td> 
							</tr>
							<tr>
								<td>是否匹配空格</td>
								<td>
								<s:radio name="checkItem.fileCheck.compareWithSpace" list="%{#{'0':'否','1':'是'}}"></s:radio>
								<span style="color:red;">*如果匹配空格，检查内容与文件内容中必须要空格一致才能检测到</span>
								</td>
							</tr>
							<tr>
								<td>只检查文件名</td>
								<td>
									<s:radio name="checkItem.fileCheck.onlyCheckName" list="%{#{'0':'否','1':'是'}}" ></s:radio>
									<span style="color:red;">&nbsp;&nbsp;*选中'是'则只检测文件名字，不检测文件内容</span>
								</td>
							</tr>
							<tr id="numBytes" >
								<td>检查文件内容前</td>
								<td><s:textfield name="checkItem.fileCheck.checkByteCount"/>字节数<span style="color:red;">*说明：为空时检查全部内容</span></td>
							</tr>
							<tr>
								<td>检查内容</td>
								<td><s:textfield id="antest" name="checkItem.fileCheck.content"/></td>
							</tr>
							<tr>
								<td>创建文件时间</td>
								<td>
									<s:textfield id="container1" cssClass="easyui-datetimebox" name="checkItem.fileCheck.createFileTime1" cssStyle="width:160px"/>
									&nbsp;-&nbsp;
									<s:textfield id="container2" cssClass="easyui-datetimebox" name="checkItem.fileCheck.createFileTime2" cssStyle="width:160px"/>
								</td>
							</tr>
							<tr>
								<td>修改文件时间</td>
								<td>
									<s:textfield id="container3" cssClass="easyui-datetimebox" name="checkItem.fileCheck.modifyFileTime1" cssStyle="width:160px"/>
									&nbsp;-&nbsp;
									<s:textfield id="container4" cssClass="easyui-datetimebox" name="checkItem.fileCheck.modifyFileTime2" cssStyle="width:160px"/>
								</td>
							</tr>	
							<tr>
								<td>对已删除文件检查</td>
								<td><s:radio name="checkItem.fileCheck.delFileCheck" list="%{#{'0':'否','1':'是'}}"/>
								</td>
							</tr>
					</table>
					<center>
						<input type="button" value="确定" onclick="$('#hide1').window('close');">
					</center>
       			</div>
		     <!-- 无线设备检查子项 -->   
		       	<div id="hide2" class="easyui-window"   data-options="title:'无线设备检查',inline:true" style="width:380px;height:170px;padding:7px;top:45%;">  
		        	<table>
		        		<thead><tr><td><h3>无线设备检查子项</h3></td></tr></thead>
						<tr>
							<td>
								<s:checkboxlist list="#request.wirelessList" name="keysWireless" listKey="key" listValue="val"/>
							</td>
						</tr>
					</table>
					<center><input type="button" value="确定" onclick="$('#hide2').window('close');"></center>
		        </div>
		     <!-- 上网痕迹检查子项 -->
		       	<div id="hide3" class="easyui-window" data-options="title:'上网痕迹检查',inline:true" style="width:480px;height:310px;padding:7px;top:45%">  
		        	<table>
		        		<thead><tr><td><h3>上网痕迹检查子项</h3></td></tr></thead>
						<tr>
							<td>检查模式：</td>
							<td><s:radio name="checkItem.netRecord.mode" list="%{#{'0':'白名单','1':'黑名单'}}" cssClass="traceMode"/></td>
						</tr>
						<tr>
							<td>名单列表</td>
							<td><s:textarea  id="whichList" style="height:80px;width:280px;"></s:textarea></td>
						</tr>
						<tr>
							<td></td>
							<td><span style="color:red;">*各地址请以分号间隔.如：www.qq.com;www.sina.com</span></td>
						</tr>
						<tr>
							<td>检查位置：</td>
							<td><s:checkboxlist name="keysLocation" list="#request.traceList" listKey="key" listValue="val"/></td>
						</tr>
					</table>
					<center><input type="button" value="确定" onclick="$('#hide3').window('close');"></center>
		      	</div>   
     	  	</div>  
      </center>
   </form>
   <!--              消息提示栏                  -->
	<div id="toolTip_div">
	    <div><img src="${basePath }/images/public/splash_green.png"><span style="color:red;font-size:15px;">提示：</span></div>
	    <div id="toolTip1" class="toolTip">基本信息检查<br/>该检查主要检查计算机名、IP及MAC地址、操作系统、内存大小等信息</div>
	    <div id="toolTip2" class="toolTip">计算机用户信息检查<br/>该检查主要检查使用计算机用户的基本信息</div>
	    <div id="toolTip3" class="toolTip">进程信息检查<br/>该检查主要是检查用户计算机当前进程数、进程名、以及开启此进程的用户</div>
	    <div id="toolTip4" class="toolTip">文件内容检查<br/>该检查项主要检查指定的文件的内容、信息</div>
	    <div id="toolTip5" class="toolTip">网络连接端口检查<br/>该检查项主要检查计算机网络连接的端口、IP地址等信息</div>
	    <div id="toolTip6" class="toolTip">未打补丁检查<br/>该检查项主要检查计算机未打补丁名称、补丁号、补丁的描述等信息</div>
	    <div id="toolTip7" class="toolTip">已安装软件检查<br/>该检查项主要检查计算机已安装软件的名称、版本信息、安装时间</div>
	    <div id="toolTip8" class="toolTip">无线设备检查<br/>该检查项主要检查无线设备的型号、首次和最后一次使用时间</div>
	    <div id="toolTip9" class="toolTip">上网痕迹检查<br/>该检查项主要检查浏览过的网络地址，访问时间等信息</div>
	    <div id="toolTip10" class="toolTip">多操作系统检查<br/>该检查项主要检查计算机多操作系统的系统名称、安装时间、安装路径等信息</div>
	    <div id="toolTip11" class="toolTip">服务列表信息检查<br/>该检查项主要检查服务名称、服务目前状态、该服务公司的信息以及描述</div>
	    <div id="toolTip12" class="toolTip">U盘使用记录检查<br/>该检查项主要检查U盘型号、首次使用和最后一次使用U盘的时间等</div>
	</div>
 </body>
</html>