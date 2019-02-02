package de.tiago.graphics3D.entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.text.DecimalFormat;

import de.tiago.graphics3D.JRaster3D;
import de.tiago.graphics3D.Space3D;
import de.tiago.graphics3D.geometry.Point3D;

public abstract class Entity implements Cloneable {
	
	private Space3D space;
	
	protected double pos_x;
	protected double pos_y;
	protected double pos_z;
	
	protected double rotation_x = 0;
	protected double rotation_y = 0;
	protected double rotation_z = 0;
	
	private Point[] pixelMap;
	
	public Entity(double x, double y, double z) {
			
		setPosition(x, y, z);
	}
	
	public Entity(Point3D p) {
		
		setPosition(p);
	}
	
	public Entity(double x, double y, double z, double rx, double ry, double rz) {
		
		this(x,y,z);
		setRotation(rx, ry, rz);
	}
	
	public Entity(Point3D p, double rx, double ry, double rz) {
		
		this(p.getX(), p.getY(), p.getZ());
		setRotation(rx, ry, rz);
	}
	
	public abstract void render(Graphics2D g2D, JRaster3D raster);
	public abstract void update(JRaster3D raster);
	
	@Deprecated
	public void setSpace3D(Space3D space) {this.space = space;}
	public Space3D getSpace3D() {return space;}
	
	public Point[] getPixelMap() {return pixelMap;}
	public void setPixelMap(Point[] pixelMap) {this.pixelMap = pixelMap;}
	
	@Override
	public String toString() {
		
		DecimalFormat f = new DecimalFormat("0.#");
		String s = String.valueOf(getClass());
		return s.substring(s.lastIndexOf('.') + 1) + (" [" + f.format(pos_x) + "," + f.format(pos_y) + "," + f.format(pos_z) + "]");
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}
	
	//relative
	public void transform(double x, double y, double z) {
		
		pos_x += x;
		pos_y += y;
		pos_z += z;
	}
	public void transformX(double x) {pos_x += x;}
	public void transformY(double y) {pos_y += y;}
	public void transformZ(double z) {pos_z += z;}
	
	public void rotate(double x, double y, double z) {
		
		rotation_x += x;
		rotation_y += y;
		rotation_z += z;
	}
	public void rotateX(double x) {rotation_x += x;}
	public void rotateY(double y) {rotation_y += y;}
	public void rotateZ(double z) {rotation_z += z;}
	
	//absolut
	public Point3D getPosition() {return new Point3D(pos_x, pos_y, pos_z);}
	public double getPositionX() {return pos_x;}
	public double getPositionY() {return pos_y;}
	public double getPositionZ() {return pos_z;}
	
	public void setPosition(Point3D p) {
		pos_x = p.getX();
		pos_y = p.getY();
		pos_z = p.getZ();
	}
	public void setPosition(double x, double y, double z) {
		pos_x = x;
		pos_y = y;
		pos_z = z;
	}
	public void setPositionX(double pos) {pos_x = pos;}
	public void setPositionY(double pos) {pos_y = pos;}
	public void setPositionZ(double pos) {pos_z = pos;}
	
	public double getRotationX() {return rotation_x;}
	public double getRotationY() {return rotation_y;}
	public double getRotationZ() {return rotation_z;}
	
	public void setRotation(double rx, double ry, double rz) {
		rotation_x = rx;
		rotation_y = ry;
		rotation_z = rz;
	}
	public void setRotationX(double rot) {rotation_x = rot;}
	public void setRotationY(double rot) {rotation_y = rot;}
	public void setRotationZ(double rot) {rotation_z = rot;}
}
