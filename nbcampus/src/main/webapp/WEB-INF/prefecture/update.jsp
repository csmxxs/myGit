<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<%@ taglib prefix="mt" uri="mt-rt"%>
<%--shiro 标签 --%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html>
<html>
<head>
<title>任务专区管理</title>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
	name="viewport" />
<link rel="icon" href="${_cxt}/favicon.ico" type="image/x-icon" />
<link rel="prefecture icon" href="${_cxt}/favicon.ico" />
<link
	href="${_cxt}/js/common/bootstrap/3.3.5/css/bootstrap.min.css?${_v}"
	rel="stylesheet" />
<link href="${_cxt}/css/common/base.css?${_v}" rel="stylesheet" />
<style>
table th {
	text-align: center;
}

table td {
	text-align: center;
}

* {
	margin: 0;
	padding: 0;
}

li {
	list-style-type: none;
}

canvas {
	width: 100%;
	border: 1px solid #000000;
}

.img-list {
	margin: 10px 5px;
}

.img-list li {
	position: relative;
	display: inline-block;
	width: 100px;
	height: 100px;
	margin: 5px 5px 20px 5px;
	border: 1px solid rgb(100, 149, 198);
	background: #fff no-repeat center;
	background-size: cover;
}
</style>
<script src="${_cxt}/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="${_cxt}/js/common/layer/layer.js"></script>
<script
	src="${_cxt}/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${_cxt}/js/shiro.demo.js"></script>
<!-- 引入弹出窗口JS库 -->
<script type="text/javascript"
	src="${_cxt}/js/lhgdialog/lhgdialog.min.js?self=true&skin=igreen">
</script>
<script>

//页面更新初始化
$(function cupdate(){
	  $.ajax({
		  type:"post",
		  url:"${_cxt}/prefecture/update.shtml",
		  data:{"ids":$("input[name=id]").val(),"cupdate":true},
		  dataType:"json",
		  success:function(cjs){
			 var obj=cjs.prefecture;
			 var task=cjs.task;
			 $.each(obj,function(index){
				 
			 $("input[name='name']").val(obj.name); 
			 $("#state option[value="+obj.state+"]").attr("selected",true); 
			 $('#logoImagesHttp1').attr('src',obj.logoImagesHttp); 
			 //保存原图片地址
			 $('#logoImagesUrl').val(obj.logoImagesHttp);	 
			 });
			$.post('${_cxt}/common/getDict.shtml',function(object){//查询字典的值
		    $.post('${_cxt}/common/getProvince.shtml',function(obj){//查询省份的值
			$.each(task,function(){
				 //开始打印
				//每次查询数据回来之前先删除旧的数据
				$("#prefectureTaskTable tr[class^=tr]").remove();
				var htr = $("#prefectureTaskTable tr:hidden");//得到table里的所有子孙
				for (var i = 0; i <task.length; i++) {
					var ntr = htr.clone().show();//得到一个新行
					ntr.addClass("tr" + (i % 2));//设置行样式(只是为了删除用,避免重复添加)
					var ntd = ntr.find("td");//得到所有后代
					var o = task[i];
					//开始打印到td
					ntd.eq(0).html("<input value=\""+o.id+"\" check=\"taskbox\" name=\"taskbox\" type=\"checkbox\">");
					ntd.eq(1).html(o.name);
					ntd.eq(2).html(o.id);
					ntd.eq(3).html(o.nbStimulate);
					    //填写中文省份
				        if(obj){
				          var jsonarray=obj.result;
							   for(var x in jsonarray){
								  if(x==o.generalize){
									  ntd.eq(4).html(jsonarray[x]); 
									  break ;
								  }
							   }
				          }
					    //填写任务状态
				        if(object){
				          var dictArray=object.result.SYS_STATE;
							   for(var x in dictArray){
								  if(x==o.state){
									  ntd.eq(5).html(dictArray[x]); 
									  break ;
								  }
							   }
				          }
					ntd.eq(6).html("<a onclick='deleteTr(this);' style=\"cursor:pointer\">删除</a>");
					//追加到容器末尾
					$("#prefectureTaskTable").append(ntr);
				}
			});
		  }); 
		});
		  },
		 error:function(){
			 alert("初始化更新失败!");
		 }
	  });
});

