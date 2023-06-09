package com.cos.book.web;

import com.cos.book.domain.Book;
import com.cos.book.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 실제 서버에서테스트 해볼때 완벽하게 돌아가는지 확인 불가 전체가 안뜨니깐 가볍다.
// @ExtendWith({SpringExtension.class}) 스프링으로 확장하려면 무조건 써야한다. junit4에서는 ExtendWith이 WebMvcTest에 업섰는뎅 5부터는 들어왔다.
// Controller, filter,Advice만 올라간다.
@Slf4j
@WebMvcTest
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean //ioc환경에 bean 등록됨 가짜를 올림,
    private BookService bookService;

    //BDDMockito 패턴 given when then
    @Test
    public void save_테스트() throws Exception {
        //given (테스트를 하기 위한 준비)
        Book book = new Book(null,"스프링따라하기","코스");
        String content = new ObjectMapper().writeValueAsString(book);
        //when stuff 미리 행동을 지정함
        when(bookService.저장하기(book)).thenReturn(new Book(1L,"스프링따라하기","코스"));

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
        books.add(new Book(1L,"스프링부트 따라하기","코스"));
        books.add(new Book(2L,"리액트 따라하기","코스"));

        when(bookService.모두가져오기()).thenReturn(books);
        //when
        ResultActions resultAction = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        //then
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].title").value("스프링부트 따라하기"))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    public void findById_테스트() throws Exception {
        //given
        Long id = 1L;
        when(bookService.한건가져오기(id)).thenReturn(new Book(1L,"자바 공부하기","쌀"));        //when

        //when
        ResultActions resultAction = mockMvc.perform(get("/book/{id}",id)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("자바 공부하기"))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    public void update_테스트() throws Exception {
        //given

        Long id = 1L;
        Book book = new Book(null,"C++ 따라하기","코스");
        String content = new ObjectMapper().writeValueAsString(book);
        //when stuff 미리 행동을 지정함
        when(bookService.수정하기(id,book)).thenReturn(new Book(1L,"C++ 따라하기","코스"));

        //when
        ResultActions resultAction = mockMvc.perform(put("/book/{id}",id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        //then
        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("C++ 따라하기"))
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    public void delete_테스트() throws Exception {
        //given

        Long id = 1L;
        //when stuff 미리 행동을 지정함
        when(bookService.삭제하기(id)).thenReturn("ok");

        //when
        ResultActions resultAction = mockMvc.perform(delete("/book/{id}",id)
                .accept(MediaType.TEXT_PLAIN)
        );

        //then
        resultAction
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        MvcResult requestResult = resultAction.andReturn();
        String result = requestResult.getResponse().getContentAsString();

        assertEquals("ok",result);
    }
}


