package com.yours.members.service;

import com.yours.members.domain.Article;
import com.yours.members.dto.AddArticleRequest;
import com.yours.members.dto.ArticleListViewResponse;
import com.yours.members.dto.ArticleResponse;
import com.yours.members.dto.UpdateArticleRequest;
import com.yours.members.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElse(null);
        if(article !=null)
            article.update(request.getTitle(), request.getContent());
        return article;
    }

    public List<ArticleResponse> findAllArticles(){
        return blogRepository.findAll().stream().map(ArticleResponse::new).toList();

    }
    public ArticleResponse findArticle(Long id){
        Article article = blogRepository.findById(id)
                .orElse(null);
        return article!=null? new ArticleResponse(article):null;
    }
    public void deleteById(Long id){
        blogRepository.deleteById(id);
    }

    public List<ArticleListViewResponse> getArticles() {
        return blogRepository.findAll().stream().map(ArticleListViewResponse::new).toList();
    }



}
