package de.tiago.obj;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import de.tiago.graphics3D.JRaster3D;
import de.tiago.graphics3D.entities.Cube;
import de.tiago.graphics3D.entities.Entity;
import de.tiago.graphics3D.geometry.Dimension3D;
import de.tiago.graphics3D.geometry.Point3D;
import de.tiago.util.Actionable;
import de.tiago.util.Categorizable;
import de.tiago.util.Property;

public class CubeArray extends Entity implements Categorizable {

	private double width;
	private double height;
	private double depth;
	
	private Point3D[] cubeVertices;
	
	private int columns;
	private int rows;	
	private int layers;
	
	private double margin_x;
	private double margin_y;
	private double margin_z;
	
	private boolean showEdges = true;
	
	public CubeArray(Point3D p,double width, double height, double depth, int columns, int rows, int layers, double margin_x, double margin_y, double margin_z) {
		
		super(p.getX(), p.getY(),p.getZ());
		this.width = width;
		this.height = height;
		this.depth = depth;
		
		cubeVertices = new Point3D[]{
				
				new Point3D(-(width / 2),(height / 2),(depth / 2)),new Point3D((width / 2),(height / 2),(depth / 2)),new Point3D(-(width / 2),(height / 2),-(depth / 2)),new Point3D((width / 2),(height / 2),-(depth / 2)),//top
				new Point3D(-(width / 2),-(height / 2),(depth / 2)),new Point3D((width / 2),-(height / 2),(depth / 2)),new Point3D(-(width / 2),-(height / 2),-(depth / 2)),new Point3D((width / 2),-(height / 2),-(depth / 2))//bottom)
		};
		
		this.rows = rows;
		this.columns = columns;
		this.layers = layers;
		
		this.margin_x = margin_x;
		this.margin_y = margin_y;
		this.margin_z = margin_z;
	}
	
	public CubeArray(Point3D p, Dimension3D dimensions, Dimension3D amount, Dimension3D margin) {
		this(p, dimensions.getWidth(), dimensions.getHeight(), dimensions.getDepth(), (int)amount.getWidth(), (int)amount.getHeight(), (int)amount.getDepth(), margin.getWidth(), margin.getHeight(), margin.getDepth());
	}
	
	public CubeArray(Point3D p, double size, int amount, double spacing) {
		
		this(p, size, size, size, amount, amount, amount, spacing, spacing, spacing);
	}

	@Override
	public void render(Graphics2D g2d, JRaster3D raster) {
		
		Point[] pixelMap = getPixelMap();
		for(int i = 0;i < (rows * columns * layers);i++) {
			
			if(showEdges) {
				try {
					
					//top
					g2d.drawLine(pixelMap[0 + (i*8)].x, pixelMap[0 + (i*8)].y, pixelMap[1 + (i*8)].x, pixelMap[1 + (i*8)].y);
					g2d.drawLine(pixelMap[1 + (i*8)].x, pixelMap[1 + (i*8)].y, pixelMap[3 + (i*8)].x, pixelMap[3 + (i*8)].y);
					g2d.drawLine(pixelMap[3 + (i*8)].x, pixelMap[3 + (i*8)].y, pixelMap[2 + (i*8)].x, pixelMap[2 + (i*8)].y);
					g2d.drawLine(pixelMap[2 + (i*8)].x, pixelMap[2 + (i*8)].y, pixelMap[0 + (i*8)].x, pixelMap[0 + (i*8)].y);
					//middle
					g2d.drawLine(pixelMap[0 + (i*8)].x, pixelMap[0 + (i*8)].y, pixelMap[4 + (i*8)].x, pixelMap[4 + (i*8)].y);
					g2d.drawLine(pixelMap[1 + (i*8)].x, pixelMap[1 + (i*8)].y, pixelMap[5 + (i*8)].x, pixelMap[5 + (i*8)].y);
					g2d.drawLine(pixelMap[3 + (i*8)].x, pixelMap[3 + (i*8)].y, pixelMap[7 + (i*8)].x, pixelMap[7 + (i*8)].y);
					g2d.drawLine(pixelMap[2 + (i*8)].x, pixelMap[2 + (i*8)].y, pixelMap[6 + (i*8)].x, pixelMap[6 + (i*8)].y);
					//bottom
					g2d.drawLine(pixelMap[4 + (i*8)].x, pixelMap[4 + (i*8)].y, pixelMap[5 + (i*8)].x, pixelMap[5 + (i*8)].y);
					g2d.drawLine(pixelMap[5 + (i*8)].x, pixelMap[5 + (i*8)].y, pixelMap[7 + (i*8)].x, pixelMap[7 + (i*8)].y);
					g2d.drawLine(pixelMap[7 + (i*8)].x, pixelMap[7 + (i*8)].y, pixelMap[6 + (i*8)].x, pixelMap[6 + (i*8)].y);
					g2d.drawLine(pixelMap[6 + (i*8)].x, pixelMap[6 + (i*8)].y, pixelMap[4 + (i*8)].x, pixelMap[4 + (i*8)].y);
				} catch(NullPointerException npe) {/*npe.printStackTrace();*/}
			}
		}
	}

