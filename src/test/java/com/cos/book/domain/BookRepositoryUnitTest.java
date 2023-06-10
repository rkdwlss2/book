package com.cos.book.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) //가짜 db로 테스트 ReplaceNone은 실제 db로 테스트
@DataJpaTest //jpa관련된 애만 뜬다. IOC에 등록되어있음 (@ExtendWith({SpringExtension.class})) 있음(spring환경)
public class BookRepositoryUnitTest {
    @Autowired
    private BookRepository bookRepository;
}
