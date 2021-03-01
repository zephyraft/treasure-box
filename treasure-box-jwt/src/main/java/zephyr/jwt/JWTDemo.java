package zephyr.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Created by zephyr on 2020/6/19.
 */
public class JWTDemo {

    public static String createToken(String appId, String appSecret) {
        LocalDateTime now = LocalDateTime.now();
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        LocalDateTime expireTime = now.plusHours(1);
        Date currentDate = Date.from(now.toInstant(zoneOffset));

        try {
            return JWT.create()
                    .withSubject("主题" + appId) // 主题
                    .withIssuedAt(currentDate) // 签发时间
                    .withExpiresAt(Date.from(expireTime.toInstant(zoneOffset))) // 失效时间
                    .withNotBefore(currentDate) // 生效时间
                    .sign(Algorithm.HMAC256(appSecret));
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return "";
    }

    public static boolean verifyToken(String token, String appId, String appSecret) {
        if (token == null || token.equals("")) {
            return false;
        }
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(appSecret))
                    .withSubject("主题" + appId) // 主题
                    .acceptLeeway(1) // 全局 允许时间误差
                    .acceptExpiresAt(5) // 指定 单个时间误差
                    .build(); //Reusable verifier instance
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
        }
        return false;
    }

    public static void main(String[] args) {
        String appId = AppIdDemo.getAppId();
        final String appSecret = AppIdDemo.getAppSecret(appId);
        System.out.println(appId);
        System.out.println(appSecret);
        System.out.println(appSecret.length());

        final String token = JWTDemo.createToken(appId, appSecret);
        System.out.println(token);
        final boolean result = JWTDemo.verifyToken(token, appId, appSecret);
        System.out.println(result);
    }
}
