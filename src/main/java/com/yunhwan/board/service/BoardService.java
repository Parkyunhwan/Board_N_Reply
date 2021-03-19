package com.yunhwan.board.service;

import com.yunhwan.board.dto.BoardDTO;
import com.yunhwan.board.dto.PageRequestDTO;
import com.yunhwan.board.dto.PageResultDTO;
import com.yunhwan.board.entity.Board;
import com.yunhwan.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    BoardDTO get(Long bno);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);


    // 추상 클래스 없이 공통 메서드를 생성함
    default Board dtoToEntity(BoardDTO dto){
        // 작성자의 이메일로 멤버 객체를 조회해옴..
        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }

    // dto를 만들어주기 위해 보드, 멤버, 댓글 수 (3 가지를 조회해와야만 함)
    default BoardDTO entityToDTO(Board board, Member member, Long replyCount)
    {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) //int로 처리하도록
                .build();
        return boardDTO;
    }
}
