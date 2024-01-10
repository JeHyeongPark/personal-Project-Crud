package kr.co.crud.controller;

import kr.co.crud.domain.UserVO;
import kr.co.crud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class UserController {



    @Autowired
    private UserService userService;

    /* 로그인 페이지 */
    @GetMapping("/user/login")
    public String loginForm(){
        return "/user/login";
    }


    /* 로그인 버튼 */
    @PostMapping("/user/login")
    public String login(){
        return "redirect:/";
    }




    /* 회원 가입 */

    @GetMapping("/user/register")
    public String registerview(){
        return "/user/register";
    }


    /* 회원 가입 버튼 */
    @PostMapping("/user/register")
    public ResponseEntity<UserVO> register(@RequestBody UserVO userVO) {

        log.warn("controller");

        userService.register(userVO);
        return ResponseEntity.ok(userVO);
    }




}
