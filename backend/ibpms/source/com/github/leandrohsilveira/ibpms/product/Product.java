package com.github.leandrohsilveira.ibpms.product;

import java.math.BigDecimal;
import java.util.UUID;

import com.github.leandrohsilveira.ibpms.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product extends Model {

	private static final long serialVersionUID = 1894799518679462789L;
	
	@Builder
	public Product(UUID uuid, String name, BigDecimal price) {
		super(uuid);
		this.name = name;
		this.price = price;
	}

	@NonNull
	private String name;
	
	@NonNull
	private BigDecimal price;

}
