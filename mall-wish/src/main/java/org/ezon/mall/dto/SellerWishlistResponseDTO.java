package org.ezon.mall.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerWishlistResponseDTO {
	
	private Long wishlistId;
	private Long sellerUserId;
	private Long userId;
	private LocalDateTime addedAt;
}