//子窗口回传任务列表ID
function closeDialog(taskIds) {
	var index = layer.confirm("确定添加这"+ taskIds.length +"个任务列表？",function(){
			var load = layer.load();
			$.post('${_cxt}/prefecture/getTask.shtml',{taskIds:taskIds.join(',')},function(result){
				layer.close(load);
				if(result && result.status != 200){
					return layer.msg(result.message,so.mdefault),!0;
				}
			    //开始打印
				//每次查询数据回来之前先删除旧的数据
				//$("#prefectureTaskTable tr[class^=tr]").remove();
				var htr = $("#prefectureTaskTable tr:hidden");//得到table里的所有子孙
				 $.post('${_cxt}/common/getDict.shtml',function(object){//查询字典的值
				 $.post('${_cxt}/common/getProvince.shtml',function(obj){//查询省份
				for (var i = 0; i < result.task.length; i++) {
					var ntr = htr.clone().show();//得到一个新行
					ntr.addClass("tr" + (i % 2));//设置行样式(只是为了删除用,避免重复添加)
					var ntd = ntr.find("td");//得到所有后代
					var o = result.task[i];
					//开始打印到td
					ntd.eq(0).html("<input value=\""+o.id+"\" check=\"taskbox\" name=\"taskbox\" type=\"checkbox\">");
					ntd.eq(1).html(o.name);
					ntd.eq(2).html(o.id);
					ntd.eq(3).html(o.nbStimulate);
				    //填写中文省份
			        if(obj){
			          var jsonarray=obj.result;
						   for(var x in jsonarray){
							  if(x==o.generalize){
								  ntd.eq(4).html(jsonarray[x]); 
								  break ;
							  }
						   }
			          }
				    //填写任务状态
			        if(object){
			          var dictArray=object.result.SYS_STATE;
						   for(var x in dictArray){
							  if(x==o.state){
								  ntd.eq(5).html(dictArray[x]); 
								  break ;
							  }
						   }
			          }
					ntd.eq(6).html("<a onclick='deleteTr(this);' style=\"cursor:pointer\">删除</a>");
					//追加到容器末尾
					$("#prefectureTaskTable").append(ntr);

				}	
			},'json');
			layer.close(index);
		 });
	});
	});		
	//关闭窗口
	dialog.close();
}
//开始更新任务专区
function addTask2prefecture(){
	
    var name = $('#name').val(),
		 state=$('#state').val(),
		 id=$('#id').val(),
		 logoImagesHttp='';
    //压缩后得到的图片
	    if(imagesData.length>0){
	        for(var i=0;i<imagesData.length;i++){
	            var jsonImages=imagesData[i];
	            if(jsonImages.id=='logoImagesHttp'){
	                logoImagesHttp=jsonImages.value;
	            }
	         }
	     }else{
	         //取出老图片提交
	         logoImagesHttp = $('#logoImagesUrl').val();	
	     } 
	     var checkobj = $("[name = taskbox]:checkbox").attr("checked",true);
	     var ids = [];
        checkobj.each(function(){
             ids.push(this.value);
        });
		if($.trim(id) == ''||$.trim(name) == ''||$.trim(state)==''|| $.trim(logoImagesHttp)==''){
			return layer.msg('信息不符合规范!。',so.mdefault),!1;
		}
    var load = layer.load();
    if(ids.length < 1){
    	ids[0]=0;
    }
    
	  $.post('${_cxt}/prefecture/update.shtml',{ids:ids.join(','),cupdate:false,id:id,name:name,state:state,logoImagesHttp:logoImagesHttp},function(result){
			layer.close(load);
			if(result && result.status != 200){
			return layer.msg(result.message,so.mdefault),!0;
		}
		layer.msg(result.message+'!');
			setTimeout(function(){
		        $("#formId",parent.document).submit();
		        //关闭窗口
     	        parent.closeUpdate();
			},1200);
	},'json');
}



//add弹出窗
var dialog;
function openDialog(title,content) {//打开任务列表弹出子窗口
	dialog = $.dialog({
		title : title,
		width : '800px',
		height : '460px',
		content : 'url:'+content
	});
}

function deleteTr(obj){
 $(obj.parentNode.parentNode).remove();
}
function myclose(){
	parent.closeUpdate();
}
</script>

</head>
<body data-target="#one" data-spy="scroll">
	<!-- 添加弹框 -->
	<div>
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="addprefectureLabel">任务专区修改</h4>
				</div>
				<div class="modal-body">
					<form id="boxprefectureForm">
						<div class="form-group">
						<label for="recipient-name" class="control-label">专区ID:</label>
						<input type="text" class="form-control" id="id" name="id" value="${param.id}" readonly="readonly" />
						</div>
						<div class="form-group">
							<label for="recipient-name" class="control-label">专区名称:</label>
							<input type="text" class="form-control" name="name" id="name" />
						</div>
						<div class="form-group">
							<label for="recipient-name" class="control-label">上线/下线:</label>
							<div>
								 <mt:select cssClass="form-control" name="state" data="${SYS_STATE}" id="state" />
							</div>
						</div>
						<label for="recipient-name" class="control-label">logo图片地址:</label>
						<div>
						  <input value="" type="hidden" id="logoImagesUrl"/>
						  <img src="" id="logoImagesHttp1" style="width:100px;height:100px" />
						</div>
						<br>
						<div class="form-group">
							<label for="recipient-name" class="control-label">更新Logo图片:</label>
							<input type="file" class="form-control" id="logoImagesHttp"
								name="logoImagesHttp" onclick="submitImages(this)" accept="image/*" multiple />
							<ul class="img-list">

							</ul>
						</div>
						<a class="btn btn-success" href="javascript:openDialog('添加任务列表','${_cxt}/task/updatePrefectureSlist.shtml?id=${param.id}');">添加任务列表</a>
						<div class="form-group">
						    <!--  原先拥有的任务列表 -->
							<table class="table table-bordered" id="prefectureTaskTable">
								<tr>
									<th>选定</th>
									<th>任务名称</th>
									<th>任务ID</th>
									<th>牛币奖励</th>
									<th>推广地区</th>
									<th>任务状态</th>
									<th>操作</th>
								</tr>
								<tr style="display: none;">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" onclick="myclose()">Close</button>
					<button type="button" onclick="addTask2prefecture();"
						class="btn btn-primary">Save</button>
				</div>
			</div>
		</div>
	</div>
<script src="${_cxt}/js/uploadAndsubmitImages.js"></script>
</body>
</html>