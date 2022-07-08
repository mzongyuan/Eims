package com.eims.common;

import com.eims.entity.system.SystemUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Description Token工具类
 * @Auth zongyuan.ma
 * @Date 2022/7/7 21:39
 * @Version V 1.0.0
 */
public class TokenUtils {

    /**
     * @Description Token生成
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/7 21:40
     * @Version V 1.0.0
     */
    public static String  getoken(SystemUser user) {
        //Jwts.builder()生成
        //Jwts.parser()验证
        JwtBuilder jwtBuilder =  Jwts.builder()
                .setId(user.getUserCode()+"")   // 用户编码
                .setSubject(user.getUserName())    //用户名称
                .setIssuedAt(new Date())    //登录时间
                .signWith(SignatureAlgorithm.HS256, "my-123").setExpiration(new Date(new
                        Date().getTime()+86400000));
        //设置过期时间
        //前三个为载荷playload 最后一个为头部 header
        return  jwtBuilder.compact();
    }

    /**
     * @Description Token解析
     * @Auth zongyuan.ma
     * @Parameter
     * @Return
     * @Date 2022/7/7 21:43
     * @Version V 1.0.0
     */
    public static String tokenToOut(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey("my-123")
                .parseClaimsJws(token)
                .getBody();
        String userCode = claims.getId();
        /*System.out.println("用户code:"+claims.getId());
        System.out.println("用户name:"+claims.getSubject());
        System.out.println("用户time:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                format(claims.getIssuedAt()));System.out.println("过期时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                format(claims.getExpiration()));*/
        return userCode;
    }
}
