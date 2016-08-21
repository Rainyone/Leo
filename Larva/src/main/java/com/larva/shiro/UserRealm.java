package com.larva.shiro;

import com.larva.model.Account;
import com.larva.service.IAccountService;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/** */
public class UserRealm extends AuthorizingRealm {
    private IAccountService accountService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	String userId = principals.getPrimaryPrincipal().toString();
        SimpleAuthorizationInfo authorizationInfo = accountService.getAccountRolePermission(userId);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        Account account = accountService.getAccountByAccount(username);
        if (account == null) {
            throw new UnknownAccountException();
        }
        String accountPassword = account.getPassword();
        if (!password.equals(accountPassword)) {
            throw new IncorrectCredentialsException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(account.getId(), password, getName());
        return authenticationInfo;
    }

    public IAccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }
}