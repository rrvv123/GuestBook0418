package kr.ac.kopo.guestbook0418.repositoty;

import kr.ac.kopo.guestbook0418.Entity.Guestbook;
import kr.ac.kopo.guestbook0418.Repository.GuestbookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title====" + i)
                    .content("Content====" + i)
                    .writer("Writer" + (i % 10 + 1))
                    .build();
            guestbookRepository.save(guestbook);
        });
    }

    @Test
    public void updateTest(){
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        if(result.isPresent()){
            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title .....");
            guestbook.changeContent("Changed Content .....");

            guestbookRepository.save(guestbook);
        }
    }
}
