package de.tiago.util;

public class Property {
	
	private String description;
	private Object value;
	
	private Actionable event;
	private boolean mutable = false;
	
	public Property(String description, Object value) {
		
		this.description = description;
		this.value = value;
	}
	
	public Property(String description, Object value, Actionable event) {
		
		this.description = description;
		this.value = value;
		
		this.event = event;
		mutable = true;
	}
	
	public String getDescription() {return description;}
	
	public Object getValue() {return value;}
	
	public boolean isMutable() {return mutable;}
	
	public void executeAction(Object parameter) {
		
		event.action(parameter);
	}
}
