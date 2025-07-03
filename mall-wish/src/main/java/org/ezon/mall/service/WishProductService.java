package org.ezon.mall.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.ezon.mall.entity.ProductWishlist;
import org.ezon.mall.exception.WishErrorCode;
import org.ezon.mall.exception.WishException;
import org.ezon.mall.repository.WishProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishProductService {

    @Autowired
    private WishProductRepository wishProductRepository;

    public WishProductService(WishProductRepository wishProductRepository) {
        this.wishProductRepository = wishProductRepository;
    }

    // 상품 찜 토글 (등록/해제)
    @Transactional
    public boolean toggleWishProduct(Long userId, Long productId) {
    	System.out.println("💡 찜 토글 진입: userId=" + userId + ", productId=" + productId);
        if(userId == null || productId == null) {
            throw new WishException("잘못된 요청입니다.", WishErrorCode.INVALID_REQUEST);
        }
        Optional<ProductWishlist> existing = wishProductRepository.findByUserIdAndProductId(userId, productId);
        if(existing.isPresent()) {
            wishProductRepository.deleteByUserIdAndProductId(userId, productId);
            return false; // 해제됨
        } else {
            ProductWishlist wishlist = ProductWishlist.builder()
                .userId(userId)
                .productId(productId)
                .addedAt(LocalDateTime.now())
                .build();
            wishProductRepository.save(wishlist);
            return true; // 등록됨
        }
    }

    // 찜 목록 조회
    public List<ProductWishlist> getWishProducts(Long userId) {
        if(userId == null) {
            throw new WishException("잘못된 요청입니다.", WishErrorCode.INVALID_REQUEST);
        }
        return wishProductRepository.findAllByUserId(userId);
    }

    // 상품 찜 해제
    @Transactional
    public void deleteWishProduct(Long userId, Long productId) {
        if(userId == null || productId == null) {
            throw new WishException("잘못된 요청입니다.", WishErrorCode.INVALID_REQUEST);
        }
        Optional<ProductWishlist> existing = wishProductRepository.findByUserIdAndProductId(userId, productId);
        if(existing.isPresent()) {
            wishProductRepository.deleteByUserIdAndProductId(userId, productId);
        } else {
            throw new WishException("찜한 정보가 없습니다.", WishErrorCode.NOT_FOUND);
        }
    }
}
