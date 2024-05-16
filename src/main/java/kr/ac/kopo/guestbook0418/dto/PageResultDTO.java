package kr.ac.kopo.guestbook0418.dto;

import kr.ac.kopo.guestbook0418.Entity.Guestbook;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// View 계층에서 화면에 출력하기 위해 필요한 정보들이 저장되는 클래스
@Data
public class PageResultDTO<DTO, EN> {
//    화면에 보여질 글 목록: GuestbookDTO 객체 참조값들이 저장된 리스트
    private List<DTO> dtoList;
    
//    전체 페이지수
    private int totalPage;
    
//    글 목록에 보여지는 전체 페이지 번호
    private int page;
    
//    한페이지에 보여지는 글목록 개수
    private int size;
    
//    한 화면에 아래쪽에 보여질 시작페이지 번호
    private int start;

//    한 화면에 아래쪽에 보여질 마지막페이지 번호
    private int end;

//    한 화면의 화면을 바꿔줄 이전, 다음 링크가 보여지거나 보이지않게 설정할 때 필요한 함수
    private boolean prev, next;

//    한 화면에 보여질 페이지 번호 목록이 저장되어있다
    private List<Integer> pageList; 

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
//      매개변수로 전달받은 결과형들과 Entity를 DTO로 변환한 fn을 사용해서 GuestbookDTO 객체를 저장한 리스트
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages(); // 301개의 행을 갖고 있다면 전체페이지수는 31페이지이다.

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        // 현재 화면에 보여질 임시 마지막 페이지 번호
        int tempEnd;
        tempEnd = (int)(Math.ceil(page/10.0)) * 10;

        start = tempEnd = 9;

        // 설정조건연산자에서 조건식 true면 마지막 화면이 아닌경우, false면 마지막화면이라는 의미
        // 전체페이지번호가 31일때: 마지막 화면이 아닌경우 1 ~ 3번째 화면(10, 20, 30), 마지막 화면은 4번째 화면을 의미(31)
        end = totalPage > tempEnd ? tempEnd: totalPage;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        prev = start > 1; // 2번째 부터 마지막 화면까지 true
        next = totalPage > tempEnd; // 첫 번째부터 마지막 바로 전 화면까지 true
    }


}
