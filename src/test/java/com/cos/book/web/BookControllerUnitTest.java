package com.cos.book.web;

import com.cos.book.domain.Book;
import com.cos.book.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

// 실제 서버에서테스트 해볼때 완벽하게 돌아가는지 확인 불가 전체가 안뜨니깐 가볍다.
// @ExtendWith({SpringExtension.class}) 스프링으로 확장하려면 무조건 써야한다. junit4에서는 ExtendWith이 WebMvcTest에 업섰는뎅 5부터는 들어왔다.
// Controller, filter,Advice만 올라간다.
@Slf4j
@WebMvcTest
public class BookControllerUnitTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean // ioc환경에 bean등록됨 실제로 동작 안하는 서비스
    private BookService bookService;

    // BDDMockito 패턴
    @Test
    public void save_테스트() throws JsonProcessingException {
        // given (테스트를 하기 위한 준비)
        String content = new ObjectMapper().writeValueAsString(new Book(null,"스프링 따라하기","코스"));
        log.info(content);

//        log.info("save_테스트() 시작====================================");
//        Book book = bookService.저장하기(new Book(null,"제목","코스"));
//        System.out.println("book : "+ book);
    }
}
