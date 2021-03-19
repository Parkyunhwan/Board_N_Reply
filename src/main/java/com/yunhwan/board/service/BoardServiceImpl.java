package com.yunhwan.board.service;

import com.yunhwan.board.dto.BoardDTO;
import com.yunhwan.board.dto.PageRequestDTO;
import com.yunhwan.board.dto.PageResultDTO;
import com.yunhwan.board.entity.Board;
import com.yunhwan.board.entity.Member;
import com.yunhwan.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{
    private final BoardRepository repository;

    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);

        Board board = dtoToEntity(dto);

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
        return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO
                                                             pageRequestDTO) {
        log.info(pageRequestDTO);

        // 사용할 Function 정의 fn
        Function<Object[], BoardDTO> fn = (en ->
                entityToDTO((Board)en[0],(Member)en[1],(Long)en[2]));

        Page<Object[]> result = repository.getBoardWithReplyCount(
                pageRequestDTO.getPageable(Sort.by("bno").descending())); // page, size, order 설정

        // DB 조회해온 것을 fn 으로 실행해서 PageResultDTO 값을 채워 반환.
        return new PageResultDTO<>(result, fn);
    }
}