package com.example.logintest.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Date;
@Component
public class Token_Mg {
    private static final long EXPIRE_TIME= 10*60*1000;  //有效期10min，单位为毫秒
    private static final String TOKEN_SECRET="test";  //密钥盐
    @Autowired
    User_Mg myUser_mg;
    public static String sign(String UserId){
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userAccount", UserId)
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("生成token:"+token);
        return token;
    }
    /**
     * 签名验证
     * @param token
     * @return
     */
    public static String verify(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            String userid=jwt.getClaim("userAccount").asString();
            System.out.println("认证通过：");
            System.out.println("userAccount: " + userid);
            System.out.println("过期时间：      " + jwt.getExpiresAt());
            return userid;
        } catch (Exception e){
            return null;
        }
    }
}

