package kr.ac.kopo.guestbook0418.Controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
//@Log4j // 로그가 제대로 실행이 안될 때 확인을 할 수 있게 해주는 역할. 그때 필요한 어노테이션
public class GuestbookController {
    @GetMapping({"/", "/list"})
    public String list(){
        return "/guestbook/list";
    }
}