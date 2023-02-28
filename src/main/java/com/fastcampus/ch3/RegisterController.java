package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired UserDao userDao;

    final int FAIL=0;

    @InitBinder
    public void toDate(WebDataBinder binder) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
        binder.setValidator(new UserValidator()); // UserValidator를 WebDateBinder의 로컬 validator로 등록
    }

    @GetMapping("/add")
    public String register() {
        return "registerForm"; // WEB-INF/views/registerForm.jsp
    }

    @PostMapping("/add")
    public String save(@Valid User user, BindingResult result, Model m) throws Exception {
        System.out.println("result: "+result);
        System.out.println("user: "+user);

        // User 객체를 검증한 결과에 에러가 있으면 registerForm을 이용해 에러를 보여줘야 함
        if(!result.hasErrors()) {
        // database에 신규 회원정보를 저장
        int rowCnt=userDao.insertUser(user);

        if(rowCnt!=FAIL)
            return "registerInfo";
        }
        return "registerForm";
    }

    private boolean isValid(User user) {
        return true;
    }
}
