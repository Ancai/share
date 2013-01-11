<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link  href="${basePath}/styles/pageCommon.css" rel="stylesheet" type="text/css" />
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
		margin: 10px 50px 10px 30px;
	}
	.checkItemDiv div span {
		width: 180px;
		margin: 5px 0 5px 0;
		float: left;
	}

</style>
<script type="text/javascript">
     $(function(){
        $("#addButton").click(function(){
             showWin("aabc","{basePath}/welcomeAction_addUI.do","400","400",true);
        });
        $('#ss').timespinner({  
            required: true,  
            showSeconds: true  
        }); 
   	  customTable_resize();
  	});
</script>
</head>
<body>
     <div id="divTitle">
        <div id="divTitle_inline">
             <img src="${basePath}/images/user.png" id="img"></img><span id="menuName">我要检查</span>
        </div>
      </div>
       <center>
	      <div id="center">
	     	  <div class="checkDiv">
	     	  	<div class="checkTypeDiv">
	     	  		<span>检查类型：</span>
	     	  		<span>
		     	  		<select>
		     	  			<option>IP地址</option>
		     	  			<option>IP段</option>
		     	  		</select>
	     	  		</span>
	     	  		<input type="text" name="ip" size="25">
     	  		</div>
     	  		<div class="checkCntDiv">
     	  			<span>检查内容：</span>
     	  			<div class="checkItemDiv">
     	  				<div>
		     	  			<span>
		     	  				<input type="checkbox" name="">基本信息
		   	  				</span>
		     	  			<span>
		     	  				<input type="checkbox" name="">用户信息
		   	  				</span>
		     	  			<span>
		     	  				<input type="checkbox" name="">进程信息
		   	  				</span>
	   	  				</div>
	   	  				<div>
	   	  					<span>
		     	  				<input type="checkbox" name="">网络端口连接
		   	  				</span>
		     	  			<span>
		     	  				<input type="checkbox" name="">未打补丁
		   	  				</span>
		     	  			<span>
		     	  				<input type="checkbox" name="">上网痕迹
		   	  				</span>
	   	  				</div>
	   	  				<div>
	   	  					<span>
		     	  				<input type="checkbox" name="">U盘使用记录
		   	  				</span>
		     	  			<span>
		     	  				<input type="checkbox" name="">无线设备
		   	  				</span>
		     	  			<span>
		     	  				<input type="checkbox" name="">安装软件
		   	  				</span>
	   	  				</div>
	   	  				<div>
	   	  					<span>
		     	  				<input type="checkbox" name="">服务列表
		   	  				</span>
		     	  			<span>
		     	  				<input type="checkbox" name="">多操作系统
		   	  				</span>
		     	  			<span>
		     	  				<input type="checkbox" name="">文件内容
		   	  				</span>
	   	  				</div>
   	  				</div>
     	  		</div>
     	  		<div>
     	  			<button class="button" id="exportBtn">
	                   <span><img   src="${basePath}/images/button/weighting.png"></img></span>
	                   <span>立即执行检查</span>            
	                </button>
     	  		</div>  
	     	  </div> 
	      </div>
      </center>
 </body>
</html>