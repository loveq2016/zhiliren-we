//计划制作表格初始化
var datatable2;
function initDatatable2() {
    datatable2 = $('#datatable2').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "language": {
            "url": BASE_PATH + "/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": BASE_PATH + "/admin/customneeds/listEditPlanData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "fnDrawCallback" : function(){
        	var api = this.api();
        	var startIndex= api.context[0]._iDisplayStart;
   	       　　  api.column(1).nodes().each(function(cell, i) {
   	       　　　　cell.innerHTML = startIndex + i + 1; 
   	       　　});
      	},
        "columns": [
                	{"data": "id", "bSortable": false,
                    	"render": function (data, type, row, meta) {
                            return '<input type="checkbox"  class="checkchild"  value="' + row.id + '" />';
                        }
                    },
                    {"data": "xuhao", "bSortable": false},
                    {"data": "dingdanhao", "bSortable": false},
                    {"data": "airlinename", "bSortable": false},
                    {"data": "leavesdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var leavesdate = new Date(row.leavesdate);
                    		var MM = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][leavesdate.getMonth()];
                    		return leavesdate.getDate() + "/" + MM;
                    	}
                    },
                    {"data": "leavescity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		return row.leavescity;
                    	}
                    },
                    {"data": "backsdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var backsdate = new Date(row.backsdate);
                    		var MM = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][backsdate.getMonth()];
                    		return backsdate.getDate() + "/" + MM;
                    	}
                    },
                    {"data": "backscity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		return row.backscity;
                    	}	
                    },
                    {"data": "peoplecount", "bSortable": false},
                    {"data": "dayscount", "bSortable": false},
                    {"data": "travelname", "bSortable": false},
                    {"data": "unioncity", "bSortable": false}
            ],
        columnDefs: [{
    		targets: 0,
            render: function(data, type, row, meta) {
                return null
            }
    	},{
    		targets: 1,
            render: function(data, type, row, meta) {
                return null
            }
    	},{
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
            targets: 12,
            render: function(data, type, row, meta) {
            	var s = '<a style="cursor:pointer;" onclick="editplan('+row.id+');">编辑</a>';
            	if(row.isclose == 0){
            		s += '&nbsp;&nbsp;&nbsp;<a style="cursor:pointer;" onclick="closeEditPlan('+row.id+');">关闭</a>';
            	}else{
            		s += '&nbsp;&nbsp;&nbsp;<a style="cursor:pointer;" onclick="enableEditPlan('+row.id+');">启用</a>';
            	}
                return s
            }
    	}]
    });
}
//控制复选框
$(".checkall").click(function () {
    var check = $(this).prop("checked");
    $(".checkchild").prop("checked", check);
});
//加载编辑计划列表数据
initDatatable2();
//获取检索条件数据
function getEditPlanParam(){
	var teamtype1 = $('#teamtype1').val();
	var idordernum = $('#idordernum').val();
	var travelname1 = $('#travelname1').val();
	var peoplecount1 = $('#peoplecount1').val();
	var dayscount1 = $('#dayscount1').val();
	var leaveairline1 = $('#leaveairline1').val();
	var backairline1 = $('#backairline1').val();
	var unioncity1 = $('#unioncity1').val();
	var startdate1 = $('#startdate1').val();
	var enddate1 = $('#enddate1').val();
	var leavescity1 = $('#leavescity1').val();
	var backscity1 = $('#backscity1').val();
	var param = {
			teamtype:teamtype1,
			idordernum:idordernum,
			travelname1:travelname1,
			peoplecount1:peoplecount1,
			dayscount1:dayscount1,
			leaveairline1:leaveairline1,
			backairline1:backairline1,
			unioncity1:unioncity1,
			startdate1:startdate1,
			enddate1:enddate1,
			leavescity1:leavescity1,
			backscity1:backscity1
	};
	return param;
}
//检索数据
$("#editPlanSearch").on('click', function () {
	document.getElementById('exportEditPlanId').href= BASE_PATH + "/admin/customneeds/exportEditPlanExcel.html?" + $("#editPlanForm").serialize();
	var param = getEditPlanParam();
    datatable2.settings()[0].ajax.data = param;
    datatable2.ajax.reload();
});
//检索
function editPlanListSearch(){
	$("#editPlanSearch").click();
}
//按回车键后检索
function onEnterSearch(){
	 var e = window.event || arguments.callee.caller.arguments[0];
     if(e && e.keyCode == 13){
    	 editPlanListSearch();
     }
}
//恢复默认
$('#resetPlanBtn').on('click',function(){
	$("#editPlanForm")[0].reset();
	$("#editPlanSearch").click();
});
//打开编辑计划页面
function editplan(id){
	layer.open({
  	    type: 2,
  	    title: false,
  	  	closeBtn:false,
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: false,
  	    area: ['900px', '500px'],
  	    content: BASE_PATH + '/admin/customneeds/editplanpage.html?id='+id,
  	    end:function(){
  	    	datatable2.ajax.reload();
  	    }
  	});
}
//关闭编辑计划
function closeEditPlan(id){
	layer.confirm('确定要关闭该计划吗?', {icon: 3, title:'提示'}, function(){
		$.ajax({ 
			type: 'POST', 
			data: {id:id}, 
			url: BASE_PATH + '/admin/customneeds/closeEditPlan.html',
            success: function (data) { 
            	layer.msg("关闭成功",{time: 2000, icon:1});
            	datatable2.ajax.reload();
            },
            error: function (xhr) {
            	layer.msg("关闭失败",{time: 2000, icon:1});
            } 
        });
	});
}
//启用计划
function enableEditPlan(id){
	layer.confirm('确定要启用该计划吗?', {icon: 3, title:'提示'}, function(){
		$.ajax({ 
			type: 'POST', 
			data: {id:id}, 
			url: BASE_PATH + '/admin/customneeds/enableEditPlan.html',
            success: function (data) { 
            	layer.msg("启用成功",{time: 2000, icon:1});
            	datatable2.ajax.reload();
            },
            error: function (xhr) {
            	layer.msg("启用失败",{time: 2000, icon:1});
            } 
        });
	});
}
//批量关闭计划
function batchClosePlan(){
	var length = $(".checkchild:checked").length;
	if(length < 1){
		layer.msg("请至少选中一条记录",{time: 2000, icon:1});
	}else{
		layer.confirm('确定要关闭该计划吗?', {icon: 3, title:'提示'}, function(){
			var ids = [];
			$(".checkchild:checked").each(function(){
				ids.push($(this).val());
			});
			ids = ids.join(',');
			$.ajax({
				type: 'POST', 
				data: {ids:ids}, 
				url: BASE_PATH + '/admin/customneeds/betchClosePlan.html',
				success: function (data) { 
					layer.msg("关闭成功",{time: 2000, icon:1});
					datatable2.ajax.reload();
					$('.checkall').attr('checked',false);
				},
				error: function (xhr) {
					layer.msg("关闭失败",{time: 2000, icon:1});
				} 
			});
		});
	}
}
//生成订单
function generateOrderNum(){
	var length = $(".checkchild:checked").length;
	if(length < 1){
		layer.msg("请至少选中一条记录",{time: 2000, icon:1});
	}else{
		layer.confirm('确定要批量生成订单吗?', {icon: 3, title:'提示'}, function(){
			var ids = [];
			$(".checkchild:checked").each(function(){
				ids.push($(this).val());
			});
			ids = ids.join(',');
			$.ajax({
				type: 'POST', 
				data: {planids:ids}, 
				url: BASE_PATH + '/admin/customneeds/generateOrderNum.html',
				success: function (data) { 
					layer.msg("生成成功",{time: 2000, icon:1});
					datatable2.ajax.reload();
					$('.checkall').attr('checked',false);
				},
				error: function (xhr) {
					layer.msg("生成失败",{time: 2000, icon:1});
				} 
			});
		});
	}
}