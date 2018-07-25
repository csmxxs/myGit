<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8" />
		<title>任务列表</title>
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
		<link   rel="icon" href="${basePath}/images/favicon.ico" type="image/x-icon" />
		<link   rel="shortcut icon" href="${basePath}/images/favicon.ico" />
		<link href="${basePath}/js/common/bootstrap/3.3.5/css/bootstrap.min.css?${_v}" rel="stylesheet"/>
		<link href="${basePath}/css/common/base.css?${_v}" rel="stylesheet"/>
		
		<style>
		  *{margin: 0;padding: 0;}
		  table th {
		    text-align:center;
		  }
		  table td {
		    text-align:center;
		  }
	     canvas{width: 100%;border: 1px solid #000000;}
	     .img-list{margin: 10px 5px;}
	     .img-list li{list-style-type: none;position: relative;display: inline-block;width: 300px;height: 200px;margin: 5px 5px 20px 5px;border: 1px solid rgb(100,149,198);background: #fff no-repeat center;background-size: cover;}
		</style>
		
		
		<script  src="${basePath}/js/common/jquery/jquery1.8.3.min.js"></script>
		<script  src="${basePath}/js/common/layer/layer.js"></script>
		<script  src="${basePath}/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<script  src="${basePath}/js/shiro.demo.js"></script>
		<!-- 引入弹出窗口JS库 -->
        <script type="text/javascript"
	       src="${basePath}/js/lhgdialog/lhgdialog.min.js?self=true&skin=igreen">
	    </script>
		<script >
		
		   //验证手机号码(包括移动、联通、电信)    
		   var yidong = /^170[356]\d{7}$|^(?:13[4-9]|147|178|15[0-27-9]|178|18[2-478])\d{8}$/,    
		   liantong = /^170[4789]\d{7}$|^(?:13[0-2]|145|15[56]|17[165]|18[56])\d{8}$/,    
		   dianxin = /^170[01]\d{7}$|^(?:133|153|173|177|18[019])\d{8}$/;

			so.init(function(){
				//初始化全选。
				so.checkBoxInit('#checkAll','[check=box]');
				
				<@shiro.hasPermission name="/task/deleteById.shtml">
				//全选
				so.id('deleteAll').on('click',function(){
					var checkeds = $('[check=box]:checked');
					if(!checkeds.length){
						return layer.msg('请选择要删除的选项。',so.mdefault),!0;
					}
					var array = [];
					checkeds.each(function(){
						array.push(this.value);
					});
					return deleteById(array);
				});
				</@shiro.hasPermission>
				
				<@shiro.hasPermission name="/task/update.shtml">
				//全选
				so.id('check1ById').on('click',function(){
					var checkeds = $('[check=box]:checked');
					if(!checkeds.length){
						return layer.msg('请选择要审核的选项。',so.mdefault),!0;
					}
					var array = [];
					checkeds.each(function(){
						array.push(this.value);
					});
					//审核通过
					return check1ById(array);
				});
				</@shiro.hasPermission>
				<@shiro.hasPermission name="/task/update.shtml">
				//全选
				so.id('check2ById').on('click',function(){
					var checkeds = $('[check=box]:checked');
					if(!checkeds.length){
						return layer.msg('请选择要审核的选项。',so.mdefault),!0;
					}
					var array = [];
					checkeds.each(function(){
						array.push(this.value);
					});
					//审核不通过
					return check2ById(array);
				});
				</@shiro.hasPermission>
			});
			

			<#--根据ID数组批量审核状态操作-->
			<@shiro.hasPermission name="/task/update.shtml">
			function check1ById(ids){
			    var check=1;
				var index = layer.confirm("确定通过这"+ ids.length +"个task列表？",function(){
					var load = layer.load();
					$.post('${basePath}/task/checkById.shtml',{ids:ids.join(','),check:check},function(result){
						layer.close(load);
						if(result && result.status != 200){
							return layer.msg(result.message,so.mdefault),!0;
						}else{
							layer.msg(result.message);
							setTimeout(function(){
								$('#formId').submit();
							},1000);
						}
					},'json');
					layer.close(index);
				});
			}
			</@shiro.hasPermission>
			<#--根据ID数组批量审核状态操作-->
			<@shiro.hasPermission name="/task/update.shtml">
			function check2ById(ids){
			    var check=2;
				var index = layer.confirm("确定否决这"+ ids.length +"个Task列表？",function(){
					var load = layer.load();
					$.post('${basePath}/task/checkById.shtml',{ids:ids.join(','),check:check},function(result){
						layer.close(load);
						if(result && result.status != 200){
							return layer.msg(result.message,so.mdefault),!0;
						}else{
							layer.msg(result.message);
							setTimeout(function(){
								$('#formId').submit();
							},1000);
						}
					},'json');
					layer.close(index);
				});
			}
			</@shiro.hasPermission>
			
			
			<@shiro.hasPermission name="/task/deleteById.shtml">
			<#--根据ID数组删除任务列表-->
			function deleteById(ids){
				var index = layer.confirm("确定删除这"+ ids.length +"个任务列表？",function(){
					var load = layer.load();
					$.post('${basePath}/task/deleteById.shtml',{ids:ids.join(',')},function(result){
						layer.close(load);
						if(result && result.status != 200){
							return layer.msg(result.message,so.mdefault),!0;
						}else{
							layer.msg(result.message);
							setTimeout(function(){
								$('#formId').submit();
							},1000);
						}
					},'json');
					layer.close(index);
				});
			}
			</@shiro.hasPermission>
			<@shiro.hasPermission name="/task/add.shtml">
			<#--添加任务列表-->
			function add(){
			
				var name =$.trim($('#name').val()),
				    property=$('#property option:selected').val(),
				    taskType=$('#taskType option:selected').val(),
				    nbStimulate=$.trim($('#nbStimulate').val()),
				    generalize=$('#generalize option:selected').val(),
				    text=$.trim($('#text').val()),
				    tel=$.trim($('#tel').val()),
				    taskHttp=$.trim($('#taskHttp').val());
				    //压缩后得到的图片
				    var logoImages;
				    var taskFulfilImg;
				    for(var i=0;i<imagesData.length;i++){
				        var jsonImages=imagesData[i];
				        if(jsonImages.id=='logoImages'){
				           logoImages=jsonImages.value;
				        }
				        if(jsonImages.id=='taskFulfilImg'){
				           taskFulfilImg=jsonImages.value;
				        }
				    }
				if($.trim(name) == '' || $.trim(property)=='' || $.trim(taskType)==''||$.trim(nbStimulate)==''||$.trim(generalize)==''||$.trim(logoImages)==''||$.trim(text)==''||$.trim(tel)=='' ||$.trim(taskHttp)==''||$.trim(taskFulfilImg)==''){
					       return layer.msg('注意信息不能为空。',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				$.post('${basePath}/task/add.shtml',{name:name,property:property,taskType:taskType,nbStimulate:nbStimulate,generalize:generalize,logoImages:logoImages,text:text,tel:tel,taskHttp:taskHttp,taskFulfilImg:taskFulfilImg},function(result){
					layer.close(load);
					if(result && result.status != 200){
						return layer.msg(result.message,so.mdefault),!1;
					}
					layer.msg('添加成功!');
					setTimeout(function(){
						$('#formId').submit();
					},800);
				},'json');
			}
			</@shiro.hasPermission>
			
			
			<@shiro.hasPermission name="/task/update.shtml">
			<#--修改任务列表-->
			function updateById(id,obj){
			    <#--提示-->
			    if(obj==2){
				    var index = layer.confirm("修改完成提交后，将自动审核通过!",function(){
				       layer.close(index);
				    });
			    }
			    <#--先删除多余的孩子-->
			    $('#boxUpdateTaskForm').empty();	
				if($.trim(id)==''|| id == null){
					return layer.msg('请选择要修改的项。',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				$.post('${basePath}/task/findById.shtml',{id:id},function(result){
					layer.close(load);
					if(result.status != 200){
					 return layer.msg(result.message,so.mdefault),!1;
					}
					$('#update').modal();
					var result=JSON.parse(result.task);
                    var html='<div class="form-group"><input type="hidden" id="oldFulfilImg" value="'+result.taskFulfilImg+'"><input type="hidden" id="oldlogoImages" value="'+result.logoImages+'"><input type="hidden" value="'+result.id+'" id="id"/><label for="recipient-name" class="control-label">名称:</label><input type="text" value="'+result.name+'" class="form-control" name="name" id="updateName"/></div><div class="form-group"><label for="recipient-name" class="control-label">链接地址:</label><input type="text" class="form-control" value="'+result.taskHttp+'" id="updateTaskHttp" name="taskHttp"/></div><div class="form-group"><label for="recipient-name" class="control-label">任务描述:</label><textarea id="updatetext" name="text" maxlength="255" cos="10" style="width:568px;height:150px" value="'+result.text+'"></textarea></div><div class="form-group"><label for="recipient-name" class="control-label">牛币奖励:</label><input type="text" class="form-control" value="'+result.nbStimulate+'" id="updatenbStimulate" name="nbStimulate"/></div><div class="form-group"><label for="recipient-name" class="control-label">任务步骤图:</label><img src="'+result.taskFulfilImg+'" style="width:300px;height:200px"/></div><div class="form-group"><label for="recipient-name" class="control-label">更新任务步骤图:</label><input type="file" id="updatetaskFulfilImg" onclick="submitImages(this)"/><ul class="img-list"></ul></div><div class="form-group"><label for="recipient-name" class="control-label">logo图片:</label><img src="'+result.logoImages+'" style="width:300px;height:200px"/></div><div class="form-group"><label for="recipient-name" class="control-label">更新Logo图:</label><input type="file" id="updatelogoImages" onclick="submitImages(this)"/><ul class="img-list"></ul></div><div class="form-group"><label for="recipient-name" class="control-label">联系电话</label><input type="tel" class="form-control" value="'+result.tel+'" name="tel" id="updatetel" placeholder="请输入" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,"")" onpaste="return false" onafterpaste="this.value=this.value.replace(/\D/g,"")" /></div>';
					 $('#boxUpdateTaskForm').append(html);
					 $('#updatetext').val(result.text);
				},'json');
			}
			<#--修改完成后提交-->
			function update(){
			
			 	var id=$('#id').val(),
			 	    name = $.trim($('#updateName').val()),
			 	    text=$.trim($('#updatetext').val()),
				    taskHttp=$.trim($('#updateTaskHttp').val()), 
				    tel=$.trim($('#updatetel').val()), 
				    nbStimulate=$.trim($('#updatenbStimulate').val());
			     for(var i=0;i<1;i++){
				    if(yidong.test(tel)||liantong.test(tel)||dianxin.test(tel)){
				          break;
				     }else{
				          return layer.msg('电话号码不正确!',so.mdefault),!1;
				   }
				}    
				var logoImages;
				var taskFulfilImg;
				//压缩后得到的图片
				if(imagesData.length<1){
				  //说明不更新图片
				  logoImages=$('#oldlogoImages').val();
				  taskFulfilImg=$('#oldFulfilImg').val();
				}else{
				   //更新图片
				    if(imagesData.length>1){
				      for(var i=0;i<imagesData.length;i++){
				         var jsonImages=imagesData[i];
				         if(jsonImages.id=='updatelogoImages'){
				           logoImages=jsonImages.value;
				        }
				        if(jsonImages.id=='updatetaskFulfilImg'){
				           taskFulfilImg=jsonImages.value;
				        }
				    }   
				  }
				  if(imagesData.length<2){
				     for(var i=0;i<imagesData.length;i++){
				        var jsonImages=imagesData[i];
				        if(jsonImages.id=='updatelogoImages'){
				           logoImages=jsonImages.value;
				        }else{
				           logoImages=$('#oldlogoImages').val();
				        }
				        if(jsonImages.id=='updatetaskFulfilImg'){
				           taskFulfilImg=jsonImages.value;
				        }else{
				           taskFulfilImg=$('#oldFulfilImg').val();
				        }
				     }
				  
				   }
				} 
				if($.trim(text)==''||$.trim(text).length>255||$.trim(taskFulfilImg)==''||$.trim(logoImages)==''||$.trim(tel)==''||$.trim(tel).length>11||$.trim(name)==''||$.trim(taskHttp)==''|| $.trim(nbStimulate) == ''){
				    if($.trim(text).length>255){
				      return layer.msg('任务描述内容太大。',so.mdefault),!1;
				    }
					return layer.msg('提交信息不符合规范。',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				$.post('${basePath}/task/update.shtml',{taskFulfilImg:taskFulfilImg,logoImages:logoImages,text:text,tel:tel,id:id,name:name,taskHttp:taskHttp,nbStimulate:nbStimulate},function(result){
					layer.close(load);
					if(result && result.status != 200){
						return layer.msg(result.message,so.mdefault),!1;
					}
					layer.msg(result.message+'!');
					setTimeout(function(){
					      $('#formId').submit();
					  },1000);
				},'json');
			}
		  </@shiro.hasPermission>
		  
		  
		  <@shiro.hasPermission name="/task/update.shtml">
			<#--根据ID修改列表的上线操作-->
			function onlineByIdAll(ids){
			    var state=1;
			    var ids=""+ids;
				var index = layer.confirm("确定上线这个task列表？",function(){
					var load = layer.load();
					$.post('${basePath}/task/online_or_offline_ById.shtml',{id:ids,state:state},function(result){
						layer.close(load);
						if(result && result.status != 200){
							return layer.msg(result.message,so.mdefault),!0;
						}else{
							layer.msg(result.message);
							setTimeout(function(){
								$('#formId').submit();
							},1000);
						}
					},'json');
					layer.close(index);
				});
			}
			</@shiro.hasPermission>
			<#--根据ID修改列表下线操作-->
			<@shiro.hasPermission name="/task/update.shtml">
			function offlineByIdAll(ids){
			    var state=2;
			    var ids=""+ids;
				var index = layer.confirm("确定下线这个task列表？",function(){
					var load = layer.load();
					$.post('${basePath}/task/online_or_offline_ById.shtml',{id:ids,state:state},function(result){
						layer.close(load);
						if(result && result.status != 200){
							return layer.msg(result.message,so.mdefault),!0;
						}else{
							layer.msg(result.message);
							setTimeout(function(){
								$('#formId').submit();
							},1000);
						}
					},'json');
					layer.close(index);
				});
			}
			</@shiro.hasPermission>
		  
		  
		</script>
	</head>
	<body data-target="#one" data-spy="scroll">
		<#--引入头部-->
		<@_top.top 3/>
		<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
			<div class="row">
				<#--引入左侧菜单-->
				<@_left.operationControl 2/>
				<div class="col-md-10">
					<h2>任务列表</h2>
					<hr>
					<form method="post" action="" id="formId" class="form-inline">
						<div clss="well">
					      <div class="form-group">
					        <input type="text" class="form-control" style="width: 300px;" value="${findContent?default('')}" 
					        			name="findContent" id="findContent" placeholder="请输入关键词">
					      </div>
					     <span class=""> <#--pull-right -->
				         	<button type="submit" class="btn btn-primary">查询</button>
				         	<@shiro.hasPermission name="/task/add.shtml">
				         		<a class="btn btn-success" onclick="$('#add').modal();">添加任务列表</a>
				         	</@shiro.hasPermission>
				         	<@shiro.hasPermission name="/task/deleteById.shtml">
				         		<button type="button" id="deleteAll" class="btn  btn-danger">Delete</button>
				         	</@shiro.hasPermission>
				         	<@shiro.hasPermission name="/banner/update.shtml">
				                <button type="button" id="check1ById" class="btn btn-success">审核-YES</button>
						    </@shiro.hasPermission>
							<@shiro.hasPermission name="/banner/update.shtml">
						        <button type="button" id="check2ById" class="btn  btn-danger">审核-NO</button>
						    </@shiro.hasPermission>
				         </span>    
				        </div>
					<hr>
					<table class="table table-bordered">
						<tr>
							<th><input type="checkbox" id="checkAll"/></th>
							<th>名称</th>
							<th>ID</th>
							<th>牛币奖励</th>
							<th>logo图</th>
							<th>描述</th>
							<th>电话</th>
							<th>所属专区</th>
							<th>推广地区</th>
							<th>类型</th>
							<th>审核</th>
							<th>状态</th>
							<th>地址</th>
							<th>任务步骤</th>
							<th>创建日期</th>
							<th>操作</th>
						</tr>
						<#if page?exists && page.list?size gt 0 >
							<#list page.list as it>
								<tr>
									<td><input value="${it.id}" check='box' type="checkbox" /></td>
									<td>${it.name?default('-')}</td>
									<td>${it.id?default('-')}</td>
									<td>${it.nbStimulate?default('-')}</td>
									<td>
									   <a href="javascript:showPic('${it.logoImages}')">查看</a>
									</td>
									<td>
									  <textarea readonly="readonly" cos="10" style="width:300px;height:100px">
									    ${it.text?default('-')}
									  </textarea>
									</td>
									<td>${it.tel?default('-')}</td>
									<td>
									    <@mytags paramId="prefectureMap,${it.property}">
									       ${dictCvalue}
									    </@mytags>
									</td>
									<td>
									   <@mytags paramId="provinceMap,${it.generalize}">
									      ${dictCvalue}
									   </@mytags>
									</td>
									<td> 
									   <@mytags paramId="TASK_SSSCIMAGES,${it.taskType}">
									      ${dictCvalue}
									   </@mytags>
									</td>
									<td>
									   <@mytags paramId="APPLY_STATE,${it.noPass}">
									      ${dictCvalue}
									   </@mytags>
									</td>
									<td>
									   <@mytags paramId="SYS_STATE,${it.state}">
									      ${dictCvalue}
									   </@mytags>
									</td>
									<td><a href="${it.taskHttp?default('#')}">查看</a></td>
									<td>
									   <a href="javascript:showPic('${it.taskFulfilImg}')">查看</a>
									</td>
									<td>${(it.createTime?string('yyyy-MM-dd'))!'-'}</td>
									<td>
									    <#if it.state !=1 >
									       <@shiro.hasPermission name="/task/deleteById.shtml">
											      <i class="glyphicon glyphicon-remove"></i>
											      <a href="javascript:deleteById([${it.id}]);">删除</a>&nbsp;&nbsp;&nbsp;
								           </@shiro.hasPermission>
								           <@shiro.hasPermission name="/task/update.shtml">
								                <a href="javascript:updateById(${it.id},${it.noPass});">|&nbsp;&nbsp;修改</a>
									            <br>
									       </@shiro.hasPermission>
									   </#if>
									   <#if it.noPass == 1>
									        <@shiro.hasPermission name="/task/update.shtml">
									           <#if it.state == 0 >
												   <button type="button" onclick="onlineByIdAll(${it.id})" class="btn btn-success">上线</button>
									           </#if>
										   </@shiro.hasPermission>
									   
										   <@shiro.hasPermission name="/task/update.shtml">
										       <#if it.state == 1>
 												   <button type="button" onclick="offlineByIdAll(${it.id})" class="btn  btn-danger">下线</button>
										       </#if>
										   </@shiro.hasPermission>
									  </#if>
									</td>
								</tr>
							</#list>
						<#else>
							<tr>
								<td class="text-center danger" colspan="4">没有找到相关信息</td>
							</tr>
						</#if>
					</table>
					<#if page?exists>
						<div class="pagination pull-right">
							${page.pageHtml}
						</div>
					</#if>
					</form>
				</div>
			</div><#--/row-->
			
			<@shiro.hasPermission name="/task/add.shtml">
				<#--添加弹框-->
				<div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="addtaskLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="addtaskLabel">添加任务列表</h4>
				      </div>
				      <div class="modal-body">
				        <form id="boxtaskForm">
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">任务名称:</label>
				            <input type="text" class="form-control" name="name" id="name"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">任务所属:</label>
				            <select class="form-control" id="property" name="property">
				               
				            </select>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">任务类型:</label>
				            <select class="form-control" id="taskType" name="taskType">
				            
				            </select>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">推广地区:</label>
				            <select class="form-control" id="generalize" name="generalize" >
				            
				            </select>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">牛币激励:</label>
				            <input type="number" class="form-control" id="nbStimulate" name="nbStimulate"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">任务logo图片:</label>
				            <input type="file" class="form-control" id="logoImages" onclick="submitImages(this)" name="logoImages" accept="image/*" multiple/>
				             <ul class="img-list"></ul>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">任务地址:</label>
				            <input type="url" class="form-control" id="taskHttp" name="taskHttp"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">联系电话</label>
				            <input type="tel" class="form-control" name="tel" id="tel" placeholder="请输入" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">任务描述</label>
				             <textarea id="text" name="text" maxlength="255" cos="10" style="width:568px;height:200px">
							 </textarea>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">上传步骤图例</label>
				            <input type="file" class="form-control" onclick="submitImages(this)" id="taskFulfilImg" name="taskFulfilImg" accept="image/*" multiple/>
				            <ul class="img-list"></ul>
				          </div>
				        </form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				        <button type="button" onclick="add();" class="btn btn-primary">Save</button>
				      </div>
				    </div>
				  </div>
				</div>
				<#--/添加弹框-->
			</@shiro.hasPermission>
			<@shiro.hasPermission name="/task/update.shtml">
			<#--添加弹框  (修改用)-->
				<div class="modal fade" id="update" tabindex="-1" role="dialog" aria-labelledby="addtaskLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="addtaskLabel">修改任务列表</h4>
				      </div>
				      <div class="modal-body">
				        <form id="boxUpdateTaskForm">
				           
				        </form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				        <button type="button" onclick="update()" class="btn btn-primary">Save</button>
				      </div>
				    </div>
				  </div>
				</div>
			<#--/添加弹框 (修改用)-->
			</@shiro.hasPermission>
		</div>
		 <script  src="${basePath}/js/uploadAndsubmitImages.js?v=1"></script>
	</body>
<script>
	  window.onload=function(){
	     //去拉取后台省份数据
	    if($('#generalize:has(option)').length!=0){
	       return;
	    }
	    
	    //拉取省份信息
	     $.post('${basePath}/common/getProvince.shtml',function(obj){
	        if(obj){
	          var select=document.getElementById('generalize');
	          var jsonarray=obj.result;
				   for(var x in jsonarray){
                      var option = new Option(jsonarray[x],x,true);				      
				      select.options.add(option);
				   }
	          }
	     }); 
	     //拉取字典信息             
	     $.post('${basePath}/common/getDict.shtml',function(obj){
	        if(obj){
	          var select=document.getElementById('taskType');
	          var jsonarray=obj.result.TASK_SSSCIMAGES;
				   for(var x in jsonarray){
                      var option = new Option(jsonarray[x],x,true);				      
				      select.options.add(option);
				   }
	          }
	     }); 
	     
	     	$.post('${basePath}/common/getPrefectureDict.shtml',function(obj){
	        if(obj){
	          var selectTaskProperty=document.getElementById('property');
	          var jso=obj.result;
				   for(var x in jso){
                      var option = new Option(jso[x],x,true);				      
				      selectTaskProperty.options.add(option);
				   }
	          }
	     }); 
	                  
}

		  function showPic(filePath) {//显示图片
		    if (filePath != '') {
			$.dialog({
				 title : '查询图片',
				 width : '500px',
				 height : '500px',
				 content : '<img src="'+ filePath +'" />'
			    });
		      }

	     }
</script>
</html>