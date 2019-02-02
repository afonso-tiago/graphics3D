package de.tiago.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.tiago.graphics3D.entities.Entity;
import de.tiago.graphics3D.geometry.Point3D;
import de.tiago.obj.ColoredCube;
import de.tiago.obj.CubeArray;
import de.tiago.obj.Plane;

public class EntityListPopupMenu extends JPopupMenu {
	
	private EntityList entityList;
	private DefaultListModel<Entity> entityListModel;
	
	private JMenu newSubMenu;
	private JMenuItem newCubeItem;
	private JMenuItem newCubeArrayItem;
	private JMenuItem newPlaneItem;
	
	private JMenuItem deleteItem;
	private JMenuItem copyItem;
	private JMenuItem pasteItem;
	
	public EntityListPopupMenu(EntityList entityList) {
		
		super();
		this.entityList = entityList;
		entityListModel = (DefaultListModel)entityList.getModel();
		init();
	}

	private void init() {
		
		MenuListener ml = new MenuListener();
		
		newSubMenu = new JMenu("New");
		//
		newCubeItem = new JMenuItem("Cube");
		newCubeItem.addActionListener(ml);
		newSubMenu.add(newCubeItem);
		newCubeArrayItem = new JMenuItem("Cube Array");
		newCubeArrayItem.addActionListener(ml);
		newSubMenu.add(newCubeArrayItem);
		newPlaneItem = new JMenuItem("Plane");
		newPlaneItem.addActionListener(ml);
		newSubMenu.add(newPlaneItem);
		//
		add(newSubMenu);
		
		deleteItem = new JMenuItem("Delete");
		deleteItem.addActionListener(ml);
		deleteItem.setEnabled(false);
		add(deleteItem);
        
        copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(ml);
        copyItem.setEnabled(false);
		add(copyItem);
		
		pasteItem = new JMenuItem("Paste");
		pasteItem.addActionListener(ml);
		pasteItem.setEnabled(false);
        add(pasteItem);
	}
	
	@Override
	public void show(Component invoker, int x, int y) {
		
		if(entityList.getModel().getSize() >= 0) {
			
			deleteItem.setEnabled(true);
			copyItem.setEnabled(true);
		} else {
			
			deleteItem.setEnabled(false);
			copyItem.setEnabled(false);
		}
		if(entityList.getCopyedSelection() != null) {
			
			pasteItem.setEnabled(true);
		} else {
			
			pasteItem.setEnabled(false);
		}
		super.show(invoker, x, y);
	}
	
	@Override
	public void setVisible(boolean b) {
		
		if(!b) {
			
			if(entityList.getMousePosition() != null) {
				
				if(entityListModel.getSize() >= 1) {
					
					entityList.setSelectedIndex(entityList.locationToIndex(entityList.getMousePosition()));
				}
			}
		}
		super.setVisible(b);
	}

	private class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			
			Object source = ae.getSource();
			if(source == newCubeItem) {
				
				Entity entity = new ColoredCube(0, 0, 0, 1, 1, 1, ColoredCube.DEFAULT_COLOR);
				entityList.getJRaster3D().getSpace3D().addEntity(entity);
				entityList.getJRaster3D().repaint();
				entityListModel.addElement(entity);
				
				entityList.setSelectedIndex(entityListModel.getSize() - 1);
			} else if(source == newCubeArrayItem) {
				
				Entity entity = new CubeArray(new Point3D(0, 0, 0), 1, 3, 1);
				entityList.getJRaster3D().getSpace3D().addEntity(entity);
				entityList.getJRaster3D().repaint();
				entityListModel.addElement(entity);
				
				entityList.setSelectedIndex(entityListModel.getSize() - 1);
			} else if(source == newPlaneItem) {
				
				Entity entity = new Plane(0, 0, 0, 10, 10);
				entityList.getJRaster3D().getSpace3D().addEntity(entity);
				entityList.getJRaster3D().repaint();
				entityListModel.addElement(entity);
				
				entityList.setSelectedIndex(entityListModel.getSize() - 1);
			} else if(source == deleteItem) {
				
				int[] indices = entityList.getSelectedIndices();
				if(indices.length != 0) {
					
					for(int i = indices.length - 1;i >= 0;i--) {
						
						entityList.getJRaster3D().getSpace3D().removeEntity(indices[i]);
					}
					entityListModel.removeRange(indices[0], indices[indices.length - 1]);
					
					entityList.getJRaster3D().repaint();
					entityList.getPropertiesPanel().showError("No Selection!");
					entityList.getPropertiesPanel().getParent().validate();
				}
			} else if(source == copyItem) {
				
				if(entityList.getSelectedIndices().length != 0) {
					
					entityList.setCopyedSelection((Entity[]) entityList.getSelectedValuesList().toArray(new Entity[entityList.getSelectedValuesList().size()]));
				}
			} else if(source == pasteItem) {
				
				for(int i = 0; i < entityList.getCopyedSelection().length;i++) {
					
					Entity entity;
					try {
						entity = (Entity) entityList.getCopyedSelection()[i].clone();
						entityList.getJRaster3D().getSpace3D().addEntity(entity);
						entityListModel.addElement(entity);
					} catch (CloneNotSupportedException e) {e.printStackTrace();}	
				}
				
				entityList.getJRaster3D().repaint();
				entityList.setSelectedIndex(entityListModel.getSize() - 1);
			}
		}
	}
}
