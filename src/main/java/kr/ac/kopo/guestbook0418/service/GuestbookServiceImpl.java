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
}