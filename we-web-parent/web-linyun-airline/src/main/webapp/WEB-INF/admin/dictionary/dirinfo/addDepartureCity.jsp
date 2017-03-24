<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%> 
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>添加</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/public/dist/css/dict.css">
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
	<!-- js -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
</head>
<body onresize=hero();>
        <div class="modal-top">
          <form id="addForm" method="post"> 
              <div class="modal-header boderButt">
                  <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
                  <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
                  <h4>添加</h4>
              </div>
                <div class="modal-body" style="height:425px;overflow-y: auto;">
                 <div class="tab-content">
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">字典类型编码：</label>
                            <div class="col-sm-8 padding">
                                <select id="typeCode" name="typeCode" class="form-control input-sm inpImpWid">
                                        <!-- <option value="">--请选择--</option> -->
                                        <c:forEach var="one" items="${obj.dirtype }">
											<option value='${one.typeCode}' ${one.typeCode==obj.dirinfo.typeCode?'selected':''}>
												${one.typeName}
											</option>
										</c:forEach> 
                                </select>
                                <span class="prompt">*</span>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">三字代码：</label>
                            <div class="col-sm-8 padding">
                              <input id="dictCode" name="dictCode" class="form-control input-sm inpImpWid" oninput="this.value=this.value.toUpperCase().replace(/(^\s*)|(\s*$)/g, '')" placeholder="请输入字典代码(例如:PEK)" />
                              <span class="prompt">*</span>
                            </div>
                        </div>
                        <div class="row">
                        	<div class="form-group">
                        		<label class="col-sm-3 text-right padding">国家：</label>
	                            <div class="col-sm-3 padding">
	                              <input id="countryName" name="countryName" class="form-control input-sm inpImportant" placeholder="请输入国家名称(例如:China)" />
	                              <span class="prompt">*</span>
	                            </div>
                        	</div>
                            <div class="form-group form-group1">
	                            <label class="col-sm-2 text-right padding">州：</label>
	                            <div class="col-sm-3 padding">
	                              <input id="stateName" name="stateName" class="form-control input-sm inpImportant" placeholder="请输入州名称" />
	                            </div>
                            </div>
                        </div>
                        <div class="row">
                        	<div class="form-group">
                        		<label class="col-sm-3 text-right padding">中文名称：</label>
	                            <div class="col-sm-3 padding">
	                              <input id="chineseName" name="chineseName" class="form-control input-sm inpImportant" placeholder="请输入中文名称(例如:北京)" />
	                              <span class="prompt">*</span>
	                            </div>
	                        </div>
	                        <div class="form-group form-group1">
	                            <label class="col-sm-2 text-right padding">英文名称：</label>
	                            <div class="col-sm-3 padding">
	                              <input id="englishName" name="englishName" class="form-control input-sm inpImportant" placeholder="请输入英文名称(例如:beiJing)" />
	                              <span class="prompt">*</span>
	                            </div>
	                       	</div>
                        </div>
                        <div class="form-group row">
                        	<div class="form-group">
	                            <label class="col-sm-3 text-right padding">拼音：</label>
	                            <div class="col-sm-3 padding">
	                              <input id="pinYin" name="pinYin" class="form-control input-sm inpImportant" placeholder="请输入拼音(例如:beiJing)" />
	                            </div>
	                        </div>
	                        <div class="form-group form-group1">
	                            <label class="col-sm-2 text-right padding">状态：</label>
	                            <div class="col-sm-3 padding">
	                                  <select id="status" name="status" class="form-control input-sm inpImportant">
	                                    <option value="1" selected="selected">启用</option>
	                                    <option value="2">删除</option>
	                                  </select>
	                            </div>
	                        </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">描述：</label>
                            <div class="col-sm-8 padding ">
                              <textarea name="description" class="form-control inpImpWid textareaHei"></textarea>
                            </div>
                        </div>
                      </div>
                   </div>
                </form>
          </div>
<script type="text/javascript">
//验证
$(document).ready(function(){
	$('#addForm').bootstrapValidator({
		message: '验证不通过!',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	dictCode: {
                validators: {
                    notEmpty: {
                        message: '三字代码不能为空!'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                         url: '${base}/admin/dictionary/dirinfo/checkTypeCodeExist.html',//验证地址
                         message: '三字代码已存在,请重新输入!',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST',//请求方式
                         //自定义提交数据，默认值提交当前input value
                         data: function(validator) {
                            return {
                            	dictCode:$('#dictCode').val()
                            };
                         }
                     },
	                regexp: {
                        regexp: /^[a-zA-Z]+$/,
                        message: '三字代码只能为英文字母!'
                    }
                }
            },
            countryName: {
                validators: {
                    notEmpty: {
                        message: '国家名称不能为空!'
                    },
	                regexp: {
                        regexp: /^[a-zA-Z]+$/,
                        message: '国家名称只能输入英文字母!'
                    }
                }
            },
            chineseName: {
            	validators: {
                    notEmpty: {
                        message: '中文名称不能为空!'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
	                    url: '${base}/admin/dictionary/dirinfo/checkDictNameExist.html',//验证地址
	                         message: '中文名称重复,请重新输入!',//提示消息
	                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                         type: 'POST',//请求方式
	                         //自定义提交数据，默认值提交当前input value
	                         data: function(validator) {
	                            return {
	                            	chineseName:$('input[name="chineseName"]').val()
	                            };
	                         }
	                   },
	                   regexp: {
	                        regexp: /^[\u4e00-\u9fa5]*$/,
	                        message: '中文名称只能输入汉字!'
	                    }
                }
            },
            englishName: {
            	validators: {
                    notEmpty: {
                        message: '英文名称不能为空!'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z]+$/,
                        message: '英文名称只能输入字母!'
                    }
                }
            },
            /* pinYin: {
           		validators: {
                   regexp: {
                   	   regexp: /^[a-zA-Z]+$/,
                       message: '拼音只能输入字母!'
                   }
                }
            }, */
            typeCode: {
            	validators: {
            		notEmpty: {
                        message: '类别名称不能为空!'
                    }
                }
            }
        }
	});
});
//添加
$("#submit").click(function(){
	$('#addForm').bootstrapValidator('validate');
	var bootstrapValidator = $("#addForm").data('bootstrapValidator');
	if(bootstrapValidator.isValid()){
		$.ajax({
           cache: false,
           type: "POST",
           url:'${base}/admin/dictionary/dirinfo/addDepartureCity.html',
           data:$('#addForm').serialize(),// 你的formid
           error: function(request) {
              layer.msg('添加失败!',{time:2000});
           },
            success: function(data) {
				layer.load(1, {
					 shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					window.location.reload(); // 父页面刷新
				    window.parent.successCallback('1');
           }
       });
	}
});
//点击返回
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>
