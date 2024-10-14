package com.masai.models;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartDTO {
	
	@NotNull
	private Integer productId;
	
	private String productName;
	
	private Double price;
	
	@Min(1)
	private Integer quantity;
	
}
