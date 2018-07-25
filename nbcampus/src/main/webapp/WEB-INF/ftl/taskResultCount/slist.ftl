<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8" />
		<title></title>
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
		  
		</script>
	</head>
	<body data-target="#one" data-spy="scroll">
		<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
			<div class="row">
				<div class="col-md-10">
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
				        <#--隐藏的任务专区ID -->
						<input type="hidden" value="${id}" name="id" id="id"/>
					<hr>
					<table class="table table-bordered">
						<tr>
							<th>名称</th>
							<th>任务ID</th>
							<th>日期</th>
							<th>所属</th>
							<th>牛币奖励</th>
							<th>类型</th>
							<th>推广地区</th>
							<th>审核</th>
							<th>状态</th>
							<th>日统计(点击/完成)</th>
						</tr>
						<#if page?exists && page.list?size gt 0 >
							<#list page.list as it>
								<tr>
									<td>${it.name?default('-')}</td>
									<td>${it.taskId?default('-')}</td>
									<td>${(it.dayCountTime?string('yyyy-MM-dd'))!'-'}</td>
									<td>${it.property?default('-')}</td>
									<td>${it.nbStimulate?default('-')}</td>
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
									<td>${it.dayclickNumber}/${it.daycompleteNumber}</td>
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
		</div>
			
	</body>
</html>