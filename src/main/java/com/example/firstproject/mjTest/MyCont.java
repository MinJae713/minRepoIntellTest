package com.example.firstproject.mjTest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyCont {
    // 테스트용 코드
    @GetMapping("/ymj")
    public String ymjMethod1(Model model) {
        // 얜 됨(기본)
        model.addAttribute("nickname", "윰재애ㅐㅐ");
        return "goodbye";
    }

    @GetMapping("/ymj2")
    public String ymjMethod2(Model model) {
        // 얘도 됨(mustache 파일을 다른 디렉토리에 넣은 경우)
        model.addAttribute("nickname", "윰쟁ㅐㅐ");
        return "mjTest/goodbye";
    }

    @GetMapping("/ymj3")
    public void ymjMethod3(Model model) {
        model.addAttribute("nickname", "윰재애ㅐㅐ");
    }
}
