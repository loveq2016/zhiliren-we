/*单选按钮*/
$("input[name=internat]").click(function(){
	var typeCodeStr = "";
	switch($("input[name=internat]:checked").attr("id")){
	case "gjnlRadio":
		typeCodeStr = "GJNL";
		break;
	case "gjRadio":
		typeCodeStr = "GJ";

		break;
	}
	$.ajax({
		type : 'POST',
		data : {
			typeCode:typeCodeStr
		},
		dataType:'json',
		url : BASE_PATH+'/admin/search/initCityTypeCode.html',
		success : function(data) {

		},
		error : function() {
		}
	});
});


/*出发城市下拉列表*/
$("#outCity").select2({
	ajax : {
		url : BASE_PATH  + "/admin/search/getCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				cityname : params.term, 

				ids:$('#arriveCityCode').val(),
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.dictCode; 
				obj.text = obj.dictCode +"("+ obj.dictName +")";
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : true
	},

	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", 
	maximumSelectionLength : 1, 
	tags : false
});

/*抵达城市查询*/
$("#singleArriveCity").select2({
	ajax : {
		url : BASE_PATH  + "/admin/search/getCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				cityname : params.term, 
				ids:$('#outCityCode').val(),
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.dictCode; 
				obj.text = obj.dictCode +"("+ obj.dictName +")"; 
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : true
	},

	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", 
	maximumSelectionLength : 1, 
	tags : false
});


/*航空公司查询*/
$("#airline").select2({
	ajax : {
		url : BASE_PATH  + "/admin/search/getAirLineSelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				airlinename : params.term,
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.dictCode;
				obj.text = obj.dictCode +"-"+ obj.dictName; 
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : true
	},

	escapeMarkup : function(markup) {
		return markup;

	}, 
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN",
	maximumSelectionLength : 1, 
	tags : false,
});

/*------------------------搜索条件 下拉列表  start----------------------------*/
/*成人下拉列表*/
var _agentSelect = document.getElementById("agentSelect");
for ( i = 1; i <= 10; i++){  
	var _option = document.createElement("option");  
	_option.value = i;  
	_option.text = "成人"+i;  
	_agentSelect.appendChild(_option);  
}  
/*儿童下拉列表*/
var _childrenSelect = document.getElementById("childrenSelect");
for ( i = 1; i <= 10; i++){  
	var _option = document.createElement("option");  
	_option.value = i;  
	_option.text = "儿童"+i;  
	_childrenSelect.appendChild(_option);  
}  
/*婴儿下拉列表*/
var _babySelect = document.getElementById("babySelect");
for ( i = 1; i <= 10; i++){  
	var _option = document.createElement("option");  
	_option.value = i;  
	_option.text = "婴儿"+i;  
	_babySelect.appendChild(_option);  
}  
/*-----------------------搜索条件 下拉列表  end----------------------------*/

/*-----------------------select2隐藏域赋值  start------------------------*/
/* 客户姓名 */
linkNameOpt = function(){
	$("#linkNameValidator").val($('#linkNameId').find("option:selected").text());
}
/* 出发城市 */
outCityNameOpt = function (){
	var cityName = $('#outCity').find("option:selected").text();
	$("#outCityName").val(cityName);
	var selectedCityId = $("#outCity").select2("val");
	$("#outCityCode").val(selectedCityId);
}
/* 抵达城市 */
arriveCityNameOpt = function(){
	var cityName = $('#singleArriveCity').find("option:selected").text();
	$("#arriveCityName").val(cityName);
	var selectedCityId = $("#singleArriveCity").select2("val");
	$("#arriveCityCode").val(selectedCityId);
}
/* 航空公司 */
airlineNameOpt = function(){
	var airName = $('#airline').find("option:selected").text();
	$("#airlineName").val(airName);
	var selectedAirId = $("#airline").select2("val");
	$("#airlineCode").val(selectedAirId);
}
/*-----------------------select2隐藏域赋值  end----------------------------*/

/*-----------------------往返段数查询  start--------------------------------*/
$(document).on('click','#num01',function(){
	$("#airInfoList").val("1");
	$("#searchSingleTicketsBtn").click();
});
$(document).on('click','#num02',function(){
	$("#airInfoList").val("2");
	$("#searchSingleTicketsBtn").click();
});
/*-----------------------往返段数查询  end--------------------------------*/

