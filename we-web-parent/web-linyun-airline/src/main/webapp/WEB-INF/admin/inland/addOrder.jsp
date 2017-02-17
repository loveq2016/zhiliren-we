<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>添加订单</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base }/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="${base }/public/ionicons/css/ionicons.min.css">
  <link rel="stylesheet" type="text/css" href="${base }/public/dist/css/inlandCross.css"><!--本页面样式-->
</head>
<body>
	<div class="modal-top">
     <div class="modal-header">
          <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
          <button type="submit" class="btn btn-primary right btn-sm" onclick="saveOrderInfo();">保存</button>
          <select id="orderType" class="form-control input-sm orderSelect right">
            <option value="1">查询</option>
            <option value="2">预定</option>
            <option value="3">出票</option>
            <option value="4">开票</option>
            <option value="5">关闭</option>
          </select>
          <label class="right orderLabel">生成订单</label>
          <input id="generateOrder" class="right orderInput" type="checkbox" checked="checked" readonly="true">
          <h4>添加订单</h4>
     </div>
      <div class="modal-body modal-hei">
      	<form id="customaddform" action="">
          <div class="customerInfo"><!--客户信息-->
               <div class="infoTop">
                 <p>客户信息</p>
               </div>
               <div class="infofooter">
                 <table>
                   <tr>
                     <td><label>客户姓名：</label></td> 
                     <td><select id="linkName" name="linkName" class="form-control input-sm" multiple="multiple" placeholder="请输入客户姓名">
                     	</select>
                     	<input id="customerId" name="customerId" type="hidden">
                     	</td>
                     <td><label style="position: relative;top: 4px;">结算方式：</label></td>
                     <td colspan="3"><pre class="preTxt">不限 信用额度：0  临时额度：0  历史欠款：0  预存款：0</pre></td>
                     <td><input type="button" value="清空" class="btn btn-primary btn-sm clearBtn"><i class="UnderIcon fa fa-chevron-circle-down"></i></td>
                   </tr>
                 </table>

                 <table class="hideTable none">
                   <tr>
                     <td><label>公司简称：</label></td>
                     <td><input id="shortName" name="shortName" type="text" class="form-control input-sm" placeholder="请输入公司简称" readonly="true"></td>
                     <td><label>电话：</label></td>
                     <td><input id="telephone" name="telephone" type="text" class="form-control input-sm" placeholder="请输入电话" readonly="true"></td>
                     <td><label>地址：</label></td>
                     <td colspan="3"><input id="address" name="address" type="text" class="form-control input-sm addressInput" placeholder="请输入地址" readonly="true"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>负责人：</label></td>
                     <td><input id="responsible" name="responsible" type="text" class="form-control input-sm" placeholder="请输入负责人" readonly="true"></td>
                     <td><label>网址：</label></td>
                     <td><input id="siteUrl" name="siteUrl" type="text" class="form-control input-sm" placeholder="请输入网址" readonly="true"></td>
                     <td><label>传真：</label></td>
                     <td><input id="fax" name="fax" type="text" class="form-control input-sm" placeholder="请输入传真" readonly="true"></td>
                     <td><label>出发城市：</label></td>
                     <td><input id="departureCity" name="departureCity" type="text" class="form-control input-sm addressInput" placeholder="请输入出发城市" readonly="true"></td>
                     
                   </tr>
                 </table>

               </div>
          </div><!--end 客户信息-->
          <div class="customerInfo"><!--客户需求-->
               <div class="infoTop">
                 <p>客户需求</p>
               </div>
               <div id="infofooter" class="infofooter">
                <div class="DemandDiv">
                 <span class="titleNum">1</span>
                 <a href="javascript:;" class="btn btn-primary btn-sm addDemand"><b>+</b>&nbsp;&nbsp;需求</a>
                 <table>
                   <tr>
                     <td><label>出发城市：</label></td>
                     <td><select id="leavecity" name="leavecity" class="form-control input-sm select2" multiple="multiple" placeholder="PEK(北京)"></select></td>
                     <td><label>抵达城市：</label></td>
                     <td><select id="arrivecity" name="arrivecity" class="form-control input-sm" multiple="multiple" placeholder="SYD(悉尼)"></select></td>
                     <td><label>出发日期：</label></td>
                     <td><input id="leavedate" name="leavedate" type="text" class="form-control input-sm textWid" placeholder="2017-02-22" onFocus="WdatePicker({minDate:'%y-%M-%d'})"></td>
                     <td><label>人数：</label></td>
                     <td><input id="peoplecount" name="peoplecount" type="text" class="form-control input-sm textWid"></td>
                     <td><label class="labelWid">早中晚：</label></td>
                     <td>
                       <select id="tickettype" name="tickettype" class="form-control input-sm textWid">
                         <option value="1">早</option>
                         <option value="2">中</option>
                         <option value="3">晚</option>
                       </select>
                     </td>
                   </tr>
                 </table>
                 <table class="cloTable">
                   <tr name="airlineinfo">
                     <td></span><label>航空公司：</label></td>
                     <td><select id="aircom" name="aircom" class="form-control input-sm"  multiple="multiple" placeholder=""></select></td>
                     <td><label>航班号：</label></td>
                     <td><select id="ailinenum" name="ailinenum" class="form-control input-sm"  multiple="multiple" placeholder="SYD(悉尼)"></select></td>
                     <td><label>出发时间：</label></td>
                     <td><input id="leavetime" name="leavetime" type="text" class="form-control input-sm textWid" placeholder=""></td>
                     <td><label>抵达时间：</label></td>
                     <td><input id="arrivetime" name="arrivetime" type="text" class="form-control input-sm textWid"></td>
                     <td><label class="labelWid">成本价：</label></td>
                     <td><input id="formprice" name="formprice" type="text" class="form-control input-sm textWid"></td>
                     <td><label class="labelWid">销售价：</label></td>
                     <td><input id="price" name="price" type="text" class="form-control input-sm textWid"></td>
                     <td>
                      <a href="javascript:;" name="addButton" class="glyphicon glyphicon-plus addIcon removAddMake"></a>
                     </td>
                   </tr>
                   <tr>
                     <td></span><label>备注：</label></td>
                     <td colspan="11"><input type="text" class="form-control input-sm noteText" placeholder=" "></td>
                   </tr>
                 </table>
                </div>
                 
               </div>
          </div><!--end 客户需求-->
          </form>
      </div>
	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
   <!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!-- select2 -->
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
	<!-- My97DatePicker -->
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<!-- Validator -->
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<!--layer -->
	<script src="${base}/common/js/layer/layer.js"></script>
	<script src="${base }/admin/order/addorder.js"></script><!-- AdminLTE App -->
  <script type="text/javascript">
  //关闭弹框,关闭当前窗口
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
  	//保存订单
  	function saveOrderInfo(){
  		//var data = [];
  		var customdata = {};
  		var customerId = $('#customerId').val();
  		customdata.customerId = customerId;
  		var generateOrder = $('#generateOrder').val();
  		customdata.generateOrder = $("#generateOrder").is(':checked');
  		var orderType = $('#orderType').val();
  		customdata.orderType = orderType;
		var row = [];
  		$('.DemandDiv').each(function(i){
  			var row1 = {};
  			var leavecity = $(this).find('[name=leavecity]').val();
  			//出发城市
  			if (leavecity) {
  				leavecity = leavecity.join(',');
  			}
  			row1.leavecity = leavecity;
  			//抵达城市
  			var arrivecity = $(this).find('[name=arrivecity]').val();
  			if (arrivecity) {
  				arrivecity = arrivecity.join(',');
  			}
  			row1.arrivecity = arrivecity;
  			row1.leavedate = $(this).find('[name=leavedate]').val();
  			row1.peoplecount = $(this).find('[name=peoplecount]').val();
  			row1.tickettype = $(this).find('[name=tickettype]').val();
  			var airrows = [];
  			$(this).find('[name=airlineinfo]').each(function(i){
  				var airrow = {};
  				var aircom = $(this).find('[name=aircom]').val();
  				if (aircom) {
  					aircom = aircom.join(',');
  	  			}
  				airrow.aircom = aircom;
  				var ailinenum = $(this).find('[name=ailinenum]').val();
  				if (ailinenum) {
  					ailinenum = ailinenum.join(',');
  	  			}
  				airrow.ailinenum = ailinenum;
  				airrow.leavetime = $(this).find('[name=leavetime]').val();
  				airrow.arrivetime = $(this).find('[name=arrivetime]').val();
  				airrow.formprice = $(this).find('[name=formprice]').val();
  				airrow.price = $(this).find('[name=price]').val();
  				airrows.push(airrow);
  			});
  			row1.airinfo = airrows;
  			row.push(row1);
  		});
  		customdata.customdata=row;
  		//data.push(customdata);
  		//alert(JSON.stringify(data));
  		console.log(JSON.stringify(customdata));
  		layer.load(1);
		$.ajax({ 
			type: 'POST', 
			data: {data:JSON.stringify(customdata)}, 
			url: '${base}/admin/inland/addOrderInfo.html',
            success: function (data) { 
            	//alert("添加成功");
            	//location.reload();
            	layer.closeAll('loading');
            	layer.msg("添加成功",{time: 2000, icon:1});
            },
            error: function (xhr) {
            	layer.msg("添加失败","",3000);
            } 
        });
  	}
  </script>
</body>
</html>	