package org.ezon.mall.repository;

import java.util.List;
import java.util.Optional;

import org.ezon.mall.entity.ProductWishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishProductRepository extends JpaRepository<ProductWishlist, Long>{

	List<ProductWishlist> findAllByUserId(Long userId);
	Optional<ProductWishlist> findByUserIdAndProductId(Long userId, Long productId);
	void deleteByUserIdAndProductId(Long userId, Long productId);
}
