package de.tiago.graphics3D;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;

import de.tiago.graphics3D.entities.Camera;

public class JRaster3D extends JComponent {
	
	private Space3D space;
	private ExecutorService executor;
	
	private int resizeMode = RESIZE_DEFAULT;
	public static final int RESIZE_DEFAULT = 0;
	public static final int RESIZE_FOV_MANIPULATION = 1;
	public static final int RESIZE_IGNORE_STRETCHING = 2;
	
	public JRaster3D(Space3D space) {
		
		super();
		this.space = space;
		this.space.setJRaster3D(this);
		
		setFocusable(true);
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				requestFocusInWindow();
				requestFocus();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		executor = Executors.newCachedThreadPool();
		
		final int packing = 1000;//(int)(space.getEntities().size() / 4);
		for(int i = 0;i < space.getEntities().size();i += packing) {
			
			final int si = i;//start i
			executor.submit(new Runnable() {
				
				@Override
				public void run() {
					
					for(int k = si;k < si + packing && k < space.getEntities().size();k++) {
						
						space.getEntities().get(k).update(JRaster3D.this);
						space.getEntities().get(k).render(g2d, JRaster3D.this);
					}
				}
			});
		}
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public Point coorToPix(double x, double y, double z) {
		
		Camera camera = space.getActiveCamera();
		
		int pwidth = getWidth();//paint Width
		int pheight = getHeight();
		
		double fov_x = camera.getFovHorizontal();
		double fov_y = camera.getFovVertical();
		if(resizeMode == RESIZE_DEFAULT) {
			
			if(pwidth > pheight) {
				
				pheight = pwidth;
			} else {
				
				pwidth = pheight;
			}
		} else if(resizeMode == RESIZE_FOV_MANIPULATION) {
			
			if(pwidth > pheight) {
				
				fov_x = 2*Math.atan((Math.tan(camera.getFovHorizontal() / 2) * pwidth) / pheight);
				fov_y = fov_x;
				pheight = pwidth;
			} else {
				
				pwidth = pheight;
			}
		}
		
		double deltaX, deltaY, deltaZ;
		deltaX = x - camera.getPosition().getX();
		deltaY = y - camera.getPosition().getY();
		deltaZ = z - camera.getPosition().getZ();
		
		// - 
			Point2D.Double ry,rx,rz;//rotation ...
			ry = Space3D.rotate2D(deltaX,deltaZ,camera.getRotationY());
			rx = Space3D.rotate2D(ry.getY(),deltaY,-camera.getRotationX());
			rz = Space3D.rotate2D(ry.getX(),rx.getY(),camera.getRotationZ());
			
			deltaX = rz.getX();
			deltaY = rz.getY();
			deltaZ = rx.getX();
		// - adjust position to rotation
		if(deltaZ < 0) {
			
			return null;
		}
		// -
			//order (xz_opp = deltaX not deltaZ)makes default rotation look to the front
			//FieldOfViewHalfHorizontalLength
			double fovHHLength = Math.tan(fov_x / 2) * deltaZ;
			
			int pix_x = (int)((fovHHLength + deltaX) * (pwidth / Math.abs(2 * fovHHLength)));
			if(pix_x > pwidth || pix_x < 0) {
				
				return null;
			}
		// - calc x pixel 
		
		// -
			//order (yz_opp = deltaY not deltaZ)makes default rotation look to the right
			//FieldOfViewHalfVerticalLength							
			double fovHVLength = Math.tan(fov_y / 2) * deltaZ;
			
			int pix_y = (int)((fovHVLength + deltaY) * (pheight / Math.abs(2 * fovHVLength)));
			pix_y = pheight - pix_y;//invert y
			if(pix_y > pheight || pix_y < 0) {
				
				return null;
			}
		// - calc y pixel
		
		if(resizeMode == RESIZE_DEFAULT || resizeMode == RESIZE_FOV_MANIPULATION) {
			
			return new Point(pix_x - ((pwidth - getWidth()) / 2),pix_y - ((pheight - getHeight()) / 2));
		}
		return new Point(pix_x,pix_y);
	}
	
	public Space3D getSpace3D() {return space;}
	public void setSpace3D(Space3D space) {this.space = space;}
	
	public void setResizeMode(int resizeMode) {this.resizeMode = resizeMode;}
	public int getResizeMode() {return resizeMode;}
}
