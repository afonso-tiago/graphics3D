package de.tiago.gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import de.tiago.graphics3D.JRaster3D;
import de.tiago.graphics3D.Space3D;
import de.tiago.graphics3D.entities.Camera;
import de.tiago.graphics3D.entities.Cube;
import de.tiago.graphics3D.geometry.Point3D;
import de.tiago.obj.ColoredCube;
import de.tiago.obj.CubeArray;
import de.tiago.obj.Plane;

public class Raster extends JRaster3D {
	
	//camera attributes
	private final double CAMERA_NORMAL_SPEED = 0.2;
	private final double CAMERA_INCREASED_SPEED = CAMERA_NORMAL_SPEED * 2;
	private final double CAMERA_NORMAL_R_SPEED = Math.PI / 128;//rotation speed
	private final double CAMERA_INCREASED_R_SPEED = CAMERA_NORMAL_R_SPEED * 2;
	
	private double cameraSpeed = CAMERA_NORMAL_SPEED;//current camera speed
	private double cameraRSpeed = CAMERA_NORMAL_R_SPEED;
	
	private boolean blocked = false;
	
	public Raster(Space3D space) {
		
		super(space);
		setResizeMode(RESIZE_FOV_MANIPULATION);

		init();
		
		addKeyListener(new KeyCameraMotionListener());

		addMouseMotionListener(new MouseCameraMotionListener());
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent mwe) {
				
				if(mwe.getWheelRotation() == 1) {
					
					cameraSpeed -= cameraSpeed / 10;
				} else {
					
					cameraSpeed += cameraSpeed / 10;
				}
			}
		});
		
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				
				blocked = true;
				//Raster.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				repaint();
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				
				blocked = false;
				/*
				try {
					new Robot().mouseMove(Raster.this.getLocationOnScreen().x + (getWidth() / 2), Raster.this.getLocationOnScreen().y + (getHeight() / 2));
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				//Raster.this.setCursor(getToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), null));
				repaint();
			}
		});
	}
	
	private void init() {
		
		
		getSpace3D().addEntity(new CubeArray(new Point3D(0, -20, 0), 3, 1, 3, 40, 1, 40, 1, 1, 1));
		getSpace3D().addEntity(new CubeArray(new Point3D(80, 0, 0), 1, 12, 1, 1, 3, 25, 1, 1, 5));
		getSpace3D().addEntity(new CubeArray(new Point3D(-80, 0, 0), 1, 12, 1, 1, 3, 25, 1, 1, 5));
		getSpace3D().addEntity(new CubeArray(new Point3D(0, 00, 80), 1, 12, 1, 25, 3, 1, 5, 1, 1));
		getSpace3D().addEntity(new CubeArray(new Point3D(0, 0, -80), 1, 12, 1, 25, 3, 1, 5, 1, 1));
		
		/*
		for(int i = 1;i < 30;i++) {
			
			getSpace3D().addEntity(new ColoredCube(new Point3D(0, 0, 0), i,i,i));
		}
		CubeArray ca1 = new CubeArray(new Point3D(0, -100, 0), 10,1,10, 20,1,20, 4,4,4);
		ca1.rotateY(Math.PI / 4);
		getSpace3D().addEntity(ca1);
		*/
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		if(blocked) {
			
			g.setColor(g.getColor().brighter().brighter());
		}
		super.paintComponent(g);
		if(blocked) {
			
			g.setColor(new Color(0,0,0,50));
			g.fillRect(0, 0, getWidth(), getHeight());
			
			g.setColor(Color.DARK_GRAY);
			g.drawRect(5, 5, getWidth() - 10, getHeight() - 10);
		}
	}
	
	private class MouseCameraMotionListener extends MouseAdapter {
		
		@Override
		public void mouseDragged(MouseEvent me) {
			
		}

		@Override
		public void mouseMoved(MouseEvent me) {
			/*
			if(!blocked) {
				
				Camera cam = Raster.this.getSpace3D().getActiveCamera();
	
				double deltaX = (getWidth()/2) - me.getPoint().x;
				double deltaY = (getHeight()/2) - me.getPoint().y;
				
				try {
					new Robot().mouseMove(Raster.this.getLocationOnScreen().x + (getWidth() / 2), Raster.this.getLocationOnScreen().y + (getHeight() / 2));
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				cam.rotateX(deltaY * (cameraRSpeed / 4));
				cam.rotateY(-deltaX * (cameraRSpeed / 4));
				
				Raster.this.repaint();
			}
			*/
		}
	}
	
	private class KeyCameraMotionListener implements KeyListener {
		
		private Set<Integer> keyCodes = new HashSet<>();
		private int updateFrequency = 17;
		
		private Thread holdThread = new Thread();
		
		@Override
		public void keyPressed(KeyEvent ke) {
			
			if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
				
				Raster.this.transferFocus();
			} else {
				
				if(getKeyCodes().size() <= 0 && !holdThread.isAlive()) {
					
					holdThread = new Thread(new Runnable() {
						
						@Override
						public void run() {
							
							while(getKeyCodes().size() > 0) {

								action();
								try {
									Thread.sleep(updateFrequency);
								} catch (InterruptedException e) {e.printStackTrace();}
							}
						}
					});
					holdThread.start();
				}
				addKeyCode(ke.getKeyCode());
			}
		}
		
		@Override
		public void keyReleased(KeyEvent ke) {
			
			removeKeyCode(ke.getKeyCode());
			/*
			if(ke.getKeyCode() == KeyEvent.VK_SHIFT) {
				
				cameraSpeed = CAMERA_NORMAL_SPEED;
				cameraRSpeed = CAMERA_NORMAL_R_SPEED;
			}
			*/
		}

		@Override
		public void keyTyped(KeyEvent ke) {}
		
		//synchronized to avoid ConcurrentModificationException
		//occurs when removing, adding or getting Items of the List happen at the same time 
		private synchronized void addKeyCode(int keyCode) {keyCodes.add(keyCode);}
		private synchronized void removeKeyCode(int keyCode) {keyCodes.remove(keyCode);}
		private synchronized Set<Integer> getKeyCodes() {return keyCodes;}
		
		//
		private synchronized void action() {
			
			Camera cam = Raster.this.getSpace3D().getActiveCamera();
			int repaint = 0;//to check if repaint is necessary
			
			//KeyHandling resembles First-Person-Shooter (not a Flying Simulator)
			for(int kc : getKeyCodes()) {
				
				switch(kc) {
				
					//speed increase
					//case KeyEvent.VK_SHIFT: cameraSpeed = CAMERA_INCREASED_SPEED; cameraRSpeed = CAMERA_INCREASED_R_SPEED;repaint++;break;
					//movement
					case KeyEvent.VK_W: cam.transform(Math.sin(cam.getRotationY()) * cameraSpeed, 
							0, Math.cos(cam.getRotationY()) * cameraSpeed);break;
							//W Key: forward movement considers only rot Y
					case KeyEvent.VK_S: cam.transform(-Math.sin(cam.getRotationY()) * cameraSpeed, 
							0, -Math.cos(cam.getRotationY()) * cameraSpeed);break;
							//S Key: backward movement considers only rot Y
					case KeyEvent.VK_D: cam.transform(Math.cos(-cam.getRotationY()) * cameraSpeed, 
							0, Math.sin(-cam.getRotationY()) * cameraSpeed);break;
							//D Key: right movement considers only rot Y
					case KeyEvent.VK_A: cam.transform(-Math.cos(-cam.getRotationY()) * cameraSpeed, 
							0, -Math.sin(-cam.getRotationY()) * cameraSpeed);break;
							//A Key: left movement considers only rot Y
					case KeyEvent.VK_SPACE: cam.transformY(cameraSpeed);break;
					case KeyEvent.VK_CONTROL: cam.transformY(-cameraSpeed);break;
					//rotation
					case KeyEvent.VK_DOWN: cam.rotateX(cameraRSpeed);break;
					case KeyEvent.VK_UP: cam.rotateX(-cameraRSpeed);break;
					case KeyEvent.VK_RIGHT: cam.rotateY(cameraRSpeed);break;
					case KeyEvent.VK_LEFT: cam.rotateY(-cameraRSpeed);break;
					case KeyEvent.VK_E: cam.rotateZ(cameraRSpeed);break;
					case KeyEvent.VK_Q: cam.rotateZ(-cameraRSpeed);break;
					
					case KeyEvent.VK_PLUS: cam.setFovHorizontal(cam.getFovHorizontal() + Math.PI / 128);cam.setFovVertical(cam.getFovHorizontal() + Math.PI / 128);break;
					case KeyEvent.VK_MINUS: cam.setFovHorizontal(cam.getFovHorizontal() - Math.PI / 128);cam.setFovVertical(cam.getFovHorizontal()  - Math.PI / 128);break;
					default : repaint++;//nothing relevant was pressed
				}
			}
			
			//default got activated all the time
			if(repaint < getKeyCodes().size()) {

				Raster.this.repaint();
			}
		}
	}
	
	public void setBlocked(boolean state) {blocked = state;}
	
	public boolean isBlocked() {return blocked;}
}
