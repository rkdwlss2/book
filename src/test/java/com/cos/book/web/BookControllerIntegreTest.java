package com.cos.book.web;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;
import com.cos.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//통합테스트 (모든 Bean을 똑같이 Ioc에 올리고 테스트하기) 느림
// springBootTest안에도 @ExtendWith({SpringExtension.class})있다.
// WebEnvironment.RANDOM_PORT에서는 실제 톰켓으로 테스트
@Slf4j
@Transactional // 각각 테스트함수가 종료해줄때마다 트랜잭션을 rollback 해주는 어노테이션
@AutoConfigureMockMvc // 이게 있어야 MockMvc를 IOC에 등록해줌(넣어줌)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)// MOCK은 실제 톰켓이 아니라 다른톰켓으로 테스트
public class BookControllerIntegreTest {

    // MockitoLibrary =>
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;



    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init(){
        entityManager.createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1").executeUpdate();
//        entityManager.createNativeQuery("ALTER TABLE book AUTO_INCREMENT = 1").executeUpdate();
//        entityManager.persist(new Book()); // 원래 이렇게 해야함
    }
    //BDDMockito 패턴 given when then
    @Test
    public void save_테스트() throws Exception {
        //given (테스트를 하기 위한 준비)
        Book book = new Book(null,"스프링따라하기","코스");
        String content = new ObjectMapper().writeValueAsString(book);
        //when stuff이 필요없음 실제 repository 실행됨 DB에 진짜 저장됨

        //when
        ResultActions resultAction = mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        //then
        resultAction
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("스프링따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findAll_테스트() throws Exception {
        //given
        List<Book> books = new ArrayList<>();
        books.add(new Book(null,"스프링부트 따라하기","코스"));
        books.add(new Book(null,"리액트 따라하기","코스"));
        books.add(new Book(null,"JUnit 따라하기","코스"));

        bookRepository.saveAll(books);


        //when
        ResultActions resultAction = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        //then
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[2].title").value("JUnit 따라하기"))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    public void findById_테스트() throws Exception {
        //given
        Long id = 2L;

        List<Book> books = new ArrayList<>();
        books.add(new Book(null,"스프링부트 따라하기","코스"));
        books.add(new Book(null,"리액트 따라하기","코스"));
        books.add(new Book(null,"JUnit 따라하기","코스"));
        bookRepository.saveAll(books);

        //when
        ResultActions resultAction = mockMvc.perform(get("/book/{id}",id)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        //then
        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("리액트 따라하기"))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    public void update_테스트() throws Exception {
        //given
        Long id = 3L;

        List<Book> books = new ArrayList<>();
        books.add(new Book(null,"스프링부트 따라하기","코스"));
        books.add(new Book(null,"리액트 따라하기","코스"));
        books.add(new Book(null,"JUnit 따라하기","코스"));
        bookRepository.saveAll(books);

        Book book = new Book(null,"C++ 따라하기","코스");
        String content = new ObjectMapper().writeValueAsString(book);

        //when
        ResultActions resultAction = mockMvc.perform(put("/book/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        //then
        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.title").value("C++ 따라하기"))
                .andDo(MockMvcResultHandlers.print());


    }
}
