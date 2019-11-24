package life.majiang.community.controller;

import life.majiang.community.Mapper.UserMapper;
import life.majiang.community.dio.AccessTokenDTO;
import life.majiang.community.dio.GithubUser;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvaider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Resource
    private GithubProvaider githubProvaider;

    @Value("${github.client.id}")
    private String clienId;
    @Value("${github.client.secret}")
    private String clienSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;
    @Resource
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clienId);
        accessTokenDTO.setClient_secret(clienSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setState(state);
        String tokenDTO = githubProvaider.getAccessTokenDTO(accessTokenDTO);
        GithubUser githubUser = githubProvaider.getUser(tokenDTO);
        if (githubUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.insert(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            //登录失败，重新登陆
            return "redirect:/";
        }

    }
}
