package com.yunhwan.board.repository;

import com.yunhwan.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> { // email이므로 PK String임을 명심하자.
}
