package kr.ac.kopo.guestbook0418.service;

import kr.ac.kopo.guestbook0418.Entity.Guestbook;
import kr.ac.kopo.guestbook0418.Repository.GuestbookRepository;
import kr.ac.kopo.guestbook0418.dto.GuestbookDTO;
import kr.ac.kopo.guestbook0418.dto.PageReguestDTO;
import kr.ac.kopo.guestbook0418.dto.PageResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

    @Service
    @RequiredArgsConstructor
    public class GuestbookServiceImpl implements GuestbookService{

        private final GuestbookRepository repository; // 의존성 자동 주입을 사용하려면 final을 작성해줘야 쿼리문 등등 주입 가능.
        @Override
        public Long register(GuestbookDTO dto){
            Guestbook entity = dtoToEntity(dto);

            repository.save(entity);

            return entity.getGno();
        }

        @Override
        public PageResultDTO<GuestbookDTO, Guestbook> getList(PageReguestDTO reguestDTO) {
            Pageable pageable = reguestDTO.getPageable(Sort.by("gno").descending());
            Page<Guestbook> result = repository.findAll(pageable);
            Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

            return new PageResultDTO<>(result, fn);
        }

        @Override
        public GuestbookDTO read(Long gno) {
            Optional<Guestbook> result = repository.findById(gno);

            return result.isPresent()? entityToDto(result.get()) : null;
        }

        @Override
        public void modify(GuestbookDTO dto){
            Optional<Guestbook> result = repository.findById(dto.getGno());

            if(result.isPresent()){
                Guestbook entity = result.get();

                entity.changeTitle(dto.getTitle());
                entity.changeContent(dto.getContent());

                repository.save(entity); // 글의 제목과 내용을 update문 실행
            }
        }

        @Override
        public void remove(Long gno) {
            repository.deleteById(gno);
        }
    }