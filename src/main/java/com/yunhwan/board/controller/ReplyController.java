package com.yunhwan.board.controller;

import com.yunhwan.board.dto.ReplyDTO;
import com.yunhwan.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 댓글 컨트롤러의 모든 리턴값은 json을 기본으로 반환
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno) {
        log.info("bno: " + bno);

        // ResponseEntity를 통해 (댓글 리스트 + 상태 코드) 반환
        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO) {
        log.info(replyDTO);

        Long rno = replyService.register(replyDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
        log.info("RNO:" + rno);

        replyService.remove(rno);

        // 상태 메시지 + 상태코드 반환
       return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO) {
        log.info(replyDTO);

        replyService.modify(replyDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
