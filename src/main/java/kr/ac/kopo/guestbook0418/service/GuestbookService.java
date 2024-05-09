package kr.ac.kopo.guestbook0418.service;

import kr.ac.kopo.guestbook0418.Entity.Guestbook;
import kr.ac.kopo.guestbook0418.dto.GuestbookDTO;

public interface GuestbookService {
    Long register(GuestbookDTO dto);

    default Guestbook dtoToEntity(GuestbookDTO dto){

        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }
}