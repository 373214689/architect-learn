package web.spring.boot.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import web.spring.boot.entity.GenericUser;
import web.spring.boot.entity.Message;
import web.spring.boot.mapper.UserMapper;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Component
public class TokenUtil {

    private static TokenUtil tokenUtil;

    @Value("${jwt.token.issuer:spring-boot}")
    private static String ISSUER;

    @Value("${jwt.token.issuer:1234567890}")
    private static String SECRET_KEY;

    @Autowired
    UserMapper userMapper;

    @PostConstruct
    public void init() {
        tokenUtil = this;
    }

    public static String create(GenericUser user, long expired) {
        return tokenUtil.createJWT(user, expired);
    }

    public static Message<String> verify(String token) {
        return tokenUtil.verifyJWT(token);
    }

    private String createJWT(GenericUser user, long expired) {
        // 加密方式有两种，一种是利用公共密钥加密，一种是利用用户密码加密
        // 用途各有不同，从安全性方向考虑，公共密钥比用户密码加密更不容易暴露，缺点是要需要频繁查询数据库
        // 使用公共密钥的好处是不需要频繁查询数据库，但公共刻密钥暴露后危害较大
        return JWT.create()
                .withAudience(String.valueOf(user.getUserId()))
                .withClaim("username", user.getUsername())
                .withClaim("password", user.getPassword())
                .withSubject(user.getUsername())
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expired))
                .sign(Algorithm.HMAC256(user.getPassword())); // 解密需要用到
    }

    private Message<String> verifyJWT(String token) {
        // 解析公共部分的 USERID
        int userId = -1;
        try {
            List<String> audience = JWT.decode(token).getAudience();
            if (null != audience && audience.size()>0)
                userId = Integer.parseInt(audience.get(0));
        } catch (JWTDecodeException j) {
            return Message.create(Message.FORBIDDEN, null, "User status is forbidden");
        }
        // 从数据库查询 用户
        List<GenericUser> userList = userMapper.selectUserById(userId);

        if (null == userList || userList.size() == 0) {
            return Message.create(Message.NOT_FOUND, null,"User has been not found");
        }

        GenericUser user = userList.get(0);

        // 使用用户密码验证 token
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            return Message.create(Message.UNAUTHORIZED, null, "User unauthorized");
        }

        return Message.create(Message.OK, null,"User verify success");
    }
}
