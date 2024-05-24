package com.matchingMatch.admin.domain;

import com.matchingMatch.admin.NoticePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticePostRepository extends JpaRepository<NoticePost, Long> {

    Page<NoticePost> findAll(Pageable pageable);

}
