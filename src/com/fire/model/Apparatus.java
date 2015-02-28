package com.fire.model;

/**
 * This entity class provides a representation of the fire department
 * apparatus.  
 * 
 * @author Louis Drotos - 22 January 2015
 *
 */
public class Apparatus {
	
	private String stationId;
	private String unitDesignator;
	private String unitType;
	
	/**
	 * Constructor
	 */
	public Apparatus() {
		super();
	}

	/**
	 * Getter - Station ID
	 * 
	 * @return This unit's assigned station ID
	 */
	public String getStationId() {
		return stationId;
	}

	/**
	 * Setter - Station ID
	 * 
	 * @param stationId This unit's assigned station ID 
	 */
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	/**
	 * Getter - Unit Designator
	 * 
	 * @return This unit's designator
	 */
	public String getUnitDesignator() {
		return unitDesignator;
	}

	/**
	 * Setter - Unit Designator
	 * 
	 * @param unitDesignator This unit's designator 
	 */
	public void setUnitDesignator(String unitDesignator) {
		this.unitDesignator = unitDesignator;
	}

	/**
	 * Getter - Unit Type
	 * 
	 * @return This unit's type
	 */
	public String getUnitType() {
		return unitType;
	}

	/**
	 * Setter - Unit Type
	 * 
	 * @param unitType This unit's type
	 */
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	
	/**
	 * Returns a string representation of this object
	 * 
	 * @return The string representation
	 */
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Assigned Station ID : " + this.stationId);
		builder.append("Unit Designator : " + this.unitDesignator);
		builder.append("Unit Type : " + this.unitType);
		return builder.toString().trim();
	}
}
