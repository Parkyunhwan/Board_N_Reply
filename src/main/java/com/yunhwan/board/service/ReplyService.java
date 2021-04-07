package com.yunhwan.board.service;

import com.yunhwan.board.dto.ReplyDTO;
import com.yunhwan.board.entity.Board;
import com.yunhwan.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    List<ReplyDTO> getList(Long bo); // 게시물에 딸린 모든 댓글 리스트 반환

    void modify(ReplyDTO replyDTO); // 수정 정보

    void remove(Long rno); //삭제할 댓글 id


    // dto -> entity 변환
    default Reply dtoToEntity(ReplyDTO replyDTO) {
        Board board = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder().board(board)
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .rno(replyDTO.getRno())
                .build();

        return reply;
    }

    // entity -> dto 변환
    default ReplyDTO entityToDto(Reply reply) {

        ReplyDTO replyDTO = ReplyDTO.builder()
                .bno(reply.getBoard().getBno())
                .replyer(reply.getReplyer())
                .rno(reply.getRno())
                .text(reply.getText())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();

        return replyDTO;
    }

}
