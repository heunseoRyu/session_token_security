package com.yours.members.controller;

import com.yours.members.dto.AddMemberRequest;
import com.yours.members.repository.MemberRepository;
import com.yours.members.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth") //auth 주소를 가로챔
public class AuthController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/join")
    public String join( AddMemberRequest request) { //join.html 을 사용하는곳에 모두.
        return "join";
    } //ui 렌더링

    @PostMapping("/join")
    public String join(
            @Valid AddMemberRequest request,
            BindingResult result //@Valid BindingResult 는 꼭 붙어있어야해.
            ) {
        if (result.hasErrors()) { //valid 에 이러가 없으면
            return "join";
        }
        try {
            memberService.addMember(request);
        }catch (DataIntegrityViolationException e) {
            result.reject("duplicated", "사용중인 Username입니다.");
            return "join";
        }catch (Exception e) {
            result.reject("error", e.getMessage());
            return "join";
        }
        return "redirect:/"; //index
    } //db에 추가

}

//unique는 유효성 체크에 포함x, 디비 문제
