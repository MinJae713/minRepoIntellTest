package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());
//        System.out.println(form);
        // 1. DTO --> Entity
        Article article = form.toEntity();
//        System.out.println(article);
        log.info(article.toString());
        // 2. Repository로 Entity를 DB에 저장
        Article saved = articleRepository.save(article);
//        System.out.println(saved);
        log.info(saved.toString());
        return "redirect:/articles/" + saved.getId(); // 리다이렉트
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        // 1. id를 갖고 데이터 조회
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);
        // 2. 모델에 데이터 등록
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);
        // 3. 뷰 페이지 반환
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model) {
        // 1. DB에서 모든 Article 반환
        List<Article> articleEntityList = articleRepository.findAll();
        // 2. 모델에 데이터 등록
        model.addAttribute("articleList", articleEntityList);
        // 3. 뷰 페이지 설정
        return "articles/index";
    }
    @GetMapping("articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 조회
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // 모델에 데이터 등록
        model.addAttribute("article", articleEntity);
        // 뷰 페이지 설정
        return "articles/edit";
    }
    @PostMapping("articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());
        // 1. DTO -> Entity
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        // 2. Entity -> DB 저장
        // 2-1 기존 데이터 조회
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2 데이터 갱신
        if (target != null) // 기존에 데이터가 있었는지 확인한 후에 데이터를 집어넣음(검증과정임)
            articleRepository.save(articleEntity);
        // saved 메소드는 테이블에 entity를 추가하지만, 추가시 동일한 id(PK)값이 있다면 그 위치에 값을 대체하는 방식으로 적용
        // 그니까 INSERT랑 UPDATE를 둘 다 수행하는거지(UPDATE인 경우는 WHERE절에서 id 동일 여부를 보는거고)
        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }
    @GetMapping("articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!!");
        // 삭제 대상 조회
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 대상 entity 삭제
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        // 결과 페이지 리다이렉트
        return "redirect:/articles";
    }
}
