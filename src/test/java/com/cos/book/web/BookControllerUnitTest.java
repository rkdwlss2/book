package com.cos.book.web;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

// 실제 서버에서테스트 해볼때 완벽하게 돌아가는지 확인 불가 전체가 안뜨니깐 가볍다.
// @ExtendWith({SpringExtension.class}) 스프링으로 확장하려면 무조건 써야한다. junit4에서는 ExtendWith이 WebMvcTest에 업섰는뎅 5부터는 들어왔다.
// Controller, filter,Advice만 올라간다.
@WebMvcTest
public class BookControllerUnitTest {

}
