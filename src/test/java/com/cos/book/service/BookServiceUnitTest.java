package com.cos.book.service;

import com.cos.book.domain.BookRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
/*
* bookRepository만 필요함 => 가짜 객체로 만들 수 있음  모키토 익스텐션
*
* */
// 단위 테스트 (Service에 필요한것만 땡겨와야함
@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {
    @InjectMocks //bookService mock메모리에 뜰때 해당 파일에 @Mock로 등록된 모든 애들을 주입 받는다.
    private BookService bookService;
    @Mock// spring ioc에 들어오는게 아니라 모키토 메모리 공간에 들어옴
    private BookRepository bookRepository;


}
