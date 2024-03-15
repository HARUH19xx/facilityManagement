package com.example.facilitymanagement;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.facilitymanagement.model.RoleModel;
import com.example.facilitymanagement.model.UserModel;
import com.example.facilitymanagement.repository.RoleRepository;
import com.example.facilitymanagement.repository.UserRepository;

@Component
public class AppStartupRunner implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Environment env;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // ルートユーザーの存在チェック
        String rootUsername = env.getProperty("root.user.username");
        String rootPassword = env.getProperty("root.user.password");
        // DateOfBirth、今日の日付を取得
        LocalDate dateOfBirth = LocalDate.now();

        if (!userRepository.findByUsername(rootUsername).isPresent()) {
            // ルートユーザーの作成
            UserModel rootUser = new UserModel();
            rootUser.setUsername(rootUsername);
            rootUser.setPassword(passwordEncoder.encode(rootPassword));
            rootUser.setDateOfBirth(dateOfBirth);
            // 管理者ロールの割り当て
            RoleModel adminRole = roleRepository.findByRoleName("ROLE_ADMIN");
            if (adminRole != null) {
                rootUser.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
            }
            userRepository.save(rootUser);
            System.out.println("ルートユーザーを作成しました。");
        }
    }
}
