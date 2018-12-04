package com.github.leandrohsilveira.ibpms;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Model implements Serializable {
	
	private static final long serialVersionUID = 3123889468495726213L;
	
	private UUID uuid;
	
	@Override
	public boolean equals(Object obj) {
		if(uuid != null && obj != null && obj instanceof Model) {
			return Objects.equals(uuid, ((Model) obj).getUuid());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}
	
}
