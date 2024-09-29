package com.example.nagoyameshi.service;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.form.CategoryEditForm;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.nagoyameshi.repository.CategoryRepository;


@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void update(CategoryEditForm categoryEditForm) {
    	Category category = categoryRepository.getReferenceById(categoryEditForm.getId());

    	category.setName(categoryEditForm.getName());

    	categoryRepository.save(category);
    }

}
