package com.maven.springbootvue.Shiro;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author 谢秉均
 * @description 重写获取token
 * @date 2022/10/21--14:14
 */
@NoArgsConstructor
@AllArgsConstructor
public class JWTToken implements AuthenticationToken {
    //密钥，在这里是表示用JWT技术生成的口令
    private String token;
    //
    private Boolean rememberMe;

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



    public boolean isRememberMe() {
        return  rememberMe;
    }


    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

}
