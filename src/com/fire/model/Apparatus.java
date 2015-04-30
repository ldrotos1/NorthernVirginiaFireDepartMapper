package com.fire.model;

/**
 * This entity class provides a representation of the fire department
 * apparatus.  
 * 
 * @author Louis Drotos - 22 January 2015
 *
 */
public class Apparatus implements Comparable<Apparatus>{
	
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
	 * @return This unit's assigned station ID
	 */
	public String getStationId() {
		return stationId;
	}

	/**
	 * Setter - Station ID
	 * @param stationId This unit's assigned station ID 
	 */
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	/**
	 * Getter - Unit Designator
	 * @return This unit's designator
	 */
	public String getUnitDesignator() {
		return unitDesignator;
	}

	/**
	 * Setter - Unit Designator
	 * @param unitDesignator This unit's designator 
	 */
	public void setUnitDesignator(String unitDesignator) {
		this.unitDesignator = unitDesignator;
	}

	/**
	 * Getter - Unit Type
	 * @return This unit's type
	 */
	public String getUnitType() {
		return unitType;
	}

	/**
	 * Setter - Unit Type
	 * @param unitType This unit's type
	 */
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

	@Override
	public int compareTo(Apparatus apparatus) {
		
		// Declares variables
		int thisUnit;
		int otherUnit;
		
		// Gets the sort position for each apparatus
		thisUnit = getSortPostion(this.unitType);
		otherUnit = getSortPostion(apparatus.unitType);
		
		// Compares apparatus based on sort position
		if (thisUnit > otherUnit) {
			return 1;
		}
		else if (otherUnit > thisUnit) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	/**
	 * This method determines the sort position of a Apparatus
	 * object based on the value of its unit type.
	 * @param unitType The unit type
	 * @return The sort position
	 */
	private int getSortPostion(String unitType) {
		
		switch(unitType) {
			
			case "Engine":
				return 1;
			
			case "Truck":
				return 2;
			
			case "Tower":
				return 2;
			
			case "Rescue":
				return 3;
				
			case "Technical Rescue":
				return 4;
			
			case "Tanker":
				return 5;
				
			case "Hazmat":
				return 6;
			
			case "Foam":
				return 6;
				
			case "Battalion Chief":
				return 7;
				
			case "Mobile Command Post":
				return 8;
				
			case "Mass Casualty Support":
				return 8;
				
			case "Brush":
				return 9;
			
			case "Boat":
				return 10;
				
			case "Boat Support":
				return 11;
				
			case "Utility":
				return 12;
				
			case "Mobile Air":
				return 13;
			
			case "Light and Air":
				return 13;
			
			case "Canteen":
				return 14;
				
			case "Rehabilitation":
				return 14;
			
			case "Medic":
				return 15;
			
			case "EMS Supervisor":
				return 16;
				
			default:
				return 9999;
		}
	}
}



