package org.ezon.mall.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.ezon.mall.entity.SellerWishlist;
import org.ezon.mall.exception.WishErrorCode;
import org.ezon.mall.exception.WishException;
import org.ezon.mall.repository.WishSellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishSellerService {

    @Autowired
    private WishSellerRepository wishSellerRepository;

    public WishSellerService(WishSellerRepository wishSellerRepository) {
        this.wishSellerRepository = wishSellerRepository;
    }

    // 판매자 찜 토글 (등록/해제)
    @Transactional
    public boolean toggleWishSeller(Long userId, Long sellerUserId) {
        if(userId == null || sellerUserId == null) {
            throw new WishException("잘못된 요청입니다.", WishErrorCode.INVALID_REQUEST);
        }
        Optional<SellerWishlist> existing = wishSellerRepository.findByUserIdAndSellerUserId(userId, sellerUserId);
        if(existing.isPresent()) {
            wishSellerRepository.deleteByUserIdAndSellerUserId(userId, sellerUserId);
            return false; // 해제됨
        } else {
            SellerWishlist wishlist = SellerWishlist.builder()
                .userId(userId)
                .sellerUserId(sellerUserId)
                .addedAt(LocalDateTime.now())
                .build();
            wishSellerRepository.save(wishlist);
            return true; // 등록됨
        }
    }

    // 찜한 판매자 목록 조회
    public List<SellerWishlist> getWishSellers(Long userId) {
        if(userId == null) {
            throw new WishException("잘못된 요청입니다.", WishErrorCode.INVALID_REQUEST);
        }
        return wishSellerRepository.findAllByUserId(userId);
    }

    // 찜 해제
    @Transactional
    public void deleteWishSeller(Long userId, Long sellerUserId) {
        if(userId == null || sellerUserId == null) {
            throw new WishException("잘못된 요청입니다.", WishErrorCode.INVALID_REQUEST);
        }
        Optional<SellerWishlist> existing = wishSellerRepository.findByUserIdAndSellerUserId(userId, sellerUserId);
        if(existing.isPresent()) {
            wishSellerRepository.deleteByUserIdAndSellerUserId(userId, sellerUserId);
        } else {
            throw new WishException("찜한 정보가 없습니다.", WishErrorCode.NOT_FOUND);
        }
    }
}
