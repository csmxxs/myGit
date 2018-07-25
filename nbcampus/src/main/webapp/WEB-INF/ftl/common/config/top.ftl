<#macro top index>
<script baseUrl="${basePath}" src="${basePath}/js/user.login.js"></script>
<div class="navbar navbar-inverse navbar-fixed-top animated fadeInDown" style="z-index: 101;height: 41px;">
	  
      <div class="container" style="padding-left: 0px; padding-right: 0px;">
        <div class="navbar-header">
          <button data-target=".navbar-collapse" data-toggle="collapse" type="button" class="navbar-toggle collapsed">
            <span class="sr-only">导航</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
	     </div>
	     <div role="navigation" class="navbar-collapse collapse">
	          <ul class="nav navbar-nav" id="topMenu">
				<li class="dropdown ${(index==1)?string('active','')}">
					<a aria-expanded="false" aria-haspopup="true" role="button" data-toggle="dropdown" class="dropdown-toggle" href="${basePath}/user/index.shtml">
						个人中心<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="${basePath}/user/index.shtml">个人资料</a></li>
						<li><a href="${basePath}/user/updateSelf.shtml" >资料修改</a></li>
						<li><a href="${basePath}/user/updatePswd.shtml" >密码修改</a></li>
						<li><a href="${basePath}/role/mypermission.shtml">我的权限</a></li>
					</ul>
				</li>	  
				<#--拥有 角色888888（管理员）-->
				<@shiro.hasAnyRoles name='888888'>          
				<li class="dropdown ${(index==2)?string('active','')}">
					<a aria-expanded="false" aria-haspopup="true"  role="button" data-toggle="dropdown" class="dropdown-toggle" href="${basePath}/member/list.shtml">
						用户中心<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<@shiro.hasPermission name="/member/list.shtml">
							<li><a href="${basePath}/member/list.shtml">用户列表</a></li>
						</@shiro.hasPermission>
						<@shiro.hasPermission name="/member/online.shtml">
							<li><a href="${basePath}/member/online.shtml">在线用户</a></li>
						</@shiro.hasPermission>
					</ul>
				</li>	
				</@shiro.hasAnyRoles>         
				<#--拥有 角色888888（管理员） -->
				<@shiro.hasAnyRoles name='888888'>
					<li class="dropdown ${(index==3)?string('active','')}">
						<a aria-expanded="false" aria-haspopup="true"  role="button" data-toggle="dropdown" class="dropdown-toggle" href="${basePath}/permission/index.shtml">
							权限管理<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<@shiro.hasPermission name="/role/index.shtml">
								<li><a href="${basePath}/role/index.shtml">角色列表</a></li>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="/role/allocation.shtml">
								<li><a href="${basePath}/role/allocation.shtml">角色分配</a></li>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="/permission/index.shtml">
								<li><a href="${basePath}/permission/index.shtml">权限列表</a></li>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="/permission/allocation.shtml">
								<li><a href="${basePath}/permission/allocation.shtml">权限分配</a></li>
							</@shiro.hasPermission>
						</ul>
					</li>	
				</@shiro.hasAnyRoles>  
				<#--拥有管理员权限才会显示 -->
				<@shiro.hasAnyRoles name='888888'>
					<li class="dropdown ${(index==4)?string('active','')}">
						<a aria-expanded="false" aria-haspopup="true"  role="button" data-toggle="dropdown" class="dropdown-toggle" href="${basePath}/student/index.shtml">
							业务管理<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
						   <@shiro.hasPermission name="/student/index.shtml">
						     <li>
						       <a href="${basePath}/student/index.shtml">学生信息审核</a>
						     </li>
						   </@shiro.hasPermission>
						   <@shiro.hasPermission name="/task/index.shtml">
						     <li>
						       <a href="${basePath}/task/index.shtml">任务列表审核</a>
						     </li>
						   </@shiro.hasPermission>
						   <@shiro.hasPermission name="/banner/index.shtml">
						     <li>
						       <a href="${basePath}/banner/index.shtml">广告列表审核</a>
						     </li>
						   </@shiro.hasPermission>
						   <@shiro.hasPermission name="/shortcut/index.shtml">
						     <li>
						       <a href="${basePath}/shortcut/index.shtml">快捷功能入口</a>
						     </li>
						   </@shiro.hasPermission>
						   <@shiro.hasPermission name="/prefecture/index.shtml">
						     <li>
						       <a href="${basePath}/prefecture/index.shtml">任务专区管理</a>
						     </li>
						   </@shiro.hasPermission>
						   <@shiro.hasPermission name="/taskTesultCount/index.shtml">
						     <li>
						       <a href="${basePath}/taskTesultCount/index.shtml">效果分析</a>
						     </li>
						   </@shiro.hasPermission>
						</ul>
					</li>
					</@shiro.hasAnyRoles> 
					<#--拥有 角色888888（管理员） -->
					<@shiro.hasAnyRoles name='888888'>
						<li class="dropdown ${(index==5)?string('active','')}">
							<a aria-expanded="false" aria-haspopup="true"  role="button" data-toggle="dropdown" class="dropdown-toggle" href="${basePath}/dict/index.shtml">
								字典管理<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<@shiro.hasPermission name="/dict/index.shtml">
									<li><a href="${basePath}/dict/index.shtml">字典列表</a></li>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="/province/index.shtml">
									<li><a href="${basePath}/province/index.shtml">省份列表</a></li>
								</@shiro.hasPermission>
								<@shiro.hasPermission name="/school/index.shtml">
									<li><a href="${basePath}/school/index.shtml">高校列表</a></li>
								</@shiro.hasPermission>
							</ul>
						</li>	
					</@shiro.hasAnyRoles> 
					
					<#--拥有 角色888888（管理员） -->
					<@shiro.hasAnyRoles name='888888,100003'>
						<li class="dropdown ${(index==6)?string('active','')}">
							<a aria-expanded="false" aria-haspopup="true"  role="button" data-toggle="dropdown" class="dropdown-toggle" href="${basePath}/grantCoin/index.shtml">
								牛币派发管理<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<@shiro.hasPermission name="/grantCoin/index.shtml">
									<li><a href="${basePath}/grantCoin/index.shtml">派发列表</a></li>
								</@shiro.hasPermission>
							</ul>
						</li>	
					</@shiro.hasAnyRoles> 	
					
					
	           </ul>
	           <ul class="nav navbar-nav  pull-right" >
				<li class="dropdown ${(index==10)?string('active','')}" style="color:#fff;">
					<a aria-expanded="false" aria-haspopup="true"  role="button" data-toggle="dropdown"  
						<@shiro.user>  
							onclick="location.href='${basePath}/user/index.shtml'" href="${basePath}/user/index.shtml" class="dropdown-toggle qqlogin" >
							${token.nickname?default('Hello')}<span class="caret"></span></a>
							<ul class="dropdown-menu" userid="${token.id}">
								<li><a href="${basePath}/user/index.shtml">个人资料</a></li>
								<li><a href="${basePath}/role/mypermission.shtml">我的权限</a></li>
								<li><a href="javascript:void(0);" onclick="logout();">退出登录</a></li>
							</ul>
						</@shiro.user>  
						<@shiro.guest>   
							 href="javascript:void(0);" onclick="location.href='${basePath}/u/login.shtml'" class="dropdown-toggle qqlogin" >
							<img src="http://qzonestyle.gtimg.cn/qzone/vas/opensns/res/img/Connect_logo_1.png">&nbsp;登录</a>
						</@shiro.guest>  					
				</li>	
	          </ul>
	          <style>#topMenu>li>a{padding:10px 13px}</style>
	    </div>
  	</div>
</div>
</#macro>
