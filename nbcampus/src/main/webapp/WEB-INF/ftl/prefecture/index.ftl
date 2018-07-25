<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8" />
		<title>任务专区管理</title>
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
		<link   rel="icon" href="${basePath}/images/favicon.ico" type="image/x-icon" />
		<link   rel="prefecture icon" href="${basePath}/images/favicon.ico" />
		<link href="${basePath}/js/common/bootstrap/3.3.5/css/bootstrap.min.css?${_v}" rel="stylesheet"/>
		<link href="${basePath}/css/common/base.css?${_v}" rel="stylesheet"/>
		<style>
		  table th {
		    text-align:center;
		  }
		  table td {
		    text-align:center;
		  }
		  *{margin: 0;padding: 0;}
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
		<script>
			
			so.init(function(){
				//初始化全选。
				so.checkBoxInit('#checkAll','[check=box]');
				
				<@shiro.hasPermission name="/prefecture/deleteById.shtml">
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
			<@shiro.hasPermission name="/prefecture/deleteById.shtml">
			function deleteById(ids){
                
			    if($.trim(ids)=='' || $.trim(ids) == null){
			      return layer.msg('请选择要删除的选项。',so.mdefault),!0;
			    }
			    var id= ids[0];
			    console.log(id);
				var index = layer.confirm("确定这"+ids.length+"条信息？",function(){
					var load = layer.load();
					$.post('${basePath}/prefecture/deleteById.shtml',{ids:ids.join(',')},function(result){
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
		<@shiro.hasPermission name="/prefecture/add.shtml">
			function add(){
				var name = $('#name').val(),
				    state=$('#state').val(),
     		        logoImagesHttp='';
     		    //压缩后得到的图片
     		    if(imagesData.length>0){
     		        for(var i=0;i<imagesData.length;i++){
     		            var jsonImages=imagesData[i];
     		            if(jsonImages.id=='logoImagesHttp'){
     		                logoImagesHttp=jsonImages.value;
     		            }
     		        }
     		    }   
				if($.trim(name) == ''||$.trim(state)==''||$.trim(logoImagesHttp)==''){
				
					return layer.msg('注意信息不能为空。',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				$.post('${basePath}/prefecture/add.shtml',{name:name,state:state,logoImagesHttp:logoImagesHttp},function(result){
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
				
	</script>
	
    <script>
		
		// 子窗口回传任务列表ID
		function closeDialog(taskIds) {//关闭窗口
			var index = layer.confirm("确定添加这"+ taskIds.length +"个任务列表？",function(){
					var load = layer.load();
					$.post('${basePath}/prefecture/getTask.shtml',{taskIds:taskIds.join(',')},function(result){
						layer.close(load);
						if(result && result.status != 200){
							return layer.msg(result.message,so.mdefault),!0;
						}
					    //开始打印
						//每次查询数据回来之前先删除旧的数据
						$("#taskTable tr[class^=tr]").remove();
						var htr = $("#taskTable tr:hidden");//得到table里的所有子孙
						$.post('${_cxt}/common/getDict.shtml',function(object){//查询字典的值
						$.post('${_cxt}/common/getProvince.shtml',function(obj){//查询省份的值
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
						    //填写任务状态
				           if(object){
				               var dictArray=object.result.SYS_STATE;
							     for(var x in dictArray){
								    if(x==o.state){
									   ntd.eq(4).html(dictArray[x]); 
									   break ;
								   }
							    }
				            }
							 //填写中文省份
				            if(obj){
				               var jsonarray=obj.result;
							     for(var x in jsonarray){
								    if(x==o.generalize){
									    ntd.eq(5).html(jsonarray[x]); 
									    break ;
								  }
							   }
				           }
							ntd.eq(6).html("<a onclick='deleteTr(this);' style=\"cursor:pointer\">删除</a>");
							//追加到容器末尾
							$("#taskTable").append(ntr);
						}	
					},'json');
					layer.close(index);
				    });
			    });
			});
			dialog.close();//关闭窗口
		}
		
		function deleteTr(obj){

		  $(obj.parentNode.parentNode).remove();
		   
		}

        //update弹出窗
		var dialog1;
		function openDialog1(tittle,url) {//打开弹出窗口
			dialog1 = $.dialog({
				title : tittle,
				width : '600px',
				height : '550px',
				content : 'url:'+url
			});
		}
		
		 function showPic(filePath) {//显示图片
		    if (filePath != '') {
			$.dialog({
				 title : '查询图片',
				 width : '300px',
				 height : '300px',
				 content : '<img src="'+ filePath +'" />'
			    });
		      }

	     }
	     //关闭修改窗口
		function closeUpdate(){
		  dialog1.close();
		}
	</script>
	
	</head>
	<body data-target="#one" data-spy="scroll">
		<#--引入头部-->
		<@_top.top 3/>
		<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
			<div class="row">
				<#--引入左侧菜单-->
				<@_left.operationControl 5/>
				<div class="col-md-10">
					<h2>任务专区管理</h2>
					<hr>
					<form method="post" action="" id="formId" class="form-inline">
						<div clss="well">
					      <div class="form-group">
					        <input type="text" class="form-control" style="width: 300px;" value="${findContent?default('')}" 
					        			name="findContent" id="findContent" placeholder="请输入">
					      </div>
					     <span class=""> <#--pull-right -->
				         	<button type="submit" class="btn btn-primary">查询</button>
				         	<@shiro.hasPermission name="/prefecture/add.shtml">
				         		<a class="btn btn-success" onclick="$('#add').modal();">添加</a>
				         	</@shiro.hasPermission>
				         	<@shiro.hasPermission name="/prefecture/deleteById.shtml">
				         		<button type="button" id="deleteAll" class="btn  btn-danger">Delete</button>
				         	</@shiro.hasPermission>
				         </span>    
				        </div>
					<hr>
					<table class="table table-bordered">
						<tr>
							<th><input type="checkbox" id="checkAll"/></th>
							<th>专区ID</th>
							<th>专区名称</th>
							<th>状态</th>
							<th>图片地址</th>
							<th>创建日期</th>
							<th>操作</th>
						</tr>
						<#if page?exists && page.list?size gt 0 >
							<#list page.list as it>
								<tr>
									<td><input value="${it.id}" check='box' type="checkbox" /></td>
									<td>${it.id?default('-')}</td>
									<td>${it.name?default('-')}</td>
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
									<td><a href="javascript:showPic('${it.logoImagesHttp}');">查看</a></td>
									<td>${(it.createTime?string('yyyy-MM-dd'))!'#'}</td>
									<td>
										   
										   <@shiro.hasPermission name="/prefecture/deleteById.shtml">
												<i class="glyphicon glyphicon-remove"></i><a href="javascript:deleteById(['${it.id}']);">删除&nbsp;</a>
									       </@shiro.hasPermission>
										   <@shiro.hasPermission name="/prefecture/update.shtml">
											    <a href="javascript:openDialog1('专区更新','${basePath}/prefecture/updatejsp.shtml?id=${it.id}')">|&nbsp;更新/添加任务列表</a>
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
			
			<@shiro.hasPermission name="/prefecture/add.shtml">
				<#--添加弹框-->
				<div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="addprefectureLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="addprefectureLabel">任务专区添加</h4>
				      </div>
				      <div class="modal-body">
				        <form id="boxprefectureForm">
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">任务专区名称:</label>
				            <input type="text" class="form-control" name="name" id="name"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">上线/下线:</label>
				            <select id="state" class="form-control">
				            
				            </select>
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
				        <button type="button" onclick="add();" class="btn btn-primary">Save</button>
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