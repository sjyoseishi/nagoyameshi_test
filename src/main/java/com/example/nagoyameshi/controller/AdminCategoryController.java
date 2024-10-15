package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.nagoyameshi.service.CategoryService;
import com.example.nagoyameshi.form.CategoryEditForm;
import com.example.nagoyameshi.form.CategoryRegisterForm;
import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.repository.CategoryRepository;


@Controller
@RequestMapping("/admin/categorys")
public class AdminCategoryController {

    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public AdminCategoryController(CategoryRepository categoryRepository, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
        Page<Category> categoryPage;

        if (keyword != null && !keyword.isEmpty()) {
        	categoryPage = categoryRepository.findByNameLike("%" + keyword + "%", pageable);
        } else {
        	categoryPage = categoryRepository.findAll(pageable);
        }

        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("keyword", keyword);

        return "admin/categorys/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
    	Category category = categoryRepository.getReferenceById(id);

        model.addAttribute("category", category);

        return "admin/categorys/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
    	Category category = categoryRepository.getReferenceById(id);

    	CategoryEditForm categoryEditForm = new CategoryEditForm(category.getId(), category.getName());

        model.addAttribute("categoryEditForm", categoryEditForm);

        return "admin/categorys/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute @Validated CategoryEditForm categoryEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/categorys/edit";
        }

        categoryService.update(categoryEditForm);
        redirectAttributes.addFlashAttribute("successMessage", "カテゴリ情報を編集しました。");

        return "redirect:/admin/categorys";
    }


	@PostMapping("/create")
    public String create(@ModelAttribute @Validated CategoryRegisterForm categoryRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/categorys/register";
        }

        categoryService.create(categoryRegisterForm);
        redirectAttributes.addFlashAttribute("successMessage", "カテゴリーを登録しました。");

        return "redirect:/admin/categorys";
    }
}
