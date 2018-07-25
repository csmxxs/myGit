<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8" />
		<title>效果分析</title>
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
		<!-- 引入弹出窗口JS库 -->
        <script type="text/javascript"
	       src="${basePath}/js/lhgdialog/lhgdialog.min.js?self=true&skin=igreen">
	    </script>
		<script>
		    var dialog;
			function openDialog(tittle,url) {//打开弹出窗口
				dialog = $.dialog({
					title : tittle,
					width : '900px',
					height : '500px',
					content : 'url:'+url
				});
			}
		</script>
		
	</head>
	<body data-target="#one" data-spy="scroll">
		<#--引入头部-->
		<@_top.top 4/>
		<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
			<div class="row">
				<#--引入左侧菜单-->
				<@_left.operationControl 6/>
				<div class="col-md-10">
					<h2>效果分析</h2>
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
							<th>任务ID</th>
							<th>名称</th>
							<th>状态</th>
							<th>未完成/完成</th>
							<th>截图数</th>
						</tr>
						<#if page?exists && page.list?size gt 0 >
							<#list page.list as it>
								<tr>
									<td>${it.id?default('-')}</td>
									<td><a href="javascript:openDialog('列表日汇总','${basePath}/taskTesultCount/slist.shtml?id=${it.id}')">${it.name?default('-')}</a></td>
									<td>
									   <@mytags paramId="SYS_STATE,${it.state}">
									      ${dictCvalue}
									   </@mytags>
									</td>
									<td>${it.noComplete}/${it.complete}</td>
									<td>${it.ssscNumber}</td>
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