<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8" />
		<title>学生列表</title>
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
		<link   rel="icon" href="${basePath}/images/favicon.ico" type="image/x-icon" />
		<link   rel="shortcut icon" href="${basePath}/images/favicon.ico" />
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
		    li{list-style-type: none;}
		    canvas{width: 100%;border: 1px solid #000000;}
		    .img-list{margin: 10px 5px;}
		    .img-list li{position: relative;display: inline-block;width: 100px;height: 100px;margin: 5px 5px 20px 5px;border: 1px solid rgb(100,149,198);background: #fff no-repeat center;background-size: cover;}
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
				
				<@shiro.hasPermission name="/student/deleteById.shtml">
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
				<@shiro.hasPermission name="/student/checkById.shtml">
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
					return check1ById(array);
				});
				</@shiro.hasPermission>
				<@shiro.hasPermission name="/student/checkById.shtml">
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
					return check2ById(array);
				});
				</@shiro.hasPermission>
			});
			
			
			
			<@shiro.hasPermission name="/student/deleteById.shtml">
			<#--根据ID数组删除角色-->
			function deleteById(ids){
				var index = layer.confirm("确定删除这"+ ids.length +"个学生信息？",function(){
					var load = layer.load();
					$.post('${basePath}/student/deleteById.shtml',{ids:ids.join(',')},function(result){
						layer.close(load);
						if(result && result.status != 200){
							return layer.msg(result.message,so.mdefault),!0;
						}else{
							layer.msg(result.message);
							setTimeout(function(){
								$('#formId').submit();
							},1500);
						}
					},'json');
					layer.close(index);
				});
			}
			</@shiro.hasPermission>
			<@shiro.hasPermission name="/student/checkById.shtml">
			<#--根据ID数组审核信息-->
			function check1ById(ids){
			    var check1=1;
				var index = layer.confirm("确定审核这个学生信息？",function(){
					var load = layer.load();
					$.post('${basePath}/student/checkById.shtml',{ids:ids.join(','),myCheck:check1},function(result){
						layer.close(load);
						if(result && result.status != 200){
							return layer.msg(result.message,so.mdefault),!0;
						}else{
							layer.msg(result.message+'!');
							setTimeout(function(){
								$('#formId').submit();
							},1500);
						}
					},'json');
					layer.close(index);
				});
			}
			</@shiro.hasPermission>
			<@shiro.hasPermission name="/student/checkById.shtml">
			<#--根据ID数组审核信息-->
			function check2ById(ids){
			    var check2=2;
				var index = layer.confirm("确定审核这个学生信息？",function(){
					var load = layer.load();
					$.post('${basePath}/student/checkById.shtml',{ids:ids.join(','),myCheck:check2},function(result){
						layer.close(load);
						if(result && result.status != 200){
							return layer.msg(result.message,so.mdefault),!0;
						}else{
							layer.msg(result.message+'!');
							setTimeout(function(){
								$('#formId').submit();
							},1000);
						}
					},'json');
					layer.close(index);
				});
			}
			</@shiro.hasPermission>
			<@shiro.hasPermission name="/student/add.shtml">
			<#--添加学生信息-->
			function add(){
				var name = $('#name').val(),
				    tel = $('#tel').val(),
				    identity=$('#identity').val(),
				    studentIdCard=$('#studentIdCard').val(),
				    school=selectSchool.options[selectSchool.options.selectedIndex].text,
				    timeofEnrollment=$('#timeofEnrollment').val(),
				    profession=$('#profession').val(), 
				    shcoolProvinceId=$('#shcoolProvinceId option:selected').val(),
				    images='',
				    llb=$('#llb').val();
				    if(imagesData.length>0){
				          for(var i=0;i<imagesData.length;i++){
				                var jsonImages=imagesData[i];
				               if(jsonImages.id=='images'){
				                   images=jsonImages.value;
				                   break;
				               }
				        }
				    }
				if($.trim(shcoolProvinceId)==''||$.trim(tel)==''||$.trim(images)==''||$.trim(name) == ''||$.trim(studentIdCard)==''||$.trim(identity)==''||$.trim(school)==''||$.trim(images)==''||$.trim(profession)==''){
					return layer.msg('注意信息不能为空。',so.mdefault),!1;
				}
				//验证手机号码(包括移动、联通、电信)    
				var yidong = /^170[356]\d{7}$|^(?:13[4-9]|147|178|15[0-27-9]|178|18[2-478])\d{8}$/,    
				    liantong = /^170[4789]\d{7}$|^(?:13[0-2]|145|15[56]|17[165]|18[56])\d{8}$/,    
				    dianxin = /^170[01]\d{7}$|^(?:133|153|173|177|18[019])\d{8}$/;
				for(var i=0;i<1;i++){
				if(yidong.test(tel)||liantong.test(tel)||dianxin.test(tel)){
				     break;
				     }else{
				        return layer.msg('电话号码不正确!',so.mdefault),!1;
				   }
				}
				var load = layer.load();
				$.post('${basePath}/student/add.shtml',{shcoolProvinceId:shcoolProvinceId,name:name,tel:tel,identity:identity,school:school,studentIdCard:studentIdCard,timeofEnrollment:timeofEnrollment,profession:profession,images:images,llb:llb},function(result){
					layer.close(load);
					if(result && result.status != 200){
						return layer.msg(result.message,so.mdefault),!1;
					}
					layer.msg(result.message+'!');
					setTimeout(function(){
						$('#formId').submit();
					},1500);
				},'json');
			}
			</@shiro.hasPermission>
			
			<@shiro.hasPermission name="/student/update.shtml">
			<#--原因填写-->
			function updateById(id){
			    <#--先删除多余的孩子-->
			    $('#boxUpdateStudentForm').empty();	
				if($.trim(id)==''|| id == null){
					return layer.msg('请选择要修改的项。',so.mdefault),!1;
				}
                var html='<div class="form-group"><input type="hidden" value="'+id+'" id="id"/><label for="recipient-name" class="control-label">原因填写:</label><input type="text" class="form-control" name="causeReason" id="updatecauseReason"/></div>';
				$('#update').modal();
				$('#boxUpdateStudentForm').append(html);					
					
			}
			<#--修改完成后提交-->
			function update(){
			
			 	var id=$('#id').val(),causeReason = $('#updatecauseReason').val();
				if($.trim(id)==''||$.trim(causeReason)==''){
					return layer.msg('注意信息不能为空。',so.mdefault),!1;
				}
				<#--loding-->
				var load = layer.load();
				$.post('${basePath}/student/update.shtml',{id:id,causeReason:causeReason},function(result){
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
		  
		  
		  <#--不通过审核原因查看-->
		  function causeReasonById(id){
		       <#--先删除多余的孩子-->
			    $('#boxcauseReasonForm').empty();	
				<#--loding-->
				var load = layer.load();
				$.post('${basePath}/student/findById.shtml',{id:id},function(result){
					layer.close(load);
					if(result.status != 200){
					 return layer.msg(result.message,so.mdefault),!1;
					}
					$('#causeReasonForm').modal();
					var result=JSON.parse(result.student);
                    var html='<div class="form-group"><label for="recipient-name" class="control-label">不通过原因:</label><input type="text" value="'+result.causeReason+'" class="form-control" readonly="readonly"/></div>';
					$('#boxcauseReasonForm').append(html);					
				},'json');
		  
		  }
		  
			
		</script>
		<script type="text/javascript">
	          function showPic(filePath) {//显示图片
		        if (filePath != '') {
			$.dialog({
				 title : '查询图片',
				 width : '200px',
				 height : '200px',
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
				<@_left.operationControl 1/>
				<div class="col-md-10">
					<h2>学生信息列表</h2>
					<hr>
					<form method="post" action="" id="formId" class="form-inline">
						<div clss="well">
					      <div class="form-group">
					        <input type="text" class="form-control" style="width: 300px;" value="${findContent?default('')}" 
					        			name="findContent" id="findContent" placeholder="请输入">
					      </div>
					     <span class=""> <#--pull-right -->
				         	<button type="submit" class="btn btn-primary">查询</button>
				         	<@shiro.hasPermission name="/student/add.shtml">
				         		<a class="btn btn-success" onclick="$('#add').modal();">新增</a>
				         	</@shiro.hasPermission>
				         	<@shiro.hasPermission name="/student/deleteById.shtml">
				         		<button type="button" id="deleteAll" class="btn  btn-danger">Delete</button>
				         	</@shiro.hasPermission>
				         	<@shiro.hasPermission name="/student/checkById.shtml">
				                <button type="button" id="check1ById" class="btn btn-success">审核-YES</button>
						    </@shiro.hasPermission>
							<@shiro.hasPermission name="/student/checkById.shtml">
						        <button type="button" id="check2ById" class="btn  btn-danger">审核-NO</button>
						    </@shiro.hasPermission>
				         </span>    
				        </div>
					<hr>
					<table class="table table-bordered">
						<tr>
							<th><input type="checkbox" id="checkAll"/></th>
							<th>姓名</th>
							<th>手机号</th>
							<th>身份证</th>
							<th>学生证</th>
							<th>入学时间</th>
							<th>就读学校</th>
							<th>专业</th>
							<th>证件照</th>
							<th>推荐人</th>
							<th>审核状态</th>
							<th>审核日期</th>
							<th>操作</th>
						</tr>
						<#if page?exists && page.list?size gt 0 >
							<#list page.list as it>
								<tr>
									<td>
									  <#if it.myCheck == 1>
									     *
									    <#else>
									     <input value="${it.id}" check='box' type="checkbox" />
									  </#if>
								    </td>
									<td>${it.name?default('-')}</td>
									<td>${it.tel?default('-')}</td>
									<td>${it.identity?default('-')}</td>
									<td>${it.studentIdCard?default('-')}</td>
									<td>${(it.timeofEnrollment?string('yyyy-MM-dd'))!'-'}</td>
									<td>${it.school?default('-')}</td>
									<td>${it.profession?default('-')}</td>
									<#--tomcat使用了虚拟路径-->
									<td><a href="javascript:showPic('${it.images}')">查看</a></td>
									<td>${it.llb?default('-')}</td>
									<td>
									   <@mytags paramId="APPLY_STATE,${it.myCheck}">
									      ${dictCvalue}
									   </@mytags>
									</td>
									<td>${(it.checkTime?string('yyyy-MM-dd'))!'#'}</td>
									<td>
									   <#if it.myCheck == 1>
									       无操作
									   </#if>									
									   <#if it.myCheck == 0>
									       <@shiro.hasPermission name="/student/deleteById.shtml">
											  <i class="glyphicon glyphicon-remove"></i><a href="javascript:deleteById([${it.id}]);">删除&nbsp;</a>
								           </@shiro.hasPermission>
									   </#if>
									   <#if it.myCheck == 2>
									      <@shiro.hasPermission name="/student/index.shtml">
									       <#if it.causeReason??>
									              <@shiro.hasPermission name="/student/index.shtml">
									                <a href="javascript:causeReasonById(${it.id});">查看原因</a>
									              </@shiro.hasPermission>
									          <#else>
									               <a href="javascript:updateById(${it.id});">编辑原因</a>
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
			
			<@shiro.hasPermission name="/student/add.shtml">
				<#--添加弹框-->
				<div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="addstudentLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="addstudentLabel">添加学生信息</h4>
				      </div>
				      <div class="modal-body">
				        <form id="boxStudentForm">
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">学生姓名:</label>
				            <input type="text" class="form-control" name="name" id="name" placeholder="请输入学生姓名"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">手机号:</label>
				            <input type="tel" class="form-control" name="tel" id="tel" placeholder="请输入手机号码" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">身份证:</label>
				            <input type="text" class="form-control" id="identity" name="identity"  placeholder="身份证号"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">学生证:</label>
				            <input type="text" class="form-control" id="studentIdCard" name="studentIdCard"  placeholder="学生证号"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">就读学校:</label>
				            <select id="key" onchange="init()">
				               <option>请输入首字母大写搜索</option>
				            </select>
				            <select  class="form-control" id="school" name="school" />
				            
				            </select>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">学校所在省份:</label>
				             <select class="form-control" id="shcoolProvinceId">
				             
				             </select>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">入读时间:</label>
				            <input type="date" class="form-control" id="timeofEnrollment" name="timeofEnrollment"  placeholder="日期"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">所学专业:</label>
				            <input type="text" class="form-control" id="profession" name="profession"  placeholder="专业"/>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">学生证件照片:</label>
				            <input type="file" class="form-control" onclick="submitImages(this)" id="images" name="images" accept="image/*" multiple/>
				            <ul class="img-list"></ul>
				          </div>
				          <div class="form-group">
				            <label for="recipient-name" class="control-label">推荐人:</label>
				            <input type="text" class="form-control" id="llb" name="llb"/>
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
			
			
			<@shiro.hasPermission name="/student/update.shtml">
				<#--添加弹框(填写原因)-->
				<div class="modal fade" id="update" tabindex="-1" role="dialog" aria-labelledby="addstudentLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="addstudentLabel">原因填写</h4>
				      </div>
				      <div class="modal-body">
				        <form id="boxUpdateStudentForm">
				        
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
			<@shiro.hasPermission name="/student/update.shtml">
			
				<#--添加弹框(显示原因)-->
				<div class="modal fade" id="causeReasonForm" tabindex="-1" role="dialog" aria-labelledby="addstudentLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="addstudentLabel">原因说明</h4>
				      </div>
				      <div class="modal-body">
				        <form id="boxcauseReasonForm">
				        
				        </form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				      </div>
				    </div>
				  </div>
				</div>
				<#--/添加弹框-->
			</@shiro.hasPermission>
			
	</div>	
	<script  src="${basePath}/js/uploadAndsubmitImages.js"></script>
	<script>
	 var skey=document.getElementById("key");
	 var select=document.getElementById('shcoolProvinceId');
	 var selectSchool=document.getElementById('school');
	 var schoolKey=[];
	 var schoolName=[];
	  window.onload=function(){
	     //去拉取后台省份数据
	    if($('#shcoolProvinceId:has(option)').length!=0){
	       return;
	    }
	     $.post('${basePath}/common/getProvince.shtml',function(obj){
	        if(obj){
	          var jsonarray=obj.result;
				   for(var x in jsonarray){
                      var option = new Option(jsonarray[x],x,true);				      
				      select.options.add(option);
				   }
	          }
	     });              
	     //去拉取后台高校数据
	    if($('#shcool:has(option)').length!=0){
	       return;
	    }
	     $.post('${basePath}/common/getSchoolDict.shtml',function(obj){
	        if(obj){
	               var key=obj.result;
		           for(var i in key){
		             schoolKey[i]=key[i];
		            var option = new Option(schoolKey[i].name,i,true);				      
			        skey.options.add(option); 
		      }
	       }
    	});  
}
	  function init(){
	         while(selectSchool.hasChildNodes()) {  
                selectSchool.removeChild(selectSchool.firstChild);  
             }  
	         var txt=skey.options[skey.options.selectedIndex].text;
	  	      for(var i in schoolKey){
	  	            var keyName=schoolKey[i].name;
	  	            var school= schoolKey[i].items;
	  	            if(keyName==txt){
                      	for(var j in school){
	                       var option = new Option(school[j].name,school[j].value,true);				      
			               selectSchool.options.add(option); 
                      	}
	  	          }
	        }
	}
</script>
</body>
</html>