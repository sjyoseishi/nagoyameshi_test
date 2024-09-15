package com.example.nagoyameshi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.nagoyameshi.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Integer>{
	public Page<Store> findByNameLike(String keyword, Pageable pageable);

}