	@Override
	public void update(JRaster3D raster) {
		
		Point[] pixelMap = new Point[8 * rows * columns * layers];
		double sz = -(((layers - 1)) * (depth + margin_z));//start x etc...
		double sy = -(((rows - 1)) * (height + margin_y));
		double sx = -(((columns - 1)) * (width + margin_x));
		for(int z = 0;z < layers;z++) {
			
			for(int y = 0;y < rows;y++) {
				
				for(int x = 0;x < columns;x++) {
					
					Cube cube = new Cube((sx / 2)  + (x*(width+margin_x)) + getPositionX(),
							 (sy / 2) + (y*(height+margin_y)) + getPositionY(),
							 (sz / 2) + (z*(depth+margin_z)) + getPositionZ(),
							  width,height,depth);
					cube.rotate(getRotationX(), getRotationY(), getRotationZ());
					cube.update(getSpace3D().getJRaster3D());
					Point[] pix = cube.getPixelMap();
					int c = 0;
					for(Point p : pix) {
						
						pixelMap[(((x*8)+(y*columns*8)+(z*columns*rows*8)))+c] = p;
						c++;
					}
				}
			}
		}
		setPixelMap(pixelMap);
	}

	//dimensions
	public double getWidth() {return width;}
	public void setWidth(double width) {this.width = width;}

	public double getHeight() {return height;}
	public void setHeight(double height) {this.height = height;}

	public double getDepth() {return depth;}
	public void setDepth(double depth) {this.depth = depth;}
	
	public Dimension3D getDimensions() {return new Dimension3D(width, height, depth);}
	public void setDimensions(Dimension3D dimensions) {width = dimensions.getWidth();height = dimensions.getHeight();depth = dimensions.getDepth();}
	
	//amount
	public int getColumns() {return columns;}
	public void setColumns(int columns) {this.columns = columns;}

	public int getRows() {return rows;}
	public void setRows(int rows) {this.rows = rows;}

	public int getLayers() {return layers;}
	public void setLayers(int layers) {this.layers = layers;}

	public Dimension3D getAmount() {return new Dimension3D(columns, rows, layers);}
	public void setAmount(Dimension3D amount) {columns = (int) amount.getWidth();rows = (int) amount.getHeight();layers = (int) amount.getDepth();}
	
	//margin
	public double getMarginX() {return margin_x;}
	public void setMarginX(double margin_x) {this.margin_x = margin_x;}

	public double getMarginY() {return margin_y;}
	public void setMarginY(double margin_y) {this.margin_y = margin_y;}

	public double getMarginZ() {return margin_z;}
	public void setMarginZ(double margin_z) {this.margin_z = margin_z;}

