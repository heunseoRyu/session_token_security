package com.yours.members.dto;

import com.yours.members.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;
    private String content;
    private String fileName;

    public Article toEntity(){
        return Article.builder()
                .content(content)
                .title(title)
                .fileName(fileName)
                .build();
    }

}