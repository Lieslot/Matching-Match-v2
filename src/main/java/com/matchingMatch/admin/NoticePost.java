package com.matchingMatch.admin;

import com.matchingMatch.match.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String title;
    @NotNull
    private String content;


    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public NoticePost() {

    }
}
