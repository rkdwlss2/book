package com.cos.book.service;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor // final 붙어있는 constructure자동 생성
@Service
public class BookService {
    // 함수 => 송금() -> 레파지토리에 여러개의 함수 실행 -> commit or rollback
    private final BookRepository bookRepository;

    @Transactional // 서비스 함수가 종료될때 commit 할지 rollback할지 트랜잭션 관리 하는거임
    public Book 저장하기(Book book){
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true) // JPA변경감지라는 내부 기능 정합성, insert시에는 정합성 막을수 있더ㅏ.(팬텀 현상)
    public Book 한건가져오기(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!"))
                ;
    }

    public List<Book> 모두가져오기(){
        return bookRepository.findAll();
    }

    @Transactional
    public Book 수정하기(Long id,Book book){

        //더티체킹 update치기
        Book bookEntity = bookRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!"))
                ; //영속화
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        return bookEntity;
    }

    public String 삭제하기(Long id){
        bookRepository.deleteById(id);
        return "ok";
    }
}
