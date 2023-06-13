package com.cos.book.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) //가짜 db로 테스트 ReplaceNone은 실제 db로 테스트
@DataJpaTest //jpa관련된 애만 뜬다. IOC에 등록되어있음 (@ExtendWith({SpringExtension.class})) 있음(spring환경)
public class BookRepositoryUnitTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void save_테스트(){
        //given
        Book book = new Book(null,"책제목1","저자1");

        //when
        Book bookEntity = bookRepository.save(book);

        assertEquals("책제목1",bookEntity.getTitle());
    }
}
