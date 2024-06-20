package kr.ac.kopo.guestbook0418.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import kr.ac.kopo.guestbook0418.Entity.Guestbook;
import kr.ac.kopo.guestbook0418.Entity.QGuestbook;
import kr.ac.kopo.guestbook0418.Repository.GuestbookRepository;
import kr.ac.kopo.guestbook0418.dto.GuestbookDTO;
import kr.ac.kopo.guestbook0418.dto.PageRequestDTO;
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
        public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
            Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
//            검색 기능 추가
            BooleanBuilder booleanBuilder = getSearch(requestDTO); // where 절의 조건식
            Page<Guestbook> result = repository.findAll(booleanBuilder, pageable); // select 되는 부분(select문을 만듦) => 조건식이 포함된 select문이 생성됨. oracleDB에 연결을 시킴
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

        @Override
        public BooleanBuilder getSearch(PageRequestDTO requestDTO) {
            String type = requestDTO.getType();
            String keyword = requestDTO.getKeyword();

            BooleanBuilder booleanBuilder = new BooleanBuilder();
            QGuestbook qGuestbook = QGuestbook.guestbook;
            BooleanExpression booleanExpression = qGuestbook.gno.gt(0L); // gno > 0 이라는 조건만 생성

            booleanBuilder.and(booleanExpression);

//            화면에서 검색 조건을 선택하지 않은 경우, 검색 타입(갑이 null일때) 및 검색어(검색어 입력이 안되어있을때 => 검색어의 길이를 알면 입력 여부 확인 가능)
            if(type == null || keyword.trim().length() == 0 || type.trim().length() == 0){
                return booleanBuilder;
            }

//          검색 조건 작성
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            if(type.contains("t")){
                conditionBuilder.or(qGuestbook.title.contains(keyword));
            }
            if(type.contains("c")){
                conditionBuilder.or(qGuestbook.content.contains(keyword));
            }
            if(type.contains("w")){
                conditionBuilder.or(qGuestbook.writer.contains(keyword));
            }

            booleanBuilder.and(conditionBuilder);

            return booleanBuilder;
        }


    }