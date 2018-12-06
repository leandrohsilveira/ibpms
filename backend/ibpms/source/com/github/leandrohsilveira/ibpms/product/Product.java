package com.github.leandrohsilveira.ibpms.product;

import java.math.BigDecimal;
import java.util.UUID;

import com.github.leandrohsilveira.ibpms.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product extends Model {

	private static final long serialVersionUID = 1894799518679462789L;
	
	@Builder
	public Product(UUID uuid, String code, String name, BigDecimal price) {
		super(uuid);
		this.code = code;
		this.name = name;
		this.price = price;
	}
	
	private String code;
	
	private String name;
	
	private BigDecimal price;

}
