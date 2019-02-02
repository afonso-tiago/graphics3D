package de.tiago.graphics3D.entities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import de.tiago.graphics3D.JRaster3D;
import de.tiago.graphics3D.Space3D;
import de.tiago.graphics3D.geometry.Point3D;

public class Polygon3D extends Entity {

	protected Point3D[] vertices;
	
	private boolean showVertices = false;
	
	public Polygon3D(double x, double y, double z, Point3D[] vertices) {
		
		super(x, y, z);
		this.vertices = vertices;
	}
	
	public Polygon3D(Point3D p, Point3D[] vertices) {
		
		this(p.getX(), p.getY(), p.getZ(), vertices);
	}
	
	public Polygon3D(double x, double y, double z, double rx, double ry, double rz, Point3D[] vertices) {
		
		super(x, y, z, rx, ry, rz);
		this.vertices = vertices;
	}
	
	public Polygon3D(Point3D p, double rx, double ry, double rz, Point3D[] vertices) {
		
		this(p.getX(), p.getY(), p.getZ(), rx, ry, rz, vertices);
	}
	
	@Override
	public void render(Graphics2D g2d, JRaster3D raster) {
		
		for(Point p : getPixelMap()) {
			
			if(showVertices) {
				
				if(p != null) {
					if(p.x <= raster.getWidth() && p.y <= raster.getHeight()) {
						
						g2d.drawString("*", p.x, p.y);
					}
				}
			}
		}
	}
	
	@Override
	public void update(JRaster3D raster) {
		
		Point[] pixelMap = new Point[vertices.length];
		int c = 0;
		for(Point3D p : vertices) {
			
			Point2D.Double ry,rx,rz;//rotation ...
			ry = Space3D.rotate2D(p.getX(),p.getZ(),getRotationY());
			rx = Space3D.rotate2D(ry.getY(),p.getY(),-getRotationX());
			rz = Space3D.rotate2D(ry.getX(),rx.getY(),getRotationZ());
			
			double pos_x, pos_y, pos_z;
			pos_x = rz.getX();
			pos_y = rz.getY();
			pos_z = rx.getX();
			
			pos_x = pos_x + getPositionX();
			pos_y = pos_y + getPositionY();
			pos_z = pos_z + getPositionZ();
			
			Point pix = raster.coorToPix(pos_x, pos_y, pos_z);
			pixelMap[c++] = pix;
		}
		setPixelMap(pixelMap);
	}
	
	public Point3D[] getVetices() {return vertices;}
	public void setVertices(Point3D[] vertices) {this.vertices = vertices;}
	
	public void showVertices(boolean state) {showVertices = state;}
}
