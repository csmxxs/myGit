<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8" />
		<title>快捷功能入口列表</title>
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
		  .img-list li{list-style-type: none;position: relative;display: inline-block;width: 50px;height: 50px;margin: 5px 5px 20px 5px;border: 1px solid rgb(100,149,198);background: #fff no-repeat center;background-size: cover;}
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
			
			so.init(function(){
				//初始化全选。
				so.checkBoxInit('#checkAll','[check=box]');
				
				<@shiro.hasPermission name="/shortcut/deleteById.shtml">
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
			});
			<#--根据ID数组删除功能入口-->
			<@shiro.hasPermission name="/shortcut/deleteById.shtml">
			function deleteById(ids){
				var index = layer.confirm("确定这"+ ids.length +"个信息？",function(){
					var load = layer.load();
					$.post('${basePath}/shortcut/deleteById.shtml',{ids:ids.join(',')},function(result){
						layer.close(load);
						if(result && result.status != 200){
							return layer.msg(result.message,so.mdefault),!0;
						}
						layer.msg(result.message);
						setTimeout(function(){
								$('#formId').submit();
							},1500);
					},'json');
					layer.close(index);
				});
			}
			</@shiro.hasPermission>
			<#--修改功能入口信息-->
			<@shiro.hasPermission name="/shortcut/update.shtml">
			function update(){
			 	var id=$('#id').val(),
			 	name = $.trim($('#updatename').val()),
			    state=$('#updatestate').val(),
			 	number = $.trim($('#updatenumber').val()),
			 	shortcutHttp=$.trim($('#updateshortcutHttp').val()),
			 	images ='' ;
			 	if(imagesData.length>0){
			 	   for(var i=0;i<imagesData.length;i++){
			 	        var jsonImages=imagesData[i];
			 	        if(jsonImages.id='updateImaegs'){
			 	           images=jsonImages.value;
			 	        }
			 	   }
			 	}else{
			 	  images= $('#oldImages').val();
			 	}
				if($.trim(images)==''||$.trim(name)==''||$.trim(state)==''||$.trim(number)==''||$.trim(shortcutHttp)==''){
					return layer.msg('提交的信息不完整',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				$.post('${basePath}/shortcut/update.shtml',{id:id,images:images,name:name,state:state,number:number,shortcutHttp:shortcutHttp},function(result){
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
			
			function updateById(id){
			    <#--先删除多余的孩子-->
			    $('#boxUpdateshortcutForm').empty();	 
				if($.trim(id)==''){
					return layer.msg('请选择要修改的项。',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				$.post('${basePath}/common/getDict.shtml',function(obj){
				$.post('${basePath}/shortcut/findById.shtml',{id:id},function(result){
					layer.close(load);
					if(result.status != 200){
					 return layer.msg(result.message,so.mdefault),!1;
					}
					$('#update').modal();
					//初始化下拉框
					var updateSelect='<div class="form-group"><label for="recipient-name" class="control-label">上线/下线:</label><select id="updatestate"  class="form-control"></select></div>';
					var result=JSON.parse(result.shortcut);
		            var html ='<div class="form-group"><input type="hidden" value="'+result.images+'" id="oldImages"/><input type="hidden" value="'+result.id+'" id="id" name="id"/><label for="recipient-name" class="control-label">功能入口名称:</label><input type="text" class="form-control" name="name" id="updatename" value="'+result.name+'"/></div><div class="form-group"><label for="recipient-name" class="control-label">排序:</label><input type="number" class="form-control" value="'+result.number+'" name="number" id="updatenumber"/></div><div class="form-group"><label for="recipient-name" class="control-label">入口地址:</label><input type="url" class="form-control" name="shortcutHttp" id="updateshortcutHttp" value="'+result.shortcutHttp+'"/></div><div class="form-group"><label for="recipient-name" class="control-label">Logo图片:</label><img  src="'+result.images+'" style="width:300px;height:200px"/></div><div class="form-group"><label for="recipient-name" class="control-label">更新Logo图片:</label><input type="file" class="form-control" onclick="submitImages(this)" id="updateImaegs"  accept="image/*" multiple/><ul class="img-list"></ul></div>';
					$('#boxUpdateshortcutForm').html(html+updateSelect);	
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
			}); 
        }	
			
		</@shiro.hasPermission>	
		<@shiro.hasPermission name="/shortcut/add.shtml">
			function add(){
				var name =$.trim($('#name').val()),
				    state=$('#state').val(),
				    number=$.trim($('#number').val()),
				    shortcutHttp=$.trim($('#shortcutHttp').val()),
				    images='';
				    if(imagesData.length>0){
				       for(var i=0;i<imagesData.length;i++){
				          var jsonImages=imagesData[i];
				          if(jsonImages.id=='images'){
				              images=jsonImages.value;
				          }
				       }
				    }
				if($.trim(name) == ''||$.trim(state)==''||$.trim(number)==''||$.trim(shortcutHttp)==''||$.trim(images)==''){
					return layer.msg('提交的信息不符合规范!',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				$.post('${basePath}/shortcut/add.shtml',{name:name,number:number,shortcutHttp:shortcutHttp,state:state,images:images},function(result){
					layer.close(load);
					if(result && result.status != 200){
						return layer.msg(result.message,so.mdefault),!1;
					}
					layer.msg(result.message+'!');
					setTimeout(function(){
						$('#formId').submit();
					},1200);
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
				<@_left.operationControl 4/>
				<div class="col-md-10">
					<h2>快捷功能入口</h2>
					<hr>
					<form method="post" action="" id="formId" class="form-inline">
						<div clss="well">
					      <div class="form-group">
					        <input type="text" class="form-control" style="width: 300px;" value="${findContent?default('')}" 
					        			name="findContent" id="findContent" placeholder="请输入">
					      </div>
					     <span class=""> <#--pull-right -->
				         	<button type="submit" class="btn btn-primary">查询</button>
				         	<@shiro.hasPermission name="/shortcut/add.shtml">
				         		<a class="btn btn-success" onclick="$('#add').modal();">添加</a>
				         	</@shiro.hasPermission>
				         	<@shiro.hasPermission name="/shortcut/deleteById.shtml">
				         		<button type="button" id="deleteAll" class="btn  btn-danger">Delete</button>
				         	</@shiro.hasPermission>
				         </span>    
				        </div>
					<hr>
					<table class="table table-bordered">
						<tr>
							<th><input type="checkbox" id="checkAll"/></th>
							<th>名称</th>
							<th>ID</th>
							<th>状态</th>
							<th>排序</th>
							<th>入口地址</th>
							<th>图片地址</th>
							<th>创建日期</th>
							<th>操作</th>
						</tr>
						<#if page?exists && page.list?size gt 0 >
							<#list page.list as it>
								<tr>
									<td><input value="${it.id}" check='box' type="checkbox" /></td>
									<td>${it.name?default('-')}</td>
									<td>${it.id?default('-')}</td>
									<#if it.state==1>
										 <td style="color:red">
											<@mytags paramId="SYS_STATE,${it.state}">
											     ${dictCvalue}
											</@mytags>
									     </td>
									   <#else>
											<td>
											  <@mytags paramId="SYS_STATE,${it.state}">
											     ${dictCvalue}
											  </@mytags>
											</td>
									</#if> 
									<td>${it.number?default('-')}</td>
									<td>${it.shortcutHttp?default('-')}</td>
									<td><a href="javascript:showPic('${it.images}')">查看</a></td>
									<td>${(it.createTime?string('yyyy-MM-dd'))!'#'}</td>
									<td>
									   <@shiro.hasPermission name="/shortcut/deleteById.shtml">
											<i class="glyphicon glyphicon-remove"></i><a href="javascript:deleteById([${it.id}]);">删除&nbsp;</a>
								       </@shiro.hasPermission>
									   <@shiro.hasPermission name="/shortcut/update.shtml">
										    <a href="javascript:updateById(${it.id});">|&nbsp;修改</a>
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
			
			<@shiro.hasPermission name="/shortcut/add.shtml">
				<#--添加弹框-->
				<div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="addshortcutLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="addshortcutLabel">添加快捷功能入口</h4>
				      </div>
				      <div class="modal-body">
				        <form id="boxshortcutForm">
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">名称:</label>
				            <input type="text" class="form-control" name="name" id="name"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">上线/下线:</label>
				            <select id="state"  class="form-control">
				             
				            </select>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">地址:</label>
				            <input type="url" class="form-control" id="shortcutHttp" name="shortcutHttp"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">Logo图片地址:</label>
				            <input type="file" class="form-control" id="images" name="images" onclick="submitImages(this)" accept="image/*" multiple/>
				            <ul class="img-list"></ul>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">排序:</label>
				            <input type="number" class="form-control" id="number" name="number"/>
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
			
			
			<@shiro.hasPermission name="/shortcut/update.shtml">
				<#--添加弹框(修改)-->
				<div class="modal fade" id="update" tabindex="-1" role="dialog" aria-labelledby="addshortcutLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="addshortcutLabel">更新快捷功能入口信息</h4>
				      </div>
				      <div class="modal-body">
				        <form id="boxUpdateshortcutForm">
				           
				        </form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				        <button type="button" onclick="update();" class="btn btn-primary">Save</button>
				      </div>
				    </div>
				  </div>
				</div>
				<#--/添加弹框-->
			</@shiro.hasPermission>			
		</div>	
	<script  src="${basePath}/js/uploadAndsubmitImages.js"></script>						
</body>
<script>
	  window.onload=function(){
	  //初始化下拉框
	  var updateSelect='<div class="form-group"><label for="recipient-name" class="control-label">上线/下线:</label><select id="updatestate"  class="form-control"></select></div>'
	  $('#boxUpdateshortcutForm').html(updateSelect);
	  
	    if($('#state:has(option)').length!=0){
	       return;
	    }
	     //去拉取上下文字典数据
	     $.post('${basePath}/common/getDict.shtml',function(obj){
	        if(obj){
	          var select=document.getElementById('state');
	          var jsonarray=obj.result.SYS_STATE;
				   for(var x in jsonarray){
                      var option = new Option(jsonarray[x],x,true);				      
				      select.options.add(option);
				   }
	          }
	 });   	            
}
</script>

</html>