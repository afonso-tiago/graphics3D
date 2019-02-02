package de.tiago.graphics3D.entities;

import java.awt.Graphics2D;
import java.awt.Point;

import de.tiago.graphics3D.JRaster3D;
import de.tiago.graphics3D.geometry.Dimension3D;
import de.tiago.graphics3D.geometry.Point3D;

public class Cube extends Polygon3D {

	//points order
	//      2		3
	//
	// 0		1
	//      6		7
	//
	// 4		5
	
	protected double width;
	protected double height;
	protected double depth;
	
	private boolean showEdges = true;
	
	public Cube(double x, double y, double z, double width, double height, double depth) {
		
		super(x,y,z,new Point3D[] {
			
				new Point3D(-(width / 2),(height / 2),(depth / 2)),new Point3D((width / 2),(height / 2),(depth / 2)),new Point3D(-(width / 2),(height / 2),-(depth / 2)),new Point3D((width / 2),(height / 2),-(depth / 2)),//top
				new Point3D(-(width / 2),-(height / 2),(depth / 2)),new Point3D((width / 2),-(height / 2),(depth / 2)),new Point3D(-(width / 2),-(height / 2),-(depth / 2)),new Point3D((width / 2),-(height / 2),-(depth / 2))//bottom)
		});
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	public Cube(Point3D p, double width, double height, double depth) {
		
		this(p.getX(), p.getY(), p.getZ(), width, height, depth);
	}
	
	public Cube(Point3D p, Dimension3D d) {
		
		this(p,d.getWidth(),d.getHeight(),d.getDepth());
	}
	
	@Override
	public void render(Graphics2D g2d, JRaster3D raster) {
		
		Point[] pixelMap = getPixelMap();
		if(showEdges) {
			try {
				
				//top
				g2d.drawLine(pixelMap[0].x, pixelMap[0].y, pixelMap[1].x, pixelMap[1].y);
				g2d.drawLine(pixelMap[1].x, pixelMap[1].y, pixelMap[3].x, pixelMap[3].y);
				g2d.drawLine(pixelMap[3].x, pixelMap[3].y, pixelMap[2].x, pixelMap[2].y);
				g2d.drawLine(pixelMap[2].x, pixelMap[2].y, pixelMap[0].x, pixelMap[0].y);
				//middle
				g2d.drawLine(pixelMap[0].x, pixelMap[0].y, pixelMap[4].x, pixelMap[4].y);
				g2d.drawLine(pixelMap[1].x, pixelMap[1].y, pixelMap[5].x, pixelMap[5].y);
				g2d.drawLine(pixelMap[3].x, pixelMap[3].y, pixelMap[7].x, pixelMap[7].y);
				g2d.drawLine(pixelMap[2].x, pixelMap[2].y, pixelMap[6].x, pixelMap[6].y);
				//bottom
				g2d.drawLine(pixelMap[4].x, pixelMap[4].y, pixelMap[5].x, pixelMap[5].y);
				g2d.drawLine(pixelMap[5].x, pixelMap[5].y, pixelMap[7].x, pixelMap[7].y);
				g2d.drawLine(pixelMap[7].x, pixelMap[7].y, pixelMap[6].x, pixelMap[6].y);
				g2d.drawLine(pixelMap[6].x, pixelMap[6].y, pixelMap[4].x, pixelMap[4].y);
			} catch(NullPointerException npe) {/*npe.printStackTrace();*/}
		}
	}
	
	@Override
	public void update(JRaster3D raster) {
	
		super.update(raster);
	}
	
	public double getWidth() {return width;}
	public void setWidth(double width) {this.width = width;}

	public double getHeight() {return height;}
	public void setHeight(double height) {this.height = height;}

	public double getDepth() {return depth;}
	public void setDepth(double depth) {this.depth = depth;}
	
	public Dimension3D getDimensions() {return new Dimension3D(width, height, depth);}
	
	public void showEdges(boolean state) {showEdges = state;}
}
