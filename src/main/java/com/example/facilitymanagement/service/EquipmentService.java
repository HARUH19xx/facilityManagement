// package com.example.facilitymanagement.service;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;

// import com.example.facilitymanagement.model.EquipmentModel;
// import com.example.facilitymanagement.repository.EquipmentRepository;

// @Component
// public class EquipmentService {

// @Autowired
// private EquipmentRepository equipmentRepository;

// // 毎日午前0時に実行
// @Scheduled(cron = "0 0 0 * * ?")
// public void replenishEquipment() {
// List<EquipmentModel> equipments = equipmentRepository.findAll();
// for (EquipmentModel equipment : equipments) {
// int newEquipmentQuantity = equipment.getEquipmentQuantity() +
// equipment.getEquipmentReplenishRate();
// if (newEquipmentQuantity > equipment.getEquipmentMaxQuantity()) {
// newEquipmentQuantity = equipment.getEquipmentMaxQuantity(); // 上限を超えないように調整
// }
// equipment.setEquipmentQuantity(newEquipmentQuantity);
// equipmentRepository.save(equipment);
// }
// }
// }
