package kr.ac.kopo.guestbook0418.service;

import kr.ac.kopo.guestbook0418.Entity.Guestbook;
import kr.ac.kopo.guestbook0418.dto.GuestbookDTO;
import kr.ac.kopo.guestbook0418.dto.PageReguestDTO;
import kr.ac.kopo.guestbook0418.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GuestServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister(){

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("등록 연습 title 1")
                .content("등록 연습 content 1")
                .writer("등록 연습 writer 1")
                .build();

        service.register(guestbookDTO);
    }
    @Test
    public void testList(){
        PageReguestDTO pageReguestDTO = PageReguestDTO.builder()
                .page(5)
                .size(10)
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageReguestDTO);
        List<GuestbookDTO> list = resultDTO.getDtoList();

        System.out.println("start: " + resultDTO.getStart());
        System.out.println("end: " + resultDTO.getEnd());
        System.out.println("previous: " + resultDTO.isPrev());
        System.out.println("next: " + resultDTO.isNext());

        for (GuestbookDTO guestbookDTO : list){
            System.out.println(guestbookDTO);
        }

        for (Integer pageNum : resultDTO.getPageList()){
            System.out.println(pageNum.intValue());
        }
    }
}
