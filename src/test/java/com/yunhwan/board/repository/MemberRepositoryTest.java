package com.yunhwan.board.repository;

import com.yunhwan.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers() throws Exception {
        //given

        // 1번 부터 100 번까지 멤버를 만든다.

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .password("1111")
                    .name("USER" + i)
                    .build();
            memberRepository.save(member);
        });
        //when

        //then
    }
}