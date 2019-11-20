package life.majiang.community.controller;

import life.majiang.community.Mapper.UserMapper;
import life.majiang.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")) {
                String token = cookie.getValue();
                User user = userMapper.findbytoken(token);
                if (user != null) {
                    request.getSession().setAttribute("user",user);
                }

                break;
            }
            
        }

        return "index";
    }
}
