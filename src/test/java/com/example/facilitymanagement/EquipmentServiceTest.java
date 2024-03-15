// package com.example.facilitymanagement;

// import com.example.facilitymanagement.model.EquipmentModel;
// import com.example.facilitymanagement.repository.EquipmentRepository;
// // import com.example.facilitymanagement.service.EquipmentService;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;
// import java.util.Arrays;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// public class EquipmentServiceTest {

// @Mock
// private EquipmentRepository equipmentRepository;

// // @InjectMocks
// // private EquipmentService equipmentService;

// @BeforeEach
// public void setUp() {
// MockitoAnnotations.openMocks(this);
// }

// @Test
// public void testReplenishEquipment() {
// // 備品のモックを作成
// EquipmentModel equipment1 = new EquipmentModel();
// // equipment1.setId(1L);
// equipment1.setType("Chair");
// equipment1.setQuantity(5);
// equipment1.setReplenishRate(3);
// equipment1.setMaxQuantity(10);

// EquipmentModel equipment2 = new EquipmentModel();
// // equipment2.setId(2L);
// equipment2.setType("Table");
// equipment2.setQuantity(8);
// equipment2.setReplenishRate(5);
// equipment2.setMaxQuantity(12);

// // Repositoryが上記の備品リストを返すように設定
// when(equipmentRepository.findAll()).thenReturn(Arrays.asList(equipment1,
// equipment2));

// // 補充処理を実行
// equipmentService.replenishEquipment();

// // saveメソッドが呼び出された時の引数（EquipmentModel）をキャプチャ
// ArgumentCaptor<EquipmentModel> equipmentCaptor =
// ArgumentCaptor.forClass(EquipmentModel.class);
// verify(equipmentRepository, times(2)).save(equipmentCaptor.capture());

// // 補充後の数量が期待値に一致するか確認
// List<EquipmentModel> capturedEquipments = equipmentCaptor.getAllValues();
// for (EquipmentModel capturedEquipment : capturedEquipments) {
// if (capturedEquipment.getType().equals("Chair")) {
// assertEquals(8, capturedEquipment.getQuantity(), "Chairの数量が期待値と一致しません");
// } else if (capturedEquipment.getType().equals("Table")) {
// assertEquals(12, capturedEquipment.getQuantity(), "Tableの数量が期待値と一致しません");
// }
// }
// }
// }
