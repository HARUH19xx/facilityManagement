// package com.example.facilitymanagement.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.facilitymanagement.model.EquipmentModel;
// import com.example.facilitymanagement.repository.EquipmentRepository;

// @RestController
// public class EquipmentController {

//     @Autowired
//     private EquipmentRepository equipmentRepository;

//     @Autowired
//     public EquipmentController(EquipmentRepository equipmentRepository) {
//         this.equipmentRepository = equipmentRepository;
//     }

//     @RequestMapping("/api/equipment")
//     @PreAuthorize("hasRole('ADMIN')")
//     @PostMapping
//     public ResponseEntity<EquipmentModel> createEquipment(@RequestBody EquipmentModel equipment) {
//         // リクエストボディが空の場合は、400 Bad Requestを返す（実際にはあり得ない）
//         if (equipment == null) {
//             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//         }

//         // 備品名が空の場合は、400 Bad Requestを返す
//         if (equipment.getEquipmentType() == null || equipment.getEquipmentType().isEmpty()) {
//             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//         }

//         // 備品名が重複している場合は、400 Bad Requestを返す
//         if (equipmentRepository.findByEquipmentType(equipment.getEquipmentType()).isPresent()) {
//             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//         }

//         EquipmentModel savedEquipment = equipmentRepository.save(equipment);
//         return new ResponseEntity<>(savedEquipment, HttpStatus.CREATED);
//     }

//     @RequestMapping("/api/equipment/show")
//     @GetMapping
//     public ResponseEntity<Iterable<EquipmentModel>> getEquipments() {
//         Iterable<EquipmentModel> equipments = equipmentRepository.findAll();
//         return new ResponseEntity<>(equipments, HttpStatus.OK);
//     }
// }
