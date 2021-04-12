package com.app.model;

public enum StateCode {
	AK("AK", "Alaska"),
	AL("AL", "Alabama"),
	AR("AR", "Arkansas"),
	AZ("AZ", "Arizona"),
	CA("CA", "California"),
	CO("CO", "Colorado"),
	CT("CT", "Connecticut"),
	DE("DE", "Delaware"),
	FL("FL", "Florida"),
	GA("GA", "Georgia"),
	HI("HI", "Hawaii"),
	IA("IA", "Iowa"),
	ID("ID", "Idaho"),
	IL("IL", "Illinois"),
	IN("IN", "Indiana"),
	KS("KS", "Kansas"),
	KY("KY", "Kentucky"),
	LA("LA", "Louisiana"),
	MA("MA", "Massachusetts"),
	MD("MD", "Maryland"),
	ME("ME", "Maine"),
	MI("MI", "Michigan"),
	MN("MN", "Minnesota"),
	MO("MO", "Missouri"),
	MS("MS", "Mississippi"),
	MT("MT", "Montana"),
	NC("NC", "NorthCarolina"),
	ND("ND", "NorthDakota"),
	NE("NE", "Nebraska"),
	NH("NH", "NewHampshire"),
	NJ("NJ", "NewJersey"),
	NM("NM", "NewMexico"),
	NV("NV", "Nevada"),
	NY("NY", "NewYork"),
	OH("OH", "Ohio"),
	OK("OK", "Oklahoma"),
	OR("OR", "Oregon"),
	PA("PA", "Pennsylvania"),
	RI("RI", "RhodeIsland"),
	SC("SC", "SouthCarolina"),
	SD("SD", "SouthDakota"),
	TN("TN", "Tennessee"),
	TX("TX", "Texas"),
	UT("UT", "Utah"),
	VA("VA", "Virginia"),
	VT("VT", "Vermont"),
	WA("WA", "Washington"),
	WI("WI", "Wisconsin"),
	WV("WV", "WestVirginia"),
	WY("WY", "Wyoming");

	private final String key;
	private final String value;

	StateCode(String value, String key) {
		this.value = value;
		this.key = key;
	}
}
