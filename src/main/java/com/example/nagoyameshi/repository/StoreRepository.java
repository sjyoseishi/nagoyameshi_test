package com.example.nagoyameshi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.nagoyameshi.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Integer>{
	public Page<Store> findByNameLike(String keyword, Pageable pageable);

    public Page<Store> findByNameLikeOrAddressLikeOrCategoryNameLikeOrderByCategoryNameDesc(String nameKeyword, String addressKeyword, String categoryKeyword, Pageable pageable);
    public Page<Store> findByCategoryIdOrderByCreatedAtDesc(Integer category, Pageable pageable);
    public Page<Store> findByAddressLike(String area, Pageable pageable);
    public Page<Store> findByPriceCapLessThanEqualOrderByPriceCapAsc(Integer priceCap, Pageable pageable);

}
