package com.yunhwan.board.repository;

import com.yunhwan.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 엔티티 내부에 연관관계가 설정되어 있는 경
    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    //엔티티 내부에 연관관계가 설정되어 있지 않다면 ON을 통해 명시적으로 작성해야만 한다.
    @Query("SELECT b, r from Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    /**
     * 목록화면 JPQL 쿼리
     * 필요한 것 : (게시물 번호, 제목, 작성시간 // 회원이름, 회원이메일 // 게시물의 댓글 수)
     * 게시물의 데이터를 가장 많이 가져오므로 게시물 중심으로 조인관계 작
     * @param pageable //page 처리이므로 파라미터 pageable로 받는다.
     * @return
     */
    @Query(value = "SELECT b, w, count(r)" +
            " FROM Board b" +
            " LEFT JOIN b.writer w" + // 연관 관계 있는 것은 on 없어도 됨.
            " LEFT JOIN Reply r ON r.board = b" + // 없는 것은 on 이 필요
            " group by b",
            countQuery = "select count(b) from Board b") // countQuery를 따로 만들지 않으면 조인이 발생하면서 countQuery를 수행하게 된다.
    Page<Object[]> getBoardWithReplyCount(Pageable pageable); // limit와 order by 는 pageable로 넘어온 파라미터가 결정한다.

    @Query("SELECT b, w, count(r) " +
            " FROM Board b " +
            " LEFT JOIN b.writer w " +
            " LEFT OUTER JOIN Reply r ON r.board = b" +
            " WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}