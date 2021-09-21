package web.spring.boot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.spring.boot.entity.Message;
import web.spring.boot.entity.User;
import web.spring.boot.mapper.UserMapper;
import web.spring.boot.util.RedisUtil;
import web.spring.boot.util.TokenUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;


    public String getToken(HttpServletRequest request,
                           HttpServletResponse response)
    {
        Object sessionToken = request.getSession().getAttribute("token");
        Cookie[] cookies = request.getCookies();
        String cookieToken = null == cookies ? null : Stream.of(cookies)
                .filter(e -> "TOKEN".equals(e.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
        String requestToken = request.getHeader("Authorization");
        String parameterToken = request.getParameter("token");
        if (null != parameterToken)
            return parameterToken;
        if (null != requestToken)
            return requestToken;
        if (null != sessionToken)
            return String.valueOf(sessionToken);
        return cookieToken;
    }

    public void removeToken(HttpServletRequest request,
                            HttpServletResponse response,
                            String token)
    {
        RedisUtil.delete("token=" + token);
        request.getSession().removeAttribute("token");
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null == cookies ? null : Stream.of(cookies)
                .filter(e -> "TOKEN".equals(e.getName()))
                .findFirst()
                .orElse(null);
        if (null != cookie) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    public void saveToken(HttpServletRequest request,
                          HttpServletResponse response,
                          String token)
    {
        RedisUtil.opsForValue().setIfAbsent("token=" + token, "1");
        request.getSession().setAttribute("token", token);
        Cookie cookie = new Cookie("TOKEN", token);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

    /**
     * 用户登陆
     * @param request 请求
     * @param response 响应
     * @param username 用户名称
     * @param password 用户密码
     * @return 返回响应消息
     */
    public Message<String> login(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String username,
                                 String password)
    {
        List<User> userList = userMapper.selectUserByName(username);
        if (userList.size() == 0) {
            return Message.create(Message.NOT_FOUND, "", "User not found");
        }
        if (!password.equals(userList.get(0).getPassword())) {
            return Message.create(Message.BAD_REQUEST, "", "User longin fail");
        }
        String token = TokenUtil.create(userList.get(0), 30 * 60);

        saveToken(request, response, token);

        return Message.create(Message.OK, token, "User longin success");

    }


    public Message<String> verify(HttpServletRequest request,
                                  HttpServletResponse response)
    {
        String token = getToken(request, response);

        if (null == token)
            return Message.create(Message.NOT_FOUND, "", "User token not found");

        if (!RedisUtil.hasKey("token=" + token))
            return Message.create(Message.FORBIDDEN, "", "User token invalid");

        return TokenUtil.verify(token);
    }

    public Message<String> logout(HttpServletRequest request,
                                  HttpServletResponse response)
    {
        String token = getToken(request, response);
        if (null != token)
            removeToken(request, response, token);
        return Message.create(Message.OK, "", "User logout success");

    }






}
