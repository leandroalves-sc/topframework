package com.topsoft.topframework.base.domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;

@MappedSuperclass
public class BaseEntity<ID> implements Entity<ID> {
	
	@Id
	protected ID id;
	
	public ID getId() {
		return id;
	}

	public void setID(ID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return getId() == null ? System.identityHashCode(this) : getId().hashCode();
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;

		if (this == obj)
			return true;

		if (getClass() != obj.getClass())
			return false;
		
		BaseEntity<?> other = (BaseEntity<?>) obj;
		
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else if (!id.equals(other.id)){
			return false;
		}
		
		return true;
	}	
}