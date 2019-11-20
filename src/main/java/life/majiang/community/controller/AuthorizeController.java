package life.majiang.community.controller;

import life.majiang.community.dio.AccessTokenDTO;
import life.majiang.community.dio.GithubUser;
import life.majiang.community.provider.GithubProvaider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.misc.Request;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request)
    {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clienId);
        accessTokenDTO.setClient_secret(clienSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setState(state);
        String tokenDTO = githubProvaider.getAccessTokenDTO(accessTokenDTO);
        GithubUser user = githubProvaider.getUser(tokenDTO);
        if (user != null) {
            //登录成功，写cookie和session
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else {
            //登录失败，重新登陆
            return "redirect:/";

        }

    }
}
