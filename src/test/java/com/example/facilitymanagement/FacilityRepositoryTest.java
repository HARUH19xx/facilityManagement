package com.example.facilitymanagement;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.example.facilitymanagement.model.FacilityModel;
import com.example.facilitymanagement.repository.FacilityRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // テスト用データベースに置き換え
public class FacilityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FacilityRepository facilityRepository;

    @Test
    public void shouldFindFacilityByFacilityname() {
        // テスト用の施設エンティティを作成し、データベースに保存
        FacilityModel facility = new FacilityModel();
        facility.setFacilityname("testFacility");
        facility.setDescription("A test facility description");
        // 登録日時は自動設定されるため、ここでは指定しない
        entityManager.persistAndFlush(facility);

        // 施設名で施設を検索し、期待される結果が得られるかテスト
        Optional<FacilityModel> found = facilityRepository.findByFacilityname("testFacility");
        assertThat(found.isPresent()).isTrue();
        found.ifPresent(foundFacility -> {
            assertThat(foundFacility.getFacilityname()).isEqualTo("testFacility");
            assertThat(foundFacility.getDescription()).isEqualTo("A test facility description");
            // 登録日時が自動的に設定されていることを確認
            assertThat(foundFacility.getCreatedAt()).isNotNull();
        });
    }
}
