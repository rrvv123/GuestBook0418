package kr.ac.kopo.guestbook0418.Controller;

import kr.ac.kopo.guestbook0418.dto.GuestbookDTO;
import kr.ac.kopo.guestbook0418.dto.PageRequestDTO;
import kr.ac.kopo.guestbook0418.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@RequiredArgsConstructor
//@Log4j // 로그가 제대로 실행이 안될 때 확인을 할 수 있게 해주는 역할. 그때 필요한 어노테이션
public class GuestbookController {
    private final GuestbookService service;

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping({"/list"})
    public void list(PageRequestDTO pageRequestDTO, Model model){
//        list.html(View 계층)에 방명록 목록과 화면에 보여질 때 필요한 페이지 번호들 등의 정보를 전달하기 위한 것이다.
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping({"/register"})
    public void register(){

    }

    @PostMapping({"/register"})
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
        Long gno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "modify"})
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto", dto);
    }

    @PostMapping({"/remove"})
    public String remove(Long gno, RedirectAttributes redirectAttributes){
        service.remove(gno);
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping({"/modify"})
    public String modify(GuestbookDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){

        service.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("gno", dto.getGno());


        return "redirect:/guestbook/read";
    }
}