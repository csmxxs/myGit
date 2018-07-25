<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8" />
		<title>Banner列表</title>
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
		 *{margin: 0;padding: 0;}
		 canvas{width: 100%;border: 1px solid #000000;}
		 .img-list{margin: 10px 5px;}
		 .img-list li{list-style-type: none;position: relative;display: inline-block;width: 300px;height: 170px;margin: 5px 5px 20px 5px;border: 1px solid rgb(100,149,198);background: #fff no-repeat center;background-size: cover;}
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
				
				<@shiro.hasPermission name="/banner/deleteById.shtml">
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
				
				<@shiro.hasPermission name="/banner/deleteById.shtml">
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
				<@shiro.hasPermission name="/banner/deleteById.shtml">
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
			<@shiro.hasPermission name="/banner/deleteById.shtml">
			<#--根据ID数组删除列表-->
			function deleteById(ids){
				var index = layer.confirm("确定删除这"+ ids.length +"个banner列表？",function(){
					var load = layer.load();
					$.post('${basePath}/banner/deleteById.shtml',{ids:ids.join(',')},function(result){
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
			<@shiro.hasPermission name="/banner/update.shtml">
			<#--根据ID列表的上线操作-->
			function onlineByIdAll(ids){
			    var state=1;
			    var ids=""+ids;
				var index = layer.confirm("确定上线这个Banner列表？",function(){
					var load = layer.load();
					$.post('${basePath}/banner/online_or_offline_ById.shtml',{id:ids,state:state},function(result){
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
			<#--根据ID列表下线操作-->
			<@shiro.hasPermission name="/banner/update.shtml">
			function offlineByIdAll(ids){
			    var state=2;
			    var ids=""+ids;
				var index = layer.confirm("确定下线这个banner列表？",function(){
					var load = layer.load();
					$.post('${basePath}/banner/online_or_offline_ById.shtml',{id:ids,state:state},function(result){
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
			<@shiro.hasPermission name="/banner/update.shtml">
			function check1ById(ids){
			    var check=1;
				var index = layer.confirm("确定通过这"+ ids.length +"个banner列表？",function(){
					var load = layer.load();
					$.post('${basePath}/banner/checkById.shtml',{ids:ids.join(','),check:check},function(result){
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
			<@shiro.hasPermission name="/banner/update.shtml">
			function check2ById(ids){
			    var check=2;
				var index = layer.confirm("确定否决这"+ ids.length +"个Banner列表？",function(){
					var load = layer.load();
					$.post('${basePath}/banner/checkById.shtml',{ids:ids.join(','),check:check},function(result){
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
			<@shiro.hasPermission name="/banner/add.shtml">
			<#--添加广告列表-->
			function add(){
				var name = $.trim($('#name').val()),
				    bannerHttp=$trim($('#bannerHttp').val()), 
				    imagesHttp= '';
				    if(imagesData.length>0){
				       for(var i=0;i<imagesData.length;i++){
				               var jsonImages=imagesData[i];
				               if(jsonImages.id=='logoImagesHttp'){
				                  imagesHttp=jsonImages.value;
				               }
				       }
				    }
				if($.trim(name)==''||$.trim(bannerHttp)==''||$.trim(imagesHttp)==''){
					return layer.msg('注意信息不能为空。',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				$.post('${basePath}/banner/add.shtml',{name:name,bannerHttp:bannerHttp,imagesHttp:imagesHttp},function(result){
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
			
			<@shiro.hasPermission name="/banner/update.shtml">
			<#--修改广告列表-->
			function updateById(id,obj){
			     
			     <#--提示-->
			     if(obj==2){
			         var index = layer.confirm("修改完成提交后，将自动通过审核!",function(){
			              layer.close(index);
			           });
			       }
			    <#--先删除多余的孩子-->
			    $('#boxUpdateBannerForm').empty();	
				if($.trim(id)==''|| id == null){
					return layer.msg('请选择要修改的项。',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				$.post('${basePath}/common/getDict.shtml',function(obj){
				$.post('${basePath}/banner/findById.shtml',{id:id},function(result){
					layer.close(load);
					if(result.status != 200){
					 return layer.msg(result.message,so.mdefault),!1;
					}
					$('#update').modal();
					var result=JSON.parse(result.banner);
					//初始化下拉框
					var updateSelect='<div class="form-group"><label for="recipient-name" class="control-label">上线/下线:</label><select id="updatestate"  class="form-control"></select></div>';
                    var html='<div class="form-group"><input type="hidden" value="'+result.imagesHttp+'" id="oldImagesHttp"/><input type="hidden" value="'+result.id+'" id="id"/><label for="recipient-name" class="control-label">名称:</label><input type="text" value="'+result.name+'" class="form-control" name="name" id="updateName"/></div><div class="form-group"><label for="recipient-name" class="control-label">链接地址:</label><input type="text" class="form-control" value="'+result.bannerHttp+'" id="updateBannerHttp" name="bannerHttp"/></div><div class="form-group"><label for="recipient-name" class="control-label">Logo图片:</label><img src="'+result.imagesHttp+'" style="width:300px;height:170px"/></div><div class="form-group"><label for="recipient-name" class="control-label">更新图片:</label><input type="file" class="form-control" id="updateImagesHttp" onclick="submitImages(this)"/><ul class="img-list"></ul></div>';
					$('#boxUpdateBannerForm').html(html+updateSelect);					
					if(obj){
					    var select=document.getElementById('updatestate');
					    var jsonarray=obj.result.SYS_STATE;
					  for(var x in jsonarray){
				         var option = new Option(jsonarray[x],x,true);				      
						 select.options.add(option);
					 }
				  }
				  $("#updatestate option[value="+result.state+"]").attr("selected",true); 
				},'json');
			  })
			}
			<#--修改完成后提交-->
			function update(){
			
			 	var id=$('#id').val(),
			 	    name = $.trim($('#updateName').val()),
				    bannerHttp=$.trim($('#updateBannerHttp').val()), 
				    state=$('#updatestate').val(),
				    imagesHttp='';
				    if(imagesData.length>0){
				       for(var i=0;i<imagesData.length;i++){
				           var jsonImages=imagesData[i];
				           if(jsonImages.id='updateImagesHttp'){
				               imagesHttp=jsonImages.value;
				           }
				       }
				    }else{
				      imagesHttp=$('#oldImagesHttp').val();
				    }
				    
				if($.trim(state)==''||$.trim(name)==''||$.trim(bannerHttp)==''||$.trim(imagesHttp)==''){
					return layer.msg('注意信息不能为空。',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				$.post('${basePath}/banner/update.shtml',{state:state,id:id,name:name,bannerHttp:bannerHttp,imagesHttp:imagesHttp},function(result){
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
		<@_top.top 3/>
		<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
			<div class="row">
				<#--引入左侧菜单-->
				<@_left.operationControl 3/>
				<div class="col-md-10">
					<h2>Bannner列表</h2>
					<hr>
					<form method="post" action="" id="formId" class="form-inline">
						<div clss="well">
					      <div class="form-group">
					        <input type="text" class="form-control" style="width: 300px;" value="${findContent?default('')}" 
					        			name="findContent" id="findContent" placeholder="请输入">
					      </div>
					     <span class=""> <#--pull-right -->
				         	<button type="submit" class="btn btn-primary">查询</button>
				         	<@shiro.hasPermission name="/banner/add.shtml">
				         		<a class="btn btn-success" onclick="$('#add').modal();">新增</a>
				         	</@shiro.hasPermission>
				         	<@shiro.hasPermission name="/banner/deleteById.shtml">
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
							<th>审核</th>
							<th>审核日期</th>
							<th>状态</th>
							<th>链接地址</th>
							<th>图片地址</th>
							<th>上线时间</th>
							<th>创建日期</th>
							<th>操作</th>
						</tr>
						<#if page?exists && page.list?size gt 0 >
							<#list page.list as it>
								<tr>
									<td><input value="${it.id}" check='box' type="checkbox" /></td>
									<td>${it.name?default('#')}</td>
									<td>${it.id?default('#')}</td>
									<td>
									    <@mytags paramId="APPLY_STATE,${it.noPass}">
									       ${dictCvalue}
									    </@mytags>
									</td>
									<td>${(it.checkTime?string('yyyy-MM-dd'))!'#'}</td>
									<td>
									    <@mytags paramId="SYS_STATE,${it.state}">
											${dictCvalue}
									    </@mytags>
									</td>
									<td><a href="${it.bannerHttp?default('#')}">查看</a></td>
									<td><a href="javascript:showPic('${it.imagesHttp}')">查看</a></td>
									<td>${(it.onlineTime?string('yyyy-MM-dd'))!'#'}</td>
									<td>${(it.createTime?string('yyyy-MM-dd'))!'#'}</td>
									<td>
									    <#if it.state !=1 >
									       <@shiro.hasPermission name="/banner/deleteById.shtml">
											      <i class="glyphicon glyphicon-remove"></i>
											      <a href="javascript:deleteById([${it.id}]);">删除</a>&nbsp;&nbsp;&nbsp;
								           </@shiro.hasPermission>
								           <@shiro.hasPermission name="/banner/update.shtml">
								                <a href="javascript:updateById(${it.id},${it.noPass});">|&nbsp;&nbsp;修改</a>
									            <br>
									       </@shiro.hasPermission>
									   </#if>
									   <#if it.noPass == 1>
									        <@shiro.hasPermission name="/banner/update.shtml">
									           <#if it.state == 0 >
												   <button type="button" onclick="onlineByIdAll(${it.id})" class="btn btn-success">上线</button>
									           </#if>
										   </@shiro.hasPermission>
									   
									       <@shiro.hasPermission name="/banner/update.shtml">
									           <#if it.state == 2 >
												   <button type="button" onclick="onlineByIdAll(${it.id})" class="btn btn-success">上线</button>
									           </#if>
										   </@shiro.hasPermission>
										   <@shiro.hasPermission name="/banner/update.shtml">
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
			
			<@shiro.hasPermission name="/banner/add.shtml">
				<#--添加弹框(添加列表用)-->
				<div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="addbannerLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="addbannerLabel">添加广告列表</h4>
				      </div>
				      <div class="modal-body">
				        <form id="boxbannerForm">
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">banner名称:</label>
				            <input type="text" class="form-control" name="name" id="name" placeholder="请输入广告名称"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">链接地址:</label>
				            <input type="url" class="form-control" id="bannerHttp" name="bannerHttp"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">Logo图片地址:</label>
				            <input type="file" class="form-control" id="logoImagesHttp" name="logoImagesHttp" onclick="submitImages(this)" accept="image/*" multiple/>
				            <ul class="img-list"></ul>
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
			
			<@shiro.hasPermission name="/banner/update.shtml">
			<#--添加弹框  (修改用)-->
				<div class="modal fade" id="update" tabindex="-1" role="dialog" aria-labelledby="addbannerLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="addbannerLabel">修改广告列表</h4>
				      </div>
				      <div class="modal-body">
				        <form id="boxUpdateBannerForm">
				           
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
	</body>
    <script  src="${basePath}/js/uploadAndsubmitImages.js"></script>	
</html>