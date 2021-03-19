package com.yunhwan.board.repository;

import com.yunhwan.board.entity.Board;
import com.yunhwan.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() throws Exception {
        //given
        IntStream.rangeClosed(1, 300).forEach(i -> {
            long bno = (long)(Math.random() * 100) + 1; // 1 ~ 100 사이의 번호

            // 랜덤하게 보드와 댓글 생성
            Board board = Board.builder().bno(bno).build(); // test에선 bno그냥 넣어도..

            Reply reply = Reply.builder()
                    .text("Reply......" + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });
        //when

        //then
    }

    @Test
    public void readReply1() throws Exception {
        //given
        Optional<Reply> result = replyRepository.findById(1L);
        //when
        Reply reply = result.get();
        //then
        System.out.println(reply);
        System.out.println(reply.getBoard());
    }
}