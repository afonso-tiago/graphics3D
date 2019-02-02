package de.tiago.obj;

import java.awt.Color;
import java.util.ArrayList;

import de.tiago.graphics3D.entities.Cube;
import de.tiago.graphics3D.geometry.Point3D;
import de.tiago.util.Actionable;
import de.tiago.util.Categorizable;
import de.tiago.util.Property;

public class ColoredCube extends Cube implements Categorizable {
	
	private Color color;
	
	public static final Color DEFAULT_COLOR = Color.DARK_GRAY;
	
	public ColoredCube(double x, double y, double z, double width, double height, double depth, Color color) {
		
		super(x, y, z, width, height, depth);
		this.color = color;
	}
	
	public ColoredCube(Point3D p, double width, double height, double depth, Color color) {
		
		this(p.getX(), p.getY(), p.getZ(), width, height, depth, color);
	}
	
	public ColoredCube(double x, double y, double z, double width, double height, double depth) {
		
		this(x, y, z, width, height, depth, DEFAULT_COLOR);
	}
	
	public ColoredCube(Point3D p, double width, double height, double depth) {
		
		this(p.getX(), p.getY(), p.getZ(), width, height, depth, DEFAULT_COLOR);
	}
	
	@Override
	public Property[] getProperties() {
		
		ArrayList<Property> properties = new ArrayList<>();
		//pos
		properties.add(new Property("\n", "\n"));
		
		properties.add(new Property("Position X", getPositionX(), new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setPositionX((double)parameter);} catch(ClassCastException cce) {cce.printStackTrace();}
			}
		}));
		properties.add(new Property("Position Y", getPositionY(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setPositionY((double)parameter);} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("Position Z", getPositionZ(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setPositionZ((double)parameter);} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("\n", "\n"));
		
		//rot
		properties.add(new Property("Rotation X", Math.toDegrees(getRotationX()),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setRotationX(Math.toRadians((double)parameter));} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("Rotation Y", Math.toDegrees(getRotationY()),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setRotationY(Math.toRadians((double)parameter));} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("Rotation Z", Math.toDegrees(getRotationZ()),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setRotationZ(Math.toRadians((double)parameter));} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("\n", "\n"));
		
		//size
		properties.add(new Property("Width", getWidth(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setWidth((double)parameter);setVertices(new Point3D[] {
						
						new Point3D(-(getWidth() / 2),(getHeight() / 2),(getDepth() / 2)),new Point3D((getWidth() / 2),(getHeight() / 2),(getDepth() / 2)),new Point3D(-(getWidth() / 2),(getHeight() / 2),-(getDepth() / 2)),new Point3D((getWidth() / 2),(getHeight() / 2),-(getDepth() / 2)),//top
						new Point3D(-(getWidth() / 2),-(getHeight() / 2),(getDepth() / 2)),new Point3D((getWidth() / 2),-(getHeight() / 2),(getDepth() / 2)),new Point3D(-(getWidth() / 2),-(getHeight() / 2),-(getDepth() / 2)),new Point3D((getWidth() / 2),-(getHeight() / 2),-(getDepth() / 2))//bottom)
				});} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("Height", getHeight(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setHeight((double)parameter);setVertices(new Point3D[] {
						
						new Point3D(-(getWidth() / 2),(getHeight() / 2),(getDepth() / 2)),new Point3D((getWidth() / 2),(getHeight() / 2),(getDepth() / 2)),new Point3D(-(getWidth() / 2),(getHeight() / 2),-(getDepth() / 2)),new Point3D((getWidth() / 2),(getHeight() / 2),-(getDepth() / 2)),//top
						new Point3D(-(getWidth() / 2),-(getHeight() / 2),(getDepth() / 2)),new Point3D((getWidth() / 2),-(getHeight() / 2),(getDepth() / 2)),new Point3D(-(getWidth() / 2),-(getHeight() / 2),-(getDepth() / 2)),new Point3D((getWidth() / 2),-(getHeight() / 2),-(getDepth() / 2))//bottom)
				});} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("Depth", getDepth(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setDepth((double)parameter);setVertices(new Point3D[] {
						
						new Point3D(-(getWidth() / 2),(getHeight() / 2),(getDepth() / 2)),new Point3D((getWidth() / 2),(getHeight() / 2),(getDepth() / 2)),new Point3D(-(getWidth() / 2),(getHeight() / 2),-(getDepth() / 2)),new Point3D((getWidth() / 2),(getHeight() / 2),-(getDepth() / 2)),//top
						new Point3D(-(getWidth() / 2),-(getHeight() / 2),(getDepth() / 2)),new Point3D((getWidth() / 2),-(getHeight() / 2),(getDepth() / 2)),new Point3D(-(getWidth() / 2),-(getHeight() / 2),-(getDepth() / 2)),new Point3D((getWidth() / 2),-(getHeight() / 2),-(getDepth() / 2))//bottom)
				});} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("\n", "\n"));
		return properties.toArray(new Property[properties.size()]);
	}
}
