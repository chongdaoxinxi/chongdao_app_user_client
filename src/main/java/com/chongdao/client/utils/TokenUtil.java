package com.chongdao.client.utils;


import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.enums.UserStatusEnum;
import com.chongdao.client.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT组成
 * 第一部分我们称它为头部（header),第二部分我们称其为载荷（payload)，第三部分是签证（signature)。
 */

@Slf4j
@Component
public class TokenUtil {
    private static UserRepository userRepository;
    @Autowired
    public TokenUtil(UserRepository userRepository) {
        TokenUtil.userRepository = userRepository;
    }

    public static final long EXPIRATION_TIME = 3000_000_000L; // 1000 hour
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";
    private final String MESSAGE = "message";

    public static String generateToken(Integer userId,String username, Date generateTime) {

        HashMap<String, Object> map = new HashMap<>();
        //可以把任何安全的数据放到map里面
        map.put("username", username);
        map.put("userId",userId);
        map.put("generateTime", generateTime);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(generateTime.getTime() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return jwt;
    }



    /**
     * @param token
     * @return
     */
    public static Map<String,Object> validateToken(String token) {
        Map<String,Object> resp = new HashMap<String,Object>();
        if (token != null) {
            // 解析token
            try {
                Map<String, Object> body = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();
                String username = (String) (body.get("username"));
                Integer userId = (Integer)(body.get("userId"));
                Date generateTime = new Date((Long)body.get("generateTime"));
                if(username == null || username.isEmpty()){
                    resp.put("status", UserStatusEnum.USERNAME_NOT_EMPTY.getStatus());
                    resp.put("message",UserStatusEnum.USERNAME_NOT_EMPTY.getMessage());
                    return resp;
                }
                //账号在别处登录
  /*              if(userRepository.findByName(username).getLastLoginTime().after(generateTime)){
                    resp.put("status",ResultEnum.USER_LOGIN_ALREADY.getStatus());
                    resp.put("message",ResultEnum.USER_LOGIN_ALREADY.getMessage());
                    return resp;
                }*/
                resp.put("userId",userId);
                resp.put("status",ResultEnum.SUCCESS.getStatus());
                resp.put("message",ResultEnum.SUCCESS.getMessage());
                return resp;
            }catch (SignatureException | MalformedJwtException e) {
                // TODO: handle exception
                // don't trust the JWT!
                // jwt 解析错误
                resp.put("status",UserStatusEnum.TOKEN_ERROR.getStatus());
                resp.put("message",UserStatusEnum.TOKEN_ERROR.getMessage());
                return resp;
            } catch (ExpiredJwtException e) {
                // TODO: handle exception
                // jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
                resp.put("status",UserStatusEnum.TOKEN_EXPIRED.getStatus());
                resp.put("message",UserStatusEnum.TOKEN_EXPIRED.getMessage());
                return resp;
            }
        }else {
            resp.put("status",UserStatusEnum.TOKEN_NOT_EMPTY.getStatus());
            resp.put("message",UserStatusEnum.TOKEN_NOT_EMPTY.getMessage());
            return resp;
        }
    }


}
