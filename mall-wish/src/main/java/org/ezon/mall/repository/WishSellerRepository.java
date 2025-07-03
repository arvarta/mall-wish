package org.ezon.mall.repository;

import java.util.List;
import java.util.Optional;

import org.ezon.mall.entity.SellerWishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishSellerRepository extends JpaRepository<SellerWishlist, Long> {
    List<SellerWishlist> findAllByUserId(Long userId);
    Optional<SellerWishlist> findByUserIdAndSellerUserId(Long userId, Long sellerUserId);
    void deleteByUserIdAndSellerUserId(Long userId, Long sellerUserId);
}
