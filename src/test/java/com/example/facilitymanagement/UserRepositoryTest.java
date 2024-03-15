package com.example.facilitymanagement;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.facilitymanagement.model.UserModel;
import com.example.facilitymanagement.repository.UserRepository;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindUserByUsername() {
        // 仮のユーザーをデータベースに保存
        UserModel user = new UserModel(); // デフォルトコンストラクタを使用してインスタンス化
        user.setUsername("testUser");
        user.setPassword("password");
        user.setDateOfBirth(LocalDate.of(2000, 1, 1)); // 必要に応じて日付を設定
        entityManager.persistAndFlush(user);

        // ユーザー検索メソッドをテスト
        Optional<UserModel> found = userRepository.findByUsername(user.getUsername());
        assertThat(found.isPresent()).isTrue();
        found.ifPresent(foundUser -> assertThat(foundUser.getUsername()).isEqualTo(user.getUsername()));
    }
}
