package com.cos.book.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    public void save_테스트(){
        log.info("save_테스트() 시작====================================");
    }
}
