package com.yunhwan.board.service;

import com.yunhwan.board.dto.BoardDTO;
import com.yunhwan.board.dto.PageRequestDTO;
import com.yunhwan.board.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    /**
     * 각 게시판 등록 테스트 with dto
     */
    @Test
    public void testRegister() {
        BoardDTO dto = BoardDTO.builder()
                .title("Test.register")
                .content("Test...register")
                .writerEmail("user55@aaa.com") //현재 데이터베이스에 존재하는 회원 이메일
                .build();

        Long bno = boardService.register(dto);
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result =
                boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet() {
        Long bno = 100L;
        BoardDTO boardDTO = boardService.get(bno);
        System.out.println(boardDTO);
    }
}