/* -------------------------日期小卡片 start------------------------------- */
cardDate = function(v){
	var d = new Date(v.getAttribute("value"));
	var seperator1 = "-";
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var strDate = d.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var cardStr = year +'-'+ month +'-'+ strDate;
	$("#outDatepicker").val(cardStr);
	$("#searchSingleTicketsBtn").click();
}
getDateCard =function(){
	var dateNumHtml = "";
	var outStr = $("#outDatepicker").val();
	var outDate = new Date(outStr.replace(/-/g,"/"));
	var returnStr = $("#returnDatepicker").val();
	var returnDate = new Date(returnStr.replace(/-/g,"/"));
	/* 出发前的日期 */
	var time = outDate.getTime() - new Date().getTime() ; //日期的long型值之差
	var days = Math.floor(time/(24*60*60*1000))+1;
	if(days <=3){
		var dataCardHtml = "";
		for(var i=days; i>=1; i--){
			var beforeDate= outDate.getTime()- 1000*60*60*24*i;
			var changeDate=new Date(beforeDate);
			var formatDate = getNowFormatDate(changeDate);
			dataCardHtml += '<li onclick="cardDate(this);" value="'+changeDate+'" id="'+formatDate+'">'+formatDate+'</li>';
		}
	}else{
		var dataCardHtml = "";
		for(var i=3; i>=1; i--){
			var beforeDate= outDate.getTime()- 1000*60*60*24*i;
			var changeDate=new Date(beforeDate);
			dataCardHtml += '<li onclick="cardDate(this);" value="'+changeDate+'" id="'+getNowFormatDate(changeDate)+'">'+getNowFormatDate(changeDate)+'</li>';
		}
	}
	/* 出发日期 */
	dataCardHtml += '<li onclick="cardDate(this);" value="'+outDate+'" id="'+getNowFormatDate(outDate)+'" class="btnStyle">'+getNowFormatDate(outDate)+'</li>';
	/* 出发后的日期 */
	var travelTime = returnDate.getTime() - outDate.getTime();
	var travelDays = Math.floor(travelTime/(24*60*60*1000))+1;
	if(travelDays>3){
		var afterDate = outDate.getTime();
		for(var i=1; i<=3; i++){
			afterDate+=1000*60*60*24;
			var changeDate=new Date(afterDate);
			dataCardHtml += '<li onclick="cardDate(this);" value="'+changeDate+'" id="'+getNowFormatDate(changeDate)+'">'+getNowFormatDate(changeDate)+'</li>';
		}
	}else{
		var afterDate = outDate.getTime();
		for(var i=1; i<travelDays; i++){
			afterDate+=1000*60*60*24;
			var changeDate=new Date(afterDate);
			dataCardHtml += '<li onclick="cardDate(this);" value="'+changeDate+'" id="'+getNowFormatDate(changeDate)+'">'+getNowFormatDate(changeDate)+'</li>';
		}
	}
	document.getElementById('travelDateNum').innerHTML=dataCardHtml;
}
/* -------------------------日期小卡片 end------------------------------- */


/* -------------------------日期格式转换 start---------------------------- */
//将分钟数量转换为小时和分钟字符串
toHourMinute = function(minutes){
	var m = parseInt(minutes);
	return (Math.floor(m/60) + "h" + (m%60) + "m" );
}
/*获取某一天是周几*/
getWeekByDay = function(i){ 
	var today = new Array("周日","周一","周二","周三","周四","周五","周六"); //创建星期数组
	return today[i];  //返一个星期中的某一天，其中0为星期日 
}
/*格式化日期为年月*/
getNowFormatDate = function (changeDate){
	var seperator1 = "-";
	var seperator2 = " ";
	var year = changeDate.getFullYear();
	var month = changeDate.getMonth() + 1;
	var strDate = changeDate.getDate();
	var weekday = changeDate.getDay();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var outdate = month +seperator1+ strDate +seperator2+ getWeekByDay(weekday);
	return outdate;
}
/*------------------------日期加减天数   默认返程日期是出发日期的15天---------------------*/
function addDate(dd,count){
	var a = new Date(dd)
	a = a.valueOf()
	a = a + count * 24 * 60 * 60 * 1000
	a = new Date(a)
	return a;
}
defaultDate = function(){
	defaultReturnDate = addDate($("#outDatepicker").val(),15);
	years = defaultReturnDate.getFullYear();
	months = defaultReturnDate.getMonth()+1;
	days = defaultReturnDate.getDate();
	if (months >= 1 && months <= 9) {
		months = "0" + months;
	}
	if (days >= 0 && days <= 9) {
		days = "0" + days;
	}
	var returnDateStr = years +'-'+ months +'-'+days;

	return returnDateStr;
}

/* -------------------------日期格式转换 end---------------------------- */
