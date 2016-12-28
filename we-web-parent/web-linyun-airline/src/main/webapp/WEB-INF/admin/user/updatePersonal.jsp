<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/public/dist/css/personalInfo.css"><!--本页的styleFlie-->
</head>
<body>
	<div class="modal-top">
     <div class="modal-header">
          <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();" data-dismiss="modal">取消</button>
          <button id="submit" class="btn btn-primary right btn-sm" data-dismiss="modal">保存</button>
          <h5>编辑个人信息</h5>
     </div>
      <div class="modal-body" style="height:253px;overflow-y:auto;">
          <div class="form-group">
          	<form id="editPersonalForm" method="post">
          		<input id="id" name="id" type="hidden" value="${obj.personalInfo[0].id}"/>
	          	<table class="editInfo">
	          		<tr>
	          			<td><label>用户名称：</label></td>
	          			<td><input id="userName" name="userName" type="text" value="${obj.personalInfo[0].userName}" class="form-control input-sm" readonly="readonly"></td>
	          			<td><label>用户名/手机号码：</label></td>
	          			<td><input id="telephone" name="telephone" type="text" value="${obj.personalInfo[0].telephone }" class="form-control input-sm" readonly="readonly"></td>
	          		</tr>
	          		<tr>
	          			<td><label>座机电话：</label></td>
	          			<td><input id="landline" name="landline" type="text" value="${obj.personalInfo[0].landline }" class="form-control input-sm"></td>
	          			<td><label>联系QQ：</label></td>
	          			<td><input id="qq" name="qq" type="text" value="${obj.personalInfo[0].qq }" class="form-control input-sm"></td>
	          		</tr>
	          		<tr>
	          			<td><label>电子邮箱：</label></td>
	          			<td><input id="email" name="email" type="text" value="${obj.personalInfo[0].email }" class="form-control input-sm"></td>
	          			<td><label>所属部门：</label></td>
	          			<td><input id="deptName" name="deptName" type="text" value="${obj.personalInfo[0].deptName }" class="form-control input-sm" readonly="readonly"></td>
	          		</tr>
	          		<tr>
	          			<td><label>用户职位：</label></td>
	          			<td><input id="jobName" name="jobName" type="text" value="${obj.personalInfo[0].jobName }" class="form-control input-sm" readonly="readonly"></td>
	          			<td><label>负责区域：</label></td>
	          			<td><input id="areaName" name="areaName" type="text" value="${obj.personalInfo[0].areaName }" class="form-control input-sm" readonly="readonly"></td>
	          		</tr>
	          	</table>
              </form>
          </div>
      </div>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">
//修改成功提示
$("#submit").click(function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : '${base}/admin/user/updatePersonal.html',
			data : $('#editPersonalForm').serialize(),
			error : function(request) {
				layer.msg('修改失败!');
			},
			success : function(data) {
				layer.load(1, {
					 shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
              	layer.msg('修改成功!',{time: 5000, icon:6});
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
				location.reload();
			}
		});
});
//点击取消
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>