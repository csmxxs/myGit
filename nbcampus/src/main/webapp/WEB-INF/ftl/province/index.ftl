<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8" />
		<title>省份列表</title>
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
		<script >
			
			<#--添加列表-->
			<@shiro.hasPermission name="/province/add.shtml">
			function add(){
				var provinceName = $('#provinceName').val();
				if($.trim(provinceName)==''){
					return layer.msg('注意信息不能为空。',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				$.post('${basePath}/province/add.shtml',{provinceName:provinceName},function(result){
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
			
			<@shiro.hasPermission name="/province/update.shtml">
			<#--修改省份列表-->
			function updateById(id){
			
			    $('#boxUpdateProvinceForm').empty();
				if($.trim(id)==''|| id == null){
					return layer.msg('请选择要修改的项。',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				var cupdate;
				$.post('${basePath}/province/update.shtml',{id:id,cupdate:true},function(result){
					layer.close(load);
					if(result.status != 200){
					 return layer.msg(result.message,so.mdefault),!1;
					}
					$('#update').modal();
					var pro=result.province;
                    var html='<div class="form-group"><input type="hidden" id="id" name="id" value="'+pro.id+'"/><label for="recipient-name" class="control-label">省份名称:</label><input type="text" class="form-control" name="provinceName" id="updateName" value="'+pro.provinceName+'"/></div>';
					$('#boxUpdateProvinceForm').append(html);					
				},'json');
			}
			</@shiro.hasPermission>	
			<#--修改完成后提交-->
			<@shiro.hasPermission name="/province/update.shtml">
			function update(){
			
			 	var id=$('#id').val(),provinceName = $('#updateName').val();
				if($.trim(provinceName)==''||$.trim(id)==''){
					return layer.msg('注意信息不能为空。',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				var cupdate;
				$.post('${basePath}/province/update.shtml',{id:id,provinceName:provinceName,cupdate:false},function(result){
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
		</script>
	</head>
	<body data-target="#one" data-spy="scroll">
		<#--引入头部-->
		<@_top.top 3/>
		<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
			<div class="row">
				<#--引入左侧菜单-->
				<@_left.myDict 2/>
				<div class="col-md-10">
					<h2>省份列表</h2>
					<hr>
					<form method="post" action="" id="formId" class="form-inline">
						<div clss="well">
					      <div class="form-group">
					        <input type="text" class="form-control" style="width: 300px;" value="${findContent?default('')}" 
					        			name="findContent" id="findContent" placeholder="请输入">
					      </div>
					     <span class=""> <#--pull-right -->
				         	<button type="submit" class="btn btn-primary">查询</button>
				         	<@shiro.hasPermission name="/province/add.shtml">
				         		<a class="btn btn-success" onclick="$('#add').modal();">新增省份</a>
				         	</@shiro.hasPermission>
				         </span>    
				        </div>
					<hr>
					<table class="table table-bordered">
						<tr>
							<th>ID</th>
							<th>名称</th>
							<th>操作</th>
						</tr>
						<#if page?exists && page.list?size gt 0 >
							<#list page.list as it>
								<tr>
									<td>${it.id}</td>
									<td>${it.provinceName}</td>
									<td>
								        <@shiro.hasPermission name="/province/update.shtml">
								            <a href="javascript:updateById(${it.id});">修改</a>
									    </@shiro.hasPermission>
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
			
			<@shiro.hasPermission name="/province/add.shtml">
				<#--添加弹框(添加列表用)-->
				<div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="addprovinceLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="addprovinceLabel">添加省份</h4>
				      </div>
				      <div class="modal-body">
				        <form id="boxprovinceForm">
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">省份名称:</label>
				            <input type="text" class="form-control" name="provinceName" id="provinceName" placeholder="请输入省份名称"/>
				          </div>
				        </form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				        <button type="button" onclick="add()" class="btn btn-primary">Save</button>
				      </div>
				    </div>
				  </div>
				</div>
				<#--添加弹框(添加列表用)-->
			</@shiro.hasPermission>
			<@shiro.hasPermission name="/province/update.shtml">
				<#--添加弹框(修改列表用)-->
				<div class="modal fade" id="update" tabindex="-1" role="dialog" aria-labelledby="updateprovinceLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="updateprovinceLabel">修改省份名称</h4>
				      </div>
				      <div class="modal-body">
				        <form id="boxUpdateProvinceForm">
				        
				        
				        </form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				        <button type="button" onclick="update()" class="btn btn-primary">Save</button>
				      </div>
				    </div>
				  </div>
				</div>
			</@shiro.hasPermission>
			
		</div>
	</body>
</html>