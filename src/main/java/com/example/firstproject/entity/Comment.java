package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne // Comment - Article은 n-1관계
    @JoinColumn(name = "article_id") // Foriegn Key 이름 지정
    // 테이블에 이 이름의 열이 만들어지고, Article의 PK와 매핑됨
    private Article article;
    @Column
    private String nickname;
    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 발생
        if (dto.getId() != null) throw new IllegalArgumentException("댓글 생성 실패! 댓글에 id가 없어야 합니다.");
        // dto는 처음에 아이디가 있으믄 안됨(JSON에서 받을 때 댓글 정보엔 id가 있으믄 안됨)
        if (dto.getArticleId() != article.getId()) throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");
        // JSON으로 넘어온 댓글 정보의 articleId와 URL로 넘어온 {articleId}가 다른 경우에도 생성하믄 안됨
        // 엔티티 생성 및 반환
        return new Comment(dto.getId(), article, dto.getNickname(), dto.getBody());
    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if (id != dto.getId()) throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었어유");
        // 객체 갱신
        if (dto.getNickname() != null) nickname = dto.getNickname();
        if (dto.getBody() != null) body = dto.getBody();
    }
}
