<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8" />
		<title>牛币派发列表</title>
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
		<link   rel="icon" href="${basePath}/images/favicon.ico" type="image/x-icon" />
		<link   rel="shortcut icon" href="${basePath}/images/favicon.ico" />
		<link href="${basePath}/js/common/bootstrap/3.3.5/css/bootstrap.min.css?${_v}" rel="stylesheet"/>
		<link href="${basePath}/css/common/base.css?${_v}" rel="stylesheet"/>
		<style>
		 table{
		  text-align:center;
		 }
		 table th {
		   text-align:center;
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
		<script >
			so.init(function(){
				//初始化全选。
				so.checkBoxInit('#checkAll','[check=box]');
				<@shiro.hasPermission name="/grantCoin/update.shtml">
				//全选
				so.id('grantCoinById').on('click',function(){
					var checkeds = $('[check=box]:checked');
					if(!checkeds.length){
						return layer.msg('请选择要派发的选项。',so.mdefault),!0;
					}
					var array = [];
					checkeds.each(function(){
						array.push(this.value);
					});
					//审核通过
					return checkById(array);
				});
				</@shiro.hasPermission>
			});
			<#--根据ID数组批量派发状态操作-->
			<@shiro.hasPermission name="/grantCoin/update.shtml">
			function checkById(ids){
				var index = layer.confirm("确定派发这"+ ids.length +"个任务列表？",function(){
					var load = layer.load();
					$.post('${basePath}/grantCoin/update.shtml',{ids:ids.join(',')},function(result){
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
</head>
	<body data-target="#one" data-spy="scroll">
		<#--引入头部-->
		<@_top.top 6/>
		<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
			<div class="row">
				<#--引入左侧菜单-->
				<@_left.grantCoin 1/>
				<div class="col-md-10">
					<h2>牛币派发列表</h2>
					<hr>
					<form method="post" action="" id="formId" class="form-inline">
						<div clss="well">
					      <div class="form-group">
					        <input type="text" class="form-control" style="width: 300px;" value="${findContent?default('')}" 
					        			name="findContent" id="findContent" placeholder="请输入">
					      </div>
					     <span class=""> <#--pull-right -->
				         	<button type="submit" class="btn btn-primary">查询</button>
				            <@shiro.hasPermission name="/grantCoin/update.shtml">
				                <button type="button" id="grantCoinById" class="btn btn-success">开始派发</button>
						    </@shiro.hasPermission>
				         </span>    
				        </div>
					<hr>
					<table class="table table-bordered">
						<tr>
							<th><input type="checkbox" id="checkAll"/></th>
							<th>用户电话</th>
							<th>截图查看</th>
							<th>任务ID</th>
							<th>完成状态</th>
							<th>截图状态</th>
							<th>派发状态</th>
							<th>请求流水号</th>
							<th>订单号</th>
							<th>剩余赠送次数</th>
							<th>操作</th>
						</tr>
						<#if page?exists && page.list?size gt 0 >
							<#list page.list as it>
								<tr>
									<td>
									   <#if it.commonState ==1>
									      <input value="${it.id}" check='box' type="checkbox" />
									     <#else>
									      *
									   </#if>
									</td>
									<td>${it.studentTel?default('#')}</td>
									<td><a href="javascript:showPic(${it.taskSsscImagesUrl})">查看</a></td>
									<td>${it.commonTaskId?default('#')}</td>
									<td>
									    <@mytags paramId="TASK_COMPLETE,${it.commonTaskState}">
									       ${dictCvalue}
									    </@mytags>
									</td>
									<td>
									    <@mytags paramId="TASK_SSSC_IMAGES,${it.ssscImagesBoolean}">
											${dictCvalue}
									    </@mytags>
									</td>
									<td> 
									    <@mytags paramId="TASK_GRANT_COIN,${it.commonState}">
											${dictCvalue}
									    </@mytags>
									</td>
									<td>${it.requestNo?default('#')}</td>
									<td>${it.orderNo?default('#')}</td>
									<td>${it.availableNum?default('#')}</td>
									<td>
									    <#if it.commonState ==1 >
									       <@shiro.hasPermission name="/grantCoin/update.shtml">
											      <i class="glyphicon glyphicon-remove"></i>
											      <a id="grantCoinById" href="javascript:update([${it.id}]);">派发牛币</a>
								           </@shiro.hasPermission>
									    <#else>
									    无操作
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
			</div>
		</div>
	</body>
</html>