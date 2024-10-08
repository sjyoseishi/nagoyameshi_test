package com.example.nagoyameshi.entity;

import java.sql.Timestamp;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
 @Entity
 @Table(name = "stores")
 @Data
public class Store {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     private Integer id;

 	@ManyToOne
 	@JoinColumn(name = "category_id")
 	private Category category;

     @Column(name = "name")
     private String name;

     @Column(name = "image_name")
     private String imageName;

     @Column(name = "description")
     private String description;

     @Column(name = "price_floor")
     private Integer priceFloor;

     @Column(name = "price_cap")
     private Integer priceCap;

 	@Column(name = "opening_time")
 	private LocalTime openingTime;

 	@Column(name = "closing_time")
 	private LocalTime closingTime;

	@Column(name = "seating_capacity")
	private int seatingCapacity;

     @Column(name = "postal_code")
     private String postalCode;

     @Column(name = "address")
     private String address;

     @Column(name = "phone_number")
     private String phoneNumber;

     @Column(name = "regular_holiday")
     private String regularHoliday;


     @Column(name = "created_at", insertable = false, updatable = false)
     private Timestamp createdAt;

     @Column(name = "updated_at", insertable = false, updatable = false)
     private Timestamp updatedAt;

}




