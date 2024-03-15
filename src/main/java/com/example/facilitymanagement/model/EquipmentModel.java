// package com.example.facilitymanagement.model;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;

// @Entity
// public class EquipmentModel {
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long equipmentId;

// @Column(nullable = false, unique = true)
// private String equipmentType;

// @Column(nullable = false)
// private int equipmentQuantity;

// @Column(nullable = false)
// private int equipmentReplenishRate;

// @Column(nullable = false)
// private int equipmentMaxQuantity;

// // ゲッターとセッター
// public Long getEquipmentId() {
// return equipmentId;
// }

// public String getEquipmentType() {
// return equipmentType;
// }

// public void setEquipmentType(String equipmentType) {
// this.equipmentType = equipmentType;
// }

// public int getEquipmentQuantity() {
// return equipmentQuantity;
// }

// public void setEquipmentQuantity(int equipmentQuantity) {
// this.equipmentQuantity = equipmentQuantity;
// }

// public int getEquipmentReplenishRate() {
// return equipmentReplenishRate;
// }

// public void setEquipmentReplenishRate(int equipmentReplenishRate) {
// this.equipmentReplenishRate = equipmentReplenishRate;
// }

// public int getEquipmentMaxQuantity() {
// return equipmentMaxQuantity;
// }

// public void setEquipmentMaxQuantity(int equipmentMaxQuantity) {
// this.equipmentMaxQuantity = equipmentMaxQuantity;
// }
// }
