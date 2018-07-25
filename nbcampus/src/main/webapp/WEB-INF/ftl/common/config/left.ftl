<#macro user index>
<div id="one" class="col-md-2">
	<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
	  <li class="${(index==1)?string('active',' ')}">
	      <a href="${basePath}/user/index.shtml">
	    	 <i class="glyphicon glyphicon-chevron-right"></i>个人资料
	      </a>
	       <ul class="dropdown-menu" aria-labelledby="dLabel" style="margin-left: 160px; margin-top: -40px;">
              <li><a href="${basePath}/user/updateSelf.shtml">资料修改</a></li>
              <li><a href="${basePath}/user/updatePswd.shtml">密码修改</a></li>
          </ul>
	  </li>
	  <li class="${(index==2)?string('active',' ')} dropdown">
	      <a href="${basePath}/role/mypermission.shtml">
	    	 <i class="glyphicon glyphicon-chevron-right"></i>我的权限
	      </a>
	  </li>
	</ul>
</div>
</#macro>
<#macro member index>
	<@shiro.hasAnyRoles name='888888'>          
		<div  id="one" class="col-md-2">
			<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
			  <li class="${(index==1)?string('active',' ')}">
			      <a href="${basePath}/member/list.shtml">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>用户列表
			      </a>
			  </li>
			  <li class="${(index==2)?string('active',' ')} dropdown">
			      <a href="${basePath}/member/online.shtml">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>在线用户
			      </a>
			  </li>
			</ul>
		</div>
	</@shiro.hasAnyRoles>         
</#macro>
<#macro role index>
	<@shiro.hasAnyRoles name='888888'>  
		<div id="one" class="col-md-2">
			<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
			 
			 <@shiro.hasPermission name="/role/index.shtml">
			  <li class="${(index==1)?string('active',' ')}">
			      <a href="${basePath}/role/index.shtml">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>角色列表
			      </a>
			  </li>
			  </@shiro.hasPermission>
			 <@shiro.hasPermission name="/role/allocation.shtml">
			  <li class="${(index==2)?string('active',' ')} dropdown">
			      <a href="${basePath}/role/allocation.shtml">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>角色分配
			      </a>
			  </li>
			  </@shiro.hasPermission>
			  <@shiro.hasPermission name="/permission/index.shtml">
			  <li class="${(index==3)?string('active',' ')} dropdown">
			      <a href="${basePath}/permission/index.shtml">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>权限列表
			      </a>
			  </li>
			  </@shiro.hasPermission>
			  <@shiro.hasPermission name="/permission/allocation.shtml">
			  <li class="${(index==4)?string('active',' ')} dropdown">
			      <a href="${basePath}/permission/allocation.shtml">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>权限分配
			      </a>
			  </li>
			  </@shiro.hasPermission>
			</ul>
		</div>
	</@shiro.hasAnyRoles>   
</#macro>


<#macro operationControl index>
	<@shiro.hasAnyRoles name='888888'>          
		<div  id="one" class="col-md-2">
			<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
			  <@shiro.hasPermission name="/student/index.shtml">
			  <li class="${(index==1)?string('active',' ')}">
			      <a href="${basePath}/student/index.shtml">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>学生信息列表
			      </a>
			  </li>
			  </@shiro.hasPermission>
			  <@shiro.hasPermission name="/task/index.shtml">
			  <li class="${(index==2)?string('active',' ')} dropdown">
			      <a href="${basePath}/task/index.shtml">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>任务列表
			      </a>
			  </li>
			  </@shiro.hasPermission>
			  <@shiro.hasPermission name="/banner/index.shtml">
			  <li class="${(index==3)?string('active',' ')} dropdown">
			      <a href="${basePath}/banner/index.shtml">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>广告列表
			      </a>
			  </li>
			  </@shiro.hasPermission>
			  <@shiro.hasPermission name="/shortcut/index.shtml">
			  <li class="${(index==4)?string('active',' ')} dropdown">
			      <a href="${basePath}/shortcut/index.shtml">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>快捷功能入口
			      </a>
			  </li>
			  </@shiro.hasPermission>
			  <@shiro.hasPermission name="/prefecture/index.shtml">
			  <li class="${(index==5)?string('active',' ')} dropdown">
			      <a href="${basePath}/prefecture/index.shtml">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>任务专区管理
			      </a>
			  </li>
			  </@shiro.hasPermission>
			  <@shiro.hasPermission name="/taskTesultCount/index.shtml">
			  <li class="${(index==6)?string('active',' ')} dropdown">
			      <a href="${basePath}/taskTesultCount/index.shtml">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>效果分析
			      </a>
			  </li>
			  </@shiro.hasPermission>
			</ul>
		</div>
	</@shiro.hasAnyRoles>         
</#macro>


<#macro myDict index>
	<@shiro.hasAnyRoles name='888888'>          
		<div  id="one" class="col-md-2">
			<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
				  <@shiro.hasPermission name="/dict/index.shtml">
					  <li class="${(index==1)?string('active',' ')} dropdown">
					      <a href="${basePath}/dict/index.shtml">
					    	 <i class="glyphicon glyphicon-chevron-right"></i>字典管理
					      </a>
					  </li>
				  </@shiro.hasPermission>
				  <@shiro.hasPermission name="/province/index.shtml">
					  <li class="${(index==2)?string('active',' ')} dropdown">
					      <a href="${basePath}/province/index.shtml">
					    	 <i class="glyphicon glyphicon-chevron-right"></i>省份管理
					      </a>
					  </li>
				  </@shiro.hasPermission>
				  <@shiro.hasPermission name="/school/index.shtml">
					  <li class="${(index==3)?string('active',' ')} dropdown">
					      <a href="${basePath}/school/index.shtml">
					    	 <i class="glyphicon glyphicon-chevron-right"></i>高校管理
					      </a>
					  </li>
				  </@shiro.hasPermission>
			</ul>
		</div>
	</@shiro.hasAnyRoles>         
</#macro>


<#macro grantCoin index>
	<@shiro.hasAnyRoles name='888888,100003'>          
		<div  id="one" class="col-md-2">
			<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
				  <@shiro.hasPermission name="/grantCoin/index.shtml">
					  <li class="${(index==1)?string('active',' ')} dropdown">
					      <a href="${basePath}/grantCoin/index.shtml">
					    	 <i class="glyphicon glyphicon-chevron-right"></i>牛币派发列表
					      </a>
					  </li>
				  </@shiro.hasPermission>
			</ul>
		</div>
	</@shiro.hasAnyRoles>         
</#macro>