// package com.example.facilitymanagement;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;
// import com.example.facilitymanagement.model.AdminKeyModel;
// import com.example.facilitymanagement.repository.AdminKeyRepository;

// import java.util.Optional;

// @Component
// public class AdminKeyInitializer implements CommandLineRunner {

// private final AdminKeyRepository adminKeyRepository;

// @Autowired
// public AdminKeyInitializer(AdminKeyRepository adminKeyRepository) {
// this.adminKeyRepository = adminKeyRepository;
// }

// @Override
// public void run(String... args) throws Exception {
// // 環境変数から管理者キーを取得
// String adminKey = System.getenv("ADMIN_KEY");

// // 管理者キーがデータベースに存在するか確認
// Optional<AdminKeyModel> existingKey =
// adminKeyRepository.findByAdminKeyValue(adminKey);

// // 存在しない場合、新しい管理者キーをデータベースに追加
// if (existingKey.isEmpty() && adminKey != null && !adminKey.isEmpty()) {
// AdminKeyModel newAdminKey = new AdminKeyModel();
// newAdminKey.setAdminKeyValue(adminKey);
// adminKeyRepository.save(newAdminKey);
// System.out.println("New admin key has been added to the database.");
// } else {
// System.out.println("Admin key is already set or environment variable is not
// defined.");
// }
// }
// }
