package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service // 서비스 객체 선언
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if (article.getId() != null)
            return null;
        return articleRepository.save(article);
    }

    public Article update(ArticleForm dto, Long id) {
        // 1. DTO -> 엔티티
        Article article = dto.toEntity(); // 새 데이터
        log.info("id: {}, article: {}", id, article.toString());
        // 2. 대상 엔티티 있는지 조회
        Article target = articleRepository.findById(id).orElse(null); // 기존 데이터
        // 3. 잘못 요청 처리
        if (target == null || id != article.getId()) {
            log.info("요청이 잘못되었슈 id: {}, article: {}", id, article.toString());
            return null;
        }
        // 4. 업데이트 및 정상응답(200)
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        // 1. 대상 엔티티 있는지 조회
        Article target = articleRepository.findById(id).orElse(null);
        // 2. 잘못 요청 처리
        if (target == null) {
            return null;
        }
        // 3. 있으믄 삭제 후 정상응답
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dto 묶음 -> 엔티티 묶음으로
        List<Article> articleList = dtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList());
        // 2. 엔티티 묶음을 DB에 저장
        articleList.stream().forEach(article -> articleRepository.save(article));
        // 3. 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(() -> new IllegalArgumentException("결제 실패!"));
        // 4. 결과값 반환
        return articleList;
    }
}
