package kr.ac.kopo.guestbook0418.service;

import kr.ac.kopo.guestbook0418.Entity.Guestbook;
import kr.ac.kopo.guestbook0418.dto.GuestbookDTO;
import kr.ac.kopo.guestbook0418.dto.PageReguestDTO;
import kr.ac.kopo.guestbook0418.dto.PageResultDTO;

public interface GuestbookService {
    // 글 등록 기능
    Long register(GuestbookDTO dto);

    // getList의 역할: 한 페이지에 보여질 글 목록(GuestbookDTO 객체)이 저장된 List정보를 갖고 있는 PageResultDTO객체 참조값을 반환하는 기능
    PageResultDTO<GuestbookDTO, Guestbook> getList(PageReguestDTO reguestDTO);

    GuestbookDTO read(Long gno);
    //글 제목과 내용 수정하는 기능
    void modify(GuestbookDTO dto);

    //글 삭제 기능
    void remove(Long gno);

    default Guestbook dtoToEntity(GuestbookDTO dto){

        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }

    default GuestbookDTO entityToDto(Guestbook entity){
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }
}