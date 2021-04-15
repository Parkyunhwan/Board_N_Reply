package com.yunhwan.board.service;

import com.yunhwan.board.dto.BoardDTO;
import com.yunhwan.board.dto.PageRequestDTO;
import com.yunhwan.board.dto.PageResultDTO;
import com.yunhwan.board.entity.Board;
import com.yunhwan.board.entity.Member;
import com.yunhwan.board.repository.BoardRepository;
import com.yunhwan.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository repository;

    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);

        Board board = dtoToEntity(dto);

        //System.out.println("DDD register" + board);

        repository.save(board);

        return board.getBno();
    }

    /**
     * 목록이 아닌 보드 하나의 정보 가져올 때
     * @param bno
     * @return
     */
    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);
        Object[] arr = (Object[])result;

        // 각각의 객체로 반환
        return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
    }

    /**
     * 보드 목록을 가져오는 메서드
     * @param pageRequestDTO
     * @return PageRequestDTO
     */
    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO
                                                             pageRequestDTO) {
        log.info(pageRequestDTO);

        // 사용할 Function 정의 fn
        // f(인자값, 반환값)
        Function<Object[], BoardDTO> fn = (en ->
                entityToDTO((Board)en[0],(Member)en[1],(Long)en[2]));

        // 검색 조건 없이 단순 목록 조회시
        //        Page<Object[]> result = repository.getBoardWithReplyCount(
    //                     pageRequestDTO.getPageable(Sort.by("bno").descending())); // page, size, order 설정

        // 검색 조건이 추가된 조회
        Page<Object[]> result = repository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())  );
        // DB 조회해온 것을 fn 으로 실행해서 PageResultDTO 값을 채워 반환.
        return new PageResultDTO<>(result, fn);
    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {

        Board board = repository.getOne(boardDTO.getBno());
            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());

            repository.save(board); // 변경 감지로 save하지 않아도 update 쿼리 날라감.

    }

    @Override
    @Transactional
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);

        repository.deleteById(bno);
    }
}