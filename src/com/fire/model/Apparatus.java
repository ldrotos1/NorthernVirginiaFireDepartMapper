package com.fire.model;

public class Apparatus {
	
	private String stationId;
	private String unitDesignator;
	private String unitType;
	
	public Apparatus() {
		super();
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getUnitDesignator() {
		return unitDesignator;
	}

	public void setUnitDesignator(String unitDesignator) {
		this.unitDesignator = unitDesignator;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Assigned Station ID : " + this.stationId);
		builder.append("Unit Designator : " + this.unitDesignator);
		builder.append("Unit Type : " + this.unitType);
		return builder.toString().trim();
	}
}