	public Dimension3D getMargin() {return new Dimension3D(margin_x, margin_y, margin_z);}
	public void setMargin(Dimension3D margin) {margin_x = margin.getWidth();margin_y = margin.getHeight();margin_z = margin.getDepth();}
	
	public void showEdges(boolean state) {showEdges = state;}
	
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
				try {setRotationX(Math.toRadians((double)parameter));} catch(ClassCastException cce) {cce.printStackTrace();}
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
				try {setWidth((double)parameter);cubeVertices = new Point3D[] {
						
						new Point3D(-(getWidth() / 2),(getHeight() / 2),(getDepth() / 2)),new Point3D((getWidth() / 2),(getHeight() / 2),(getDepth() / 2)),new Point3D(-(getWidth() / 2),(getHeight() / 2),-(getDepth() / 2)),new Point3D((getWidth() / 2),(getHeight() / 2),-(getDepth() / 2)),//top
						new Point3D(-(getWidth() / 2),-(getHeight() / 2),(getDepth() / 2)),new Point3D((getWidth() / 2),-(getHeight() / 2),(getDepth() / 2)),new Point3D(-(getWidth() / 2),-(getHeight() / 2),-(getDepth() / 2)),new Point3D((getWidth() / 2),-(getHeight() / 2),-(getDepth() / 2))//bottom)
				};} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("Height", getHeight(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setHeight((double)parameter);cubeVertices = new Point3D[] {
						
						new Point3D(-(getWidth() / 2),(getHeight() / 2),(getDepth() / 2)),new Point3D((getWidth() / 2),(getHeight() / 2),(getDepth() / 2)),new Point3D(-(getWidth() / 2),(getHeight() / 2),-(getDepth() / 2)),new Point3D((getWidth() / 2),(getHeight() / 2),-(getDepth() / 2)),//top
						new Point3D(-(getWidth() / 2),-(getHeight() / 2),(getDepth() / 2)),new Point3D((getWidth() / 2),-(getHeight() / 2),(getDepth() / 2)),new Point3D(-(getWidth() / 2),-(getHeight() / 2),-(getDepth() / 2)),new Point3D((getWidth() / 2),-(getHeight() / 2),-(getDepth() / 2))//bottom)
				};} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("Depth", getDepth(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setDepth((double)parameter);cubeVertices = new Point3D[] {
						
						new Point3D(-(getWidth() / 2),(getHeight() / 2),(getDepth() / 2)),new Point3D((getWidth() / 2),(getHeight() / 2),(getDepth() / 2)),new Point3D(-(getWidth() / 2),(getHeight() / 2),-(getDepth() / 2)),new Point3D((getWidth() / 2),(getHeight() / 2),-(getDepth() / 2)),//top
						new Point3D(-(getWidth() / 2),-(getHeight() / 2),(getDepth() / 2)),new Point3D((getWidth() / 2),-(getHeight() / 2),(getDepth() / 2)),new Point3D(-(getWidth() / 2),-(getHeight() / 2),-(getDepth() / 2)),new Point3D((getWidth() / 2),-(getHeight() / 2),-(getDepth() / 2))//bottom)
				};} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("\n", "\n"));
		
		//amount
		properties.add(new Property("Columns", getColumns(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setColumns((int)((double)parameter));} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("Rows", getRows(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setRows((int)((double)parameter));} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("Layers", getLayers(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setLayers((int)((double)parameter));} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("\n", "\n"));
		
		//margin
		properties.add(new Property("Margin X", getMarginX(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setMarginX((double)parameter);} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("Margin Y", getMarginY(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setMarginY((double)parameter);} catch(ClassCastException cce) {}
			}
		}));
		properties.add(new Property("Margin Z", getMarginZ(),  new Actionable() {
			@Override
			public void action(Object parameter) {
				try {setMarginZ((double)parameter);} catch(ClassCastException cce) {}
			}
		}));
		
		properties.add(new Property("\n", "\n"));
		return properties.toArray(new Property[properties.size()]);
	}
}
