package com.yours.members.controller;


import com.yours.members.domain.Article;
import com.yours.members.dto.AddArticleRequest;
import com.yours.members.dto.ArticleResponse;
import com.yours.members.dto.UpdateArticleRequest;
import com.yours.members.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }
    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        return ResponseEntity.ok()
                .body(blogService.findAllArticles());
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable(name="id") long id){
        ArticleResponse response = blogService.findArticle(id);
        if(response!= null)
            return ResponseEntity.ok()
                    .body(blogService.findArticle(id));
        else
            return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable(name="id") long id){
        blogService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticle(
            @PathVariable(name="id") long id,
            @RequestBody UpdateArticleRequest request
    ){
        Article updatedArticle = blogService.update(id, request);
        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
