package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Store;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.StoreRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;

@Controller
public class FavoriteController {
    private final FavoriteRepository favoriteRepository;
    private final StoreRepository storeRepository;
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteRepository favoriteRepository, StoreRepository storeRepository, FavoriteService favoriteService) {
        this.favoriteRepository = favoriteRepository;
        this.storeRepository = storeRepository;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/favorites")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable, Model model) {
        User user = userDetailsImpl.getUser();
        Page<Favorite> favoritePage = favoriteRepository.findByUserOrderByCreatedAtDesc(user, pageable);

        model.addAttribute("favoritePage", favoritePage);

        return "favorites/index";
    }

    @PostMapping("/stores/{storeId}/favorites/create")
    public String create(@PathVariable(name = "storeId") Integer storeId,
                         @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                         RedirectAttributes redirectAttributes,
                         Model model)
    {
        Store store = storeRepository.getReferenceById(storeId);
        User user = userDetailsImpl.getUser();

        favoriteService.create(store, user);
        redirectAttributes.addFlashAttribute("successMessage", "お気に入りに追加しました。");

        return "redirect:/stores/{storeId}";
    }

    @PostMapping("/stores/{storeId}/favorites/{favoriteId}/delete")
    public String delete(@PathVariable(name = "favoriteId") Integer favoriteId, RedirectAttributes redirectAttributes) {
        favoriteRepository.deleteById(favoriteId);

        redirectAttributes.addFlashAttribute("successMessage", "お気に入りを解除しました。");

        return "redirect:/stores/{storeId}";
    }
}