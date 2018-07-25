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
		  table th{
		    text-align:center;
		  }
		  table td{
		  text-align:center
		  }
		
		</style>
		<script  src="${basePath}/js/common/jquery/jquery1.8.3.min.js"></script>
		<script  src="${basePath}/js/common/layer/layer.js"></script>
		<script  src="${basePath}/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<script  src="${basePath}/js/shiro.demo.js"></script>
		<script >
			so.init(function(){
				//初始化全选。
				so.checkBoxInit('#checkAll','[check=box]');
				
				<@shiro.hasPermission name="/prefecture/add.shtml">
				//全选
				so.id('addAll').on('click',function(){
					var checkeds = $('[check=box]:checked');
					if(!checkeds.length){
						return layer.msg('请选择要添加的选项。',so.mdefault),!0;
					}
					var array = [];
					checkeds.each(function(){
						array.push(this.value);
					});
					//parent代表父窗口对象
			        parent.closeDialog(array);
				});
				</@shiro.hasPermission>			

			});
			
	 </script>
	 
	</head>
	<body data-target="#one" data-spy="scroll">
		<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
			<div class="row">
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
				         </span>    
				        </div>
					<hr>
					<table class="table table-bordered">
						<tr>
							<th><input type="checkbox" id="checkAll"/></th>
							<th>任务名称</th>
							<th>任务ID</th>
							<th>牛币奖励</th>
							<th>所属专区</th>
							<th>任务类型</th>
							<th>推广地区</th>
							<th>审核</th>
							<th>状态</th>
						</tr>
						<#if page?exists && page.list?size gt 0 >
							<#list page.list as it>
								<tr>
									<td><input value="${it.id}" check='box' type="checkbox" /></td>
									<td>${it.name?default('-')}</td>
									<td>${it.id?default('-')}</td>
									<td>${it.nbStimulate?default('-')}</td>
									<td>
									    <@mytags paramId="prefectureMap,${it.property}">
									      ${dictCvalue}
									    </@mytags>
									</td>
									<td>
									   <@mytags paramId="TASK_SSSCIMAGES,${it.taskType}">
									      ${dictCvalue}
									   </@mytags>
									</td>
									<td>
									   <@mytags paramId="provinceMap,${it.generalize}">
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
					<span style="width:155px;display:block;margin:auto">
				         	<@shiro.hasPermission name="/prefecture/add.shtml">
				         		<button type="button" id="addAll" class="btn btn-success">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				         	</@shiro.hasPermission>
				         	<@shiro.hasPermission name="/prefecture/add.shtml">
				         		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" id="colse" class="btn  btn-danger" onclick="parent.dialog.close();">取消</button>
				         	</@shiro.hasPermission>
				  </span>   
			    </form>
		  </div>
		</div>
	</div>		
</body>
</html>