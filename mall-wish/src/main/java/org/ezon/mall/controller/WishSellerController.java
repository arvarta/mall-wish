package org.ezon.mall.controller;

import org.ezon.mall.entity.SellerWishlist;
import org.ezon.mall.service.WishSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wish/sellers")
public class WishSellerController {

    @Autowired
    private WishSellerService wishSellerService;

    // (판매자 정보 단건 조회용)
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public WishSellerController(WishSellerService wishSellerService) {
        this.wishSellerService = wishSellerService;
    }

    // 찜한 판매자 목록 조회
    @GetMapping
    public List<SellerWishlist> getWishSellers(@RequestParam Long userId) {
        return wishSellerService.getWishSellers(userId);
    }

    // 찜 토글(등록/해제)
    @PostMapping("/toggle")
    public boolean toggleWishSeller(@RequestParam Long userId, @RequestParam Long sellerUserId) {
        return wishSellerService.toggleWishSeller(userId, sellerUserId);
    }

    // 찜 해제
    @DeleteMapping("/{sellerUserId}")
    public void deleteWishSeller(@RequestParam Long userId, @PathVariable Long sellerUserId) {
        wishSellerService.deleteWishSeller(userId, sellerUserId);
    }

    // 판매자 정보 단건 조회 (프론트에서 직접 호출 가능)
    // GET /api/wish/sellers/info/{sellerUserId}
    @GetMapping("/info/{sellerUserId}")
    public ResponseEntity<?> getSellerInfo(@PathVariable Long sellerUserId) {
        String sql = "SELECT user_id, company_name, business_registration_num, profile_img, name, tel, email " +
                     "FROM user WHERE user_id = ? AND role = 'seller'";
        try {
            Map<String, Object> seller = jdbcTemplate.queryForMap(sql, sellerUserId);
            return ResponseEntity.ok(seller);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("판매자 정보가 없습니다.");
        }
    }
}
