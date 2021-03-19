package com.yunhwan.board.repository;

import com.yunhwan.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {


    @Modifying // delete와 update 쿼리는 항상 @Modifiying 이 들어간다.
    @Query("delete from Reply r where r.board.bno = :bno")
    void deleteByBno(Long bno);
}
