package de.tiago.graphics3D;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import de.tiago.graphics3D.entities.Camera;
import de.tiago.graphics3D.entities.Entity;

public class Space3D {
	
	private JRaster3D raster;
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private Camera activeCamera;
	
	public Space3D(Camera camera) {
		
		activeCamera = camera;
		activeCamera.setSpace3D(this);
	}
	
	public static Point2D.Double rotate2D(double x, double y, double angle) {
		
		double nx,ny;//new x(y)
		nx = Math.cos(angle + atanf(y,x)) * Math.sqrt(x * x + y * y);
		ny = Math.sin(angle + atanf(y,x)) * Math.sqrt(x * x + y * y);
		return new Point2D.Double(nx,ny);
	}
	
	public static Point2D.Double rotate2D(Point2D.Double p, double angle) {
				
		return rotate2D(p.getX(), p.getY(), angle);
	}
	
	//atanFULLCIRCLE ranges from 0.0 to 2 pi
	private static double atanf(double opp, double adj) {
		
		double angle = Math.atan(opp / adj);
		
		if(adj < 0) {
			
			angle = Math.PI + angle;
		} else {
			
			angle = 2 * Math.PI + angle;
		}
		
		if(angle >= 2 * Math.PI) {
			
			angle -= 2 * Math.PI;
		}
		return angle;
	}
	
	@Deprecated
	public void setJRaster3D(JRaster3D raster) {this.raster = raster;}
	public JRaster3D getJRaster3D() {return raster;}
	
	public ArrayList<Entity> getEntities() {return entities;}
	public void addEntities(Entity... entities) {
		for(Entity entity : entities) {
			
			addEntity(entity);
		}
	}
	public void addEntity(Entity entity) {entity.setSpace3D(this);entities.add(entity);}
	//throws imposible index etc...
	public void removeEntity(int index) {entities.remove(index);}
	
	public Camera getActiveCamera() {return activeCamera;}
	public void setActiveCamera(Camera activeCamera) {activeCamera.setSpace3D(this);this.activeCamera = activeCamera;}
}
