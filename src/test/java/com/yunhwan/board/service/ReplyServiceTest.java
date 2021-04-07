package com.yunhwan.board.service;

import com.yunhwan.board.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    ReplyService replyService;

    @Test
    public void getList_테스트() throws Exception {
        //given
        Long bno = 90L;
        //when
        List<ReplyDTO> list = replyService.getList(bno);
        //then
        list.forEach(replyDTO -> System.out.println(replyDTO));
    }
}