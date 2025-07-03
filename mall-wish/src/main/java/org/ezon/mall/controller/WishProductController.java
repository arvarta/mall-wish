package org.ezon.mall.controller;

import org.ezon.mall.entity.ProductWishlist;
import org.ezon.mall.service.WishProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wish/products")
public class WishProductController {

    @Autowired
    private WishProductService wishProductService;

    public WishProductController(WishProductService wishProductService) {
        this.wishProductService = wishProductService;
    }

    // 찜 목록 조회
    @GetMapping
    public List<ProductWishlist> getWishProducts(@RequestParam Long userId) {
        return wishProductService.getWishProducts(userId);
    }

    // 찜 토글(등록/해제)
    @PostMapping("/toggle")
    public ResponseEntity<Map<String, Boolean>> toggleWishProduct(@RequestBody Map<String, Long> json) {
        Long userId = json.get("userId");
        Long productId = json.get("productId");
        boolean check = wishProductService.toggleWishProduct(userId, productId);
    	Map<String, Boolean> result = new HashMap<>();
    	result.put("wishlisted", check);
    	result.put("success", true);
        return ResponseEntity.ok(result);
    }

    // 찜 해제
    @DeleteMapping("/{productId}")
    public void deleteWishProduct(@RequestParam Long userId, @PathVariable Long productId) {
        wishProductService.deleteWishProduct(userId, productId);
    }
}
