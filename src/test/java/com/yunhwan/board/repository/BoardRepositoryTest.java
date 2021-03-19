package com.yunhwan.board.repository;

import com.yunhwan.board.entity.Board;
import com.yunhwan.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
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

    @Test
    public void testReadWithWriter() {

        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[])result;

        System.out.println("-------------------------------");
        System.out.println(Arrays.toString(arr));
        // result
        // [Board(bno=100, title=Title...100, content=Content....100), Member(email=user100@aaa.com, password=1111, name=USER100)]
    }

    @Test
    public void testGetBoardWithReply() {

        // list 형태로 받아옴
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
        /* result (보드 100 에 달린 모든 댓글들)
        [Board(bno=100, title=Title...100, content=Content....100), Reply(rno=119, text=Reply......119, replyer=guest)]
        [Board(bno=100, title=Title...100, content=Content....100), Reply(rno=180, text=Reply......180, replyer=guest)]
        [Board(bno=100, title=Title...100, content=Content....100), Reply(rno=196, text=Reply......196, replyer=guest)]
        [Board(bno=100, title=Title...100, content=Content....100), Reply(rno=261, text=Reply......261, replyer=guest)]
         */
    }


    @Test
    public void testWithReplyCount(){

        Pageable pageable = PageRequest.of(3,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {

            Object[] arr = (Object[])row;

            System.out.println(Arrays.toString(arr));
        });
    }

    // 100 보드 정보와 작성자 정보와, 댓글의 갯수를 가져오는 테스트
    @Test
    public void testRead3() {
        Object result = boardRepository.getBoardByBno(100L);
        Object[] arr = (Object[])result;
        System.out.println(Arrays.toString(arr));
    }
}