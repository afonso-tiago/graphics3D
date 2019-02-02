package de.tiago.obj;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import de.tiago.graphics3D.JRaster3D;
import de.tiago.graphics3D.entities.Polygon3D;
import de.tiago.graphics3D.geometry.Point3D;
import de.tiago.util.Actionable;
import de.tiago.util.Categorizable;
import de.tiago.util.Property;

public class Plane extends Polygon3D implements Categorizable {

	private double width;
	private double height;
	
	private boolean showEdges = true;
	
	public Plane(double x, double y, double z, double width, double height) {

		super(x, y, z, new Point3D[] {
				new Point3D(width / 2,height / 2,0), new Point3D(-width / 2,height / 2,0), new Point3D(width / 2,-height / 2,0), new Point3D(-width / 2,-height / 2,0)
		});
		this.width = width;
		this.height = height;
	}
	
	public Plane(Point3D p, Dimension d) {
		
		this(p.getX(), p.getY(), p.getZ(), d.getHeight(), d.getWidth());
	}
	
	@Override
	public void render(Graphics2D g2d, JRaster3D raster) {
		
		Point[] pixelMap = getPixelMap();
		if(showEdges) {
			try {
				
				g2d.drawLine(pixelMap[0].x, pixelMap[0].y, pixelMap[1].x, pixelMap[1].y);
				g2d.drawLine(pixelMap[1].x, pixelMap[1].y, pixelMap[3].x, pixelMap[3].y);
				g2d.drawLine(pixelMap[3].x, pixelMap[3].y, pixelMap[2].x, pixelMap[2].y);
				g2d.drawLine(pixelMap[2].x, pixelMap[2].y, pixelMap[0].x, pixelMap[0].y);
			} catch(NullPointerException npe) {/*npe.printStackTrace();*/}
		}
	}
	
	public double getWidth() {return width;}
	public void setWidth(double width) {this.width = width;}

	public double getHeight() {return height;}
	public void setHeight(double height) {this.height = height;}

	public void showEdges(boolean state) {showEdges = state;}

	@Override
	public Property[] getProperties() {
		
		ArrayList<Property> properties = new ArrayList<>();
		
		properties.add(new Property("\n", "\n"));
		
		//pos
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
				try {setWidth((double)parameter);vertices = new Point3D[] {
						
						new Point3D(width / 2,height / 2,0), new Point3D(-width / 2,height / 2,0), new Point3D(width / 2,-height / 2,0), new Point3D(-width / 2,-height / 2,0)
				};} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("Height", getHeight(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setHeight((double)parameter);vertices = new Point3D[] {
						
						new Point3D(width / 2,height / 2,0), new Point3D(-width / 2,height / 2,0), new Point3D(width / 2,-height / 2,0), new Point3D(-width / 2,-height / 2,0)
				};} catch(ClassCastException cce) {}
			}
		}));
		
		properties.add(new Property("\n", "\n"));
		return properties.toArray(new Property[properties.size()]);
	}
}
