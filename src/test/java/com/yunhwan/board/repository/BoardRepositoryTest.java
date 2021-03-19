package com.yunhwan.board.repository;

import com.yunhwan.board.entity.Board;
import com.yunhwan.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {

        IntStream.rangeClosed(1,100).forEach(i -> {

            // 1 - 100 각각의 멤버가 게시글 하나씩을 작성한다.
            Member member = Member.builder().email("user"+i +"@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..."+i)
                    .content("Content...." + i)
                    .writer(member) // 멤버를 넣어준다!!!
                    .build();

            boardRepository.save(board);

        });

    }

    @Test
    @Transactional
    public void testRead1() {

        Optional<Board> result = boardRepository.findById(100L); //데이터베이스에 존재하는 번호



        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());

    }
}