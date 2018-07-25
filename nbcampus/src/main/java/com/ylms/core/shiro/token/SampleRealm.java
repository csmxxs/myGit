package com.ylms.core.shiro.token;

import java.util.Date;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.ylms.common.entity.UUser;
import com.ylms.core.shiro.token.manager.TokenManager;
import com.ylms.permission.service.PermissionService;
import com.ylms.permission.service.RoleService;
import com.ylms.user.service.IUUserDao;

/**
 * Description:用户的鉴权 <br>
 * Company:www.ylms.com <br>
 * @author XieXiongShi
 * @date 2018年7月9日 下午5:03:48
 * @version V 1.0 
 */
public class SampleRealm extends AuthorizingRealm {

	@Autowired
	IUUserDao userService;
	@Autowired
	PermissionService permissionService;
	@Autowired
	RoleService roleService;

	public SampleRealm() {
		super();
	}

	/**
	 * 
	 * MethodsName: doGetAuthenticationInfo<br>
	 * Description:实现自己的判断逻辑 认证信息，主要针对用户登录. <br>
	 * @param authcToken(由UsernamePasswordToken类实现,ShiroToken继承UsernamePasswordToken)
	 * @return
	 * @throws AuthenticationException
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {

		ShiroToken token = (ShiroToken) authcToken;
		UUser user = userService.login(token.getUsername(), token.getPswd());
		if (null == user) {
			throw new AccountException("帐号或密码不正确！");
			/**
			 * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
			 */
		} else if (UUser._0.equals(user.getStatus())) {
			throw new DisabledAccountException("帐号已经禁止登录！");
		} else {
			// 更新登录时间 last login time
			user.setLastLoginTime(new Date());
			userService.updateByPrimaryKeySelective(user);
		}
		//采用分布式系统时，该处是否应将用户信息保存到共享缓存中呢？
		//思考下......(不放入共享缓存中，似乎没法实现分布式)
		
		
		// 交给Shiro对用户信息进行比对
		return new SimpleAuthenticationInfo(user, user.getPswd(), getName());
	}

	/**
	 * 
	 * MethodsName: doGetAuthorizationInfo<br>
	 * Description: <br>
	 * @param principals
	 * @return
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {

		Long userId = TokenManager.getUserId();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 根据用户ID查询角色（role），放入到Authorization里。
		Set<String> roles = roleService.findRoleByUserId(userId);
		info.setRoles(roles);
		// 根据用户ID查询权限（permission），放入到Authorization里。
		Set<String> permissions = permissionService
				.findPermissionByUserId(userId);
		info.setStringPermissions(permissions);
		return info;
	}

	/**
	 * 
	 * @MethodName: clearCachedAuthorizationInfo
	 * @Description: 为用户授权
	 * @param:
	 * @return: void
	 * @throws
	 */
	public void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject()
				.getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 
	 * MethodsName: clearCachedAuthorizationInfo<br>
	 * Description: 清空用户权限<br>
	 * @param principalCollection
	 * @see org.apache.shiro.realm.AuthorizingRealm#clearCachedAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	public void clearCachedAuthorizationInfo(
			PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
}
