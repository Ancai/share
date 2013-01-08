//以一个对象的x和y属性的方式返回滚动条的偏移量
function getScrollOffsets(w) {
	w = w || window;
	//除了IE8及更早的版本以外，其它浏览器都能用
	if (w.pageXOffset != null) {
		return {x: w.pageXOffset, y: w.pageYOffset};
	}
	
	//对标准模式下的IE
	var d = w.document;
	if (document.compatMode == "CSS1Compat") {
		return {x: d.documentElement.scrollLeft, y: d.documentElement.scrollTop};
	}
	
	//对怪异模式下的浏览器
	return {x: d.body.scrollLeft, y: d.body.scrollTop};
}
function drag(elementToDrag, event) {
	//初始鼠标位置，转换为文档坐标
	var scroll = getScrollOffsets();
	var startX = event.clientX + scroll.x;
	var startY = event.clientY + scroll.y;
	
	var origX = elementToDrag.offsetLeft;
	var origY = elementToDrag.offsetTop;
	
	var deltaX = startX - origX;
	var deltaY = startY - origY;
	
	if (document.addEventListener) {
		document.addEventListener("mousemove", moveHandler, true);
		document.addEventListener("mouseup", upHandler, true);
	} else if (document.attachEvent) {
		elementToDrag.setCapture();
		elementToDrag.attachEvent("onmousemove", moveHandler);
		elementToDrag.attachEvent("onmouseup", upHandler);
		elementToDrag.attachEvent("onlosecapture", upHandler);
	}
	
	//现在阻止任何默认的操作
	if (event.preventDefault) {
		event.preventDefault();
	} else {
		event.returnValue = false;
	}
	
	//当元素正在被拖动时，捕获mousemove事件处理程序
	function moveHandler(e) {
		if (!e) {
			window.event;
		}
		
		//移动这个元素到当前鼠标位置
		//通过滚动条的位置和初始单击的偏移量来调整
		var scroll = getScrollOffsets();
		elementToDrag.style.left = (e.clientX + scroll.x - deltaX) + "px";
		elementToDrag.style.top = (e.clientY + scroll.x - deltaY) + "px";
		
		//同时不让任何其他元素看到这个事件
		if (e.stopPropagation) {
			e.stopPropagation();//标准
		} else {
			e.cancelBubble = true;//IE
		}
	}
	
	//这是捕获在拖动结束时发生的最终mouseup事件的处理程序
	function upHandler(e) {
		if (!e) e = window.event;//IE事件模型
		
		//注销捕获事件处理程序
		if (document.removeEventListener) {
			document.removeEventListener("mouseup", upHandler, true);
			document.removeEventListener("mousemove", moveHandler, true);
		} else if (document.detachEvent) { //IE5+ 事件模型
			elementToDrag.detachEvent("onlosecapture", upHandler);
			elementToDrag.detachEvent("onmouseup", upHandler);
			elementToDrag.detachEvent("onmousemove", moveHandler);
			elementToDrag.releaseCapture();
		}
		
		//并且    不让事件进一步传播
		if (e.stopPropagation) e.stopPropagation();//标准模型
		else e.cancelBubble = true;
	}
}