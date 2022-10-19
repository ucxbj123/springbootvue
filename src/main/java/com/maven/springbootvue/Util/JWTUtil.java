package com.maven.springbootvue.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JWTUtil {

    private static final Logger log = LoggerFactory.getLogger(JWTUtil.class);

    // 过期时间5分钟
    private static final long EXPIRE_TIME = 30 * 1000;

    /**
     * 生成签名,5min后过期
     *
     * @param userID 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String userID, String usertype, String secret) {
        //System.currentTimeMillis()获取当前时间以毫秒为单位
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //加密密码
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带userID，usertype信息
        return JWT.create()
                .withClaim("userID", userID)
                .withClaim("usertype",usertype)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static Boolean verify(String token, String userID, String secret, String usertype) {
        Algorithm algorithm = Algorithm.HMAC256(secret);//对密码进行加密
        //创建解析对象，使用的算法和secre要与创建token时保持一致
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("userID", userID)
                .withClaim("usertype",usertype)
                .build();
        //解析指定的token，根据设置好的超时时间和用户信息进行校验，若都能匹配则会正常执行；若有一项不对则会直接抛出异常，返回false
        try {
            DecodedJWT jwt = verifier.verify(token);
            log.info("token校验成功");
            return true;
        }catch (Exception e){
            log.warn("token校验失败，token非法！！");
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUserID(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userID").asString();
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户类型
     */
    public static String getUsertype(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("usertype").asString();
    }

}
