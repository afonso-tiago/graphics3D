package de.tiago.graphics3D.entities;

import java.awt.Graphics2D;

import de.tiago.graphics3D.JRaster3D;
import de.tiago.graphics3D.geometry.Point3D;

public class Camera extends Entity {
	
	private double fov_x;//field of view (in rad)
	private double fov_y;//field of view (in rad)
	
	public Camera(double x, double y, double z, double fov_x, double fov_y) {
		
		super(x, y, z);
		this.fov_x = fov_x;
		this.fov_y = fov_y;
	}
	
	public Camera(Point3D p, double fov_x, double fov_y) {
		
		super(p.getX(), p.getY(), p.getZ());
		this.fov_x = fov_x;
		this.fov_y = fov_y;
	}
	
	public Camera(double x, double y, double z, double rx, double ry, double rz, double fov_x, double fov_y) {
		
		super(x, y, z, rx, ry, rz);
		this.fov_x = fov_x;
		this.fov_y = fov_y;
	}
	
	public Camera(Point3D p, double rx, double ry, double rz, double fov_x, double fov_y) {
		
		super(p.getX(), p.getY(), p.getZ(), rx, ry, rz);
		this.fov_x = fov_x;
		this.fov_y = fov_y;
	}
	
	@Override
	public void render(Graphics2D g2d, JRaster3D raster) {}
	
	@Override
	public void update(JRaster3D raster) {}
	
	public double getFovHorizontal() {return fov_x;}
	public void setFovHorizontal(double fov_x) {this.fov_x = fov_x;}
	
	public double getFovVertical() {return fov_y;}
	public void setFovVertical(double fov_y) {this.fov_y = fov_y;}
}
