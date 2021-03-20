package com.leyou.common;

import com.leyou.common.pojo.UserInfo;
import com.leyou.common.utils.JwtUtils;
import com.leyou.common.utils.RsaUtils;
import org.junit.Before;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Date:2021/03_12:28 下午
 * @Description：
 */
public class Test {

    private static final String pubKeyPath = "/Users/luyi/IdeaProjects/leyou/rsa/rsa.pub";  //生成的公钥

    private static final String priKeyPath = "/Users/luyi/IdeaProjects/leyou/rsa/rsa.pri";  //生成的私钥

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @org.junit.Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");    //传入公钥、私钥、salt
    }

    @Before
    public void testGetRsa() throws Exception {     //读取公钥、私钥
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @org.junit.Test
    public void testGenerateToken() throws Exception {      //生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);    //传入userInfo对象、私钥、过期时间
        System.out.println("token = " + token);
    }

    @org.junit.Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTYxNjIxNTUwNH0.EJ76gwAn3gIB3cyyLzmAAPaxMNNuIvIQ3LJwADy33hlMZMeJs_FmZ9zdLbxHMfmvd-3P8wz0E6bSHHv9BHxqoZE8xE-G1XNXRoMlBDTLrWIMLHIpBPAQChdPnRrXXrD_xdHA2RhME0Hx7gd2MJ2GnAOcLWtR_ru7PY2nmsY1pJc";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
