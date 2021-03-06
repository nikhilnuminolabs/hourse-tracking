package com.hourse.tracking.model;

import static com.hourse.tracking.constant.Constant.*;

public class Denomination implements Comparable<Denomination> {
	
	private Integer value;
	
	private String unit = DENOMINATION_UNIT;

	public Denomination(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public int compareTo(Denomination o) {
	
		return this.getValue().compareTo(o.getValue());
	}

	@Override
	public String toString() {
		return unit+value;
	}
	
	
}
