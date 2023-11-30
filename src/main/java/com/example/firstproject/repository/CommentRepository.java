package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글 모든 댓글 조회
    @Query(value = "SELECT * FROM comment WHERE article_id = :articleId",
            nativeQuery = true) // 네이티브 쿼리를 @Query 써서 구현 (네이티브 쿼리 메소드)
    List<Comment> findByArticleId(@Param("articleId") Long articleId); // ???

    // 특정 닉네임 모든 댓글 조회
    List<Comment> findByNickname(@Param("nickname") String nickname);
//     차이를 알았대이
//     저거 @Param이라 적힌거 안쓰는 방법은 설정의 build 방식을 Gradle로 하는거임
//     build 방식을 IntelliJ IDEA로 하면 무조건 @Param을 써야하고, 안쓰믄 저거 에러남
//     근디 문제는 Gradle로 하믄 @Param을 안 쓸 수 있는 대신에 Test할 때 에러나버림
//     ㄴ assertEquals 여기서 두 값을 비교를 못 하면서 에러가 나버려서 일단 IntelliJ IDEA로 해놨음
//     ㄴ Test할 때 Gradle로 한 경우에 에러가 나지 않는 방법을 한번 찾아봐야 할듯
}
