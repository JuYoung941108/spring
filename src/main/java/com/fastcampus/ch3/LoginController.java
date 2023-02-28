package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired UserDao userDao;

    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 1. 세션 종료
        return "redirect:/"; // 2. 홈으로 이동
    }

    @PostMapping("/login")
    public String login(String id, String pwd, String toURL, boolean rememberId, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1. id, pwd 확인
        if(!loginCheck(id, pwd)) {
            // 2-1. id, pwd가 일치하지 않으면 loginForm으로 이동
            String msg= URLEncoder.encode("id 혹은 pwd가 일치하지 않습니다.","utf-8");
            return "redirect:/login/login?msg="+msg;
        }
        // 2-2. id와 pwd가 일치하면
        HttpSession session=request.getSession(); // 세션 객체를 얻어서
        session.setAttribute("id",id); // 세션 객체에 id를 저장한다.

        if(rememberId) {
            // 1. 쿠키 생성
            Cookie cookie=new Cookie("id",id);
            // 2. 응답에 저장
            response.addCookie(cookie);
        } else {
            // 1. 쿠키 삭제
            Cookie cookie=new Cookie("id",id);
            // 2. 응답에 저장
            cookie.setMaxAge(0);
        }
        toURL=toURL==null||toURL.equals("")?"/":toURL;
        return "redirect:"+toURL;
    }

    private boolean loginCheck(String id, String pwd) {
        User user=userDao.selectUser(id);

        if(user==null) return false;

        return user.getPwd().equals(pwd);
//        return "admin".equals(id)&&"1234".equals(pwd);
    }
}
