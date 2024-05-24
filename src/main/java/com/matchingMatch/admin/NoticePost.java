package com.matchingMatch.admin;

import com.matchingMatch.match.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class NoticePost extends BaseEntity {


    @Builder
    public NoticePost(Long id, String title, String content) {
        this.id = id;

        this.title = title;
        this.content = content;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Long hits;

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public NoticePost() {

    }
}
