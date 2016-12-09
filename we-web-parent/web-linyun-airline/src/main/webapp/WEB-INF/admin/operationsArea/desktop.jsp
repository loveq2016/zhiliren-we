<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en-US">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>桌面</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
<!-- 图标 -->
<link rel="stylesheet"
	href="${base}/public/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="${base}/public/ionicons/css/ionicons.min.css">

<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base}/public/dist/css/skins/_all-skins.min.css">
<!-- 大日历 -->
<link rel="stylesheet" type="text/css"
	href="${base}/public/plugins/fullcalendar/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${base}/public/plugins/fullcalendar/css/fullcalendar.css">
<link rel="stylesheet" type="text/css"
	href="${base}/public/plugins/fullcalendar/css/fancybox.css">
<!--小日历 css-->
<link rel="stylesheet" href="${base}/public/build/kalendae.css"
	type="text/css" charset="utf-8">

<link rel="stylesheet" type="text/css"
	href="${base}/public/dist/css/desktop.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<!--Header -->
		<header class="main-header">

			<!-- Logo -->
			<a href="index2.html" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
				<span class="logo-mini">航空</span> <!-- logo for regular state and mobile devices -->
				<span class="logo-lg">航空票务系统</span>
			</a>

			<!-- Header Navbar -->
			<nav class="navbar navbar-static-top" role="navigation">
				<!-- Sidebar toggle button-->
				<!-- <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a> -->
				<!-- Navbar Right Menu -->

			</nav>
		</header>
		<!--end Header -->

		<!-- Left 菜单栏-->
		<aside class="main-sidebar">

			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">

				<!-- Sidebar Menu -->
				<ul class="sidebar-menu">
					<li class="header">菜单栏</li>
					<!-- Optionally, you can add icons to the links -->
					<li><a href="javascript:;"><i class="fa fa-mouse-pointer">
					</i><span>桌面</span></a></li>
					
					<!-- <li><a href="#"><i class="fa fa-eye"></i> <span>权限管理</span></a></li>
			        <li class="active"><a href="employeeManage.html"><i class="fa fa-users"></i> <span>员工管理</span></a></li>
			        <li><a href="#"><i class="fa fa-bar-chart"></i> <span>统计</span></a></li>
			        <li><a href="clientManage.html"><i class="fa fa-user-secret"></i><span>客户管理</span></a></li>
			        <li><a href="#"><i class="fa fa-search"></i> <span>查询</span></a></li>
			        <li><a href="#"><i class="fa fa-plane"></i> <span>航空公司模块</span></a></li>
			        <li><a href="#"><i class="fa fa-comment"></i> <span>个人信息</span></a></li>
			        <li><a href="#"><i class="fa fa-money"></i> <span>卖票管理</span></a></li>
			        <li><a href="#"><i class="fa fa-commenting"></i> <span>消息管理</span></a></li> -->
				</ul>
				<!-- /.sidebar-menu -->
			</section>
			<!-- /.sidebar -->
		</aside>
		<!--end  Left 菜单栏-->

		<!--right Content-->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">
				<div class="row">

					<div class="col-md-9">
						<!--任务and大日历-->
						<!--任务-->
						<div class="box-header with-border box-head">
							<i class="fa fa-folder-open-o"></i>
							<h4 class="box-title">任务</h4>
						</div>
						<div class="box-body box-sha">
							<ul id="taskListId" class="taskInfo">
								<!-- <li><a href="">今天
										&nbsp;07：23&nbsp;&nbsp;&nbsp;聚美优品孙先哲向你发送一个预售订单</a></li>
								<li><a href="">昨天
										&nbsp;07：23&nbsp;&nbsp;&nbsp;爱我行&nbsp;&nbsp;&nbsp;王行&nbsp;&nbsp;&nbsp;0494573团需要支付一订</a></li>
								<li><a href="">今天
										&nbsp;07：23&nbsp;&nbsp;&nbsp;重庆爱我行&nbsp;&nbsp;&nbsp;王行&nbsp;&nbsp;&nbsp;8474646团需要结清尾款</a></li>
								<li><a href="">今天
										&nbsp;07：23&nbsp;&nbsp;&nbsp;聚美优品孙先哲向你发送一个预售订单</a></li>
								<li><a href="">今天
										&nbsp;07：23&nbsp;&nbsp;&nbsp;聚美优品孙先哲向你发送一个预售订单</a></li> -->
							</ul>
						</div>
						<!--end 任务-->

						<div class="box box-primary marHeight">
							<!--大日历-->
							<div class="box-body no-padding">
								<!-- 大日历具体展示 -->
								<div id='calendar'></div>
							</div>
						</div>
						<!--end 大日历-->
					</div>
					<!--end 任务and大日历-->

					<div class="col-md-3">
						<!--小日历-->
						<div class="box box-primary" id="box-min">
							<p>
								<input type="checkbox"> 显示提醒
							</p>
						</div>
					</div>
					<!--end 小日历-->

				</div>
			</section>
			<!-- /.content -->
		</div>
		<!--end right Content-->

		<!--footer-->
		<footer class="main-footer">
			<div class="pull-right hidden-xs">
				<b>Version</b> 2.3.7
			</div>
			<strong>Copyright &copy; 2014-2016 <a
				href="http://almsaeedstudio.com">Almsaeed Studio</a>.
			</strong> All rights reserved.
		</footer>
		<!--end footer-->


	</div>

	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<!-- Slimscroll -->
	<script
		src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<!--大日历 js-->
	<%-- <script src='${base}/public/plugins/fullcalendar/js/jquery-ui.css'></script> --%>
	<script
		src='${base}/public/plugins/fullcalendar/js/fullcalendar.min.js'></script>
	<!--这是插件自带的一个jquery弹框，你可以换成layer弹框-->
	<script
		src='${base}/public/plugins/fullcalendar/js/jquery.fancybox-1.3.1.pack.js'></script>
	<script type="text/javascript">
		$(function() {
			/* 大日历 */
			calendarInit();
			/* 任务提醒 */
			taskEventList();
		});
	</script>
	
	<!-- 任务事件提醒 -->
	<script type="text/javascript">
		function taskEventList() {
			//获取当前日期
			var d = new Date();
			if(d.getDate() < 10){
				var dateStr = d.getMonth()+1 +"-0"+ d.getDate();
				var yesterdayStr = d.getMonth()+1 +"-0"+ (d.getDate()-1);
			}else{
				var dateStr = d.getMonth()+1 +"-"+ d.getDate();
				var yesterdayStr = d.getMonth()+1 +"-"+ (d.getDate()-1);
			}
			//获取当前时间
			var timeStr = d.getHours() +":"+ d.getMinutes();
			
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : '${base}/admin/operationsArea/getTaskEvents.html',
				success : function(data){
					var content = "";
					$.each(eval(data),function(index, element){
	                	var datetimeStr = element.generatetime;
	                	var dStr = datetimeStr.substr(5, 5);
	                	var tStr = datetimeStr.substr(11, 5);
	                	if(dStr == dateStr){
	                		dStr="今天";
	                	}
	                	if(dStr == yesterdayStr){
	                		dStr="昨天";
	                	}
	                	
	                	var comName = element.shortname;
	                	var agent = element.username;
	                	var msgC = element.msgcontent;
	                	content += '<li>'+ dStr 
	                				+'&nbsp;&nbsp;'+tStr
	                				+'&nbsp;&nbsp;&nbsp;'
	                				+comName+'&nbsp;记录了 '
	                				+msgC+'</li>';
		            });
		           
					$("#taskListId").html(content);
				}
			});
		}
	</script>
	
	
	<!-- 大日历 -->
	<script type="text/javascript">
	function calendarInit(){
		  $('#calendar').fullCalendar({
			    header: {
			      left: 'prev, today, next',
			      center: 'title',
			      right: 'month,agendaWeek,agendaDay'
			    },
			    events: function(start, end,callback) {
			    	
			    	var fStart = $.fullCalendar.formatDate(start,"yyyy-MM-dd hh:mm:ss"); 
			    	var fEnd = $.fullCalendar.formatDate(end,"yyyy-MM-dd hh:mm:ss"); 
			    
			        $.ajax({
			            url: '/admin/operationsArea/getCustomEvents.html',
			            dataType: 'json',
			            data: {
			                start: fStart,
			                end: fEnd
			            },
			            success: function(data) {
			                var events = [];
			                $.each(eval(data),function(index, element){
			                	events.push(element) ;
			                });
			                callback(events);
			            }
			        });
			    },
			    
			    //點擊事件
			    dayClick: function(date, allDay, jsEvent, view) {
			     	  /* 自定义事件 弹框日期 */
			      	  var selDate =$.fullCalendar.formatDate(date,'yyyy-MM-dd hh:mm:ss');
			          layer.open({
			              type: 2,
			              title:false,
			              shadeClose:false,
			              shade:0.6,
			              maxmin: false, 
			              area: ['400px', '275px'],
			              closeBtn: false,
			              content: '${base}/admin/operationsArea/customEvent.html?selDate='+selDate,
			              end: function () {
			            	  calendarInit();
			              }
			          }); 
			            
			      },
			      eventClick: function(event) {  // 定义了点击日历项的动作，这里将会调用jQueryUi的dialog显示日历项的内容
			    	  //do something
			      }
			  });
	  }
	</script>

	<!--小日历 js-->
	<script src="${base}/public/build/kalendae.standalone.js"
		type="text/javascript" charset="utf-8"></script>
	<!-- Page specific script -->
	<script>

//小日历 js
 new Kalendae({
          attachTo:document.getElementById("box-min"),
          months:3,
          mode:'multiple',
          selected:[Kalendae.moment().subtract({M:1}), Kalendae.moment().add({M:1})]
   });
 $(function(){
    $('#box-min .kalendae').attr('id','minCalen');
 });
//end 小日历 js
</script>
</body>
</html>
