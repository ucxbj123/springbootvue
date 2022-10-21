package com.maven.springbootvue.Shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author 谢秉均
 * @description 重写获取token
 * @date 2022/10/21--14:14
 */
public class JWTToken implements AuthenticationToken {
    //密钥，在这里是表示用JWT技术生成的口令
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
