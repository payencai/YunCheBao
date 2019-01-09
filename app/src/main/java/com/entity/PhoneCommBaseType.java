package com.entity;

import java.io.Serializable;

public class PhoneCommBaseType implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static String primaryKey = "typeId";
	private int typeId;
	private String typeName;
	private String typeDes;

	public PhoneCommBaseType() {
		super();
		// TODO Auto-generated constructor stub
	}


	public PhoneCommBaseType(int typeId, String typeName) {
		super();
		this.typeId = typeId;
		this.typeName = typeName;
	}

	public PhoneCommBaseType(int typeId, String typeName, String typeDes) {
		this.typeId = typeId;
		this.typeName = typeName;
		this.typeDes = typeDes;
	}

	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static String getPrimarykey() {
		return primaryKey;
	}

	public String getTypeDes() {
		return typeDes;
	}

	public void setTypeDes(String typeDes) {
		this.typeDes = typeDes;
	}
}
