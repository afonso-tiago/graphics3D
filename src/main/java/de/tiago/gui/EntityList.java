package de.tiago.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardUpLeftHandler;

import de.tiago.graphics3D.JRaster3D;
import de.tiago.graphics3D.entities.Entity;
import de.tiago.util.Categorizable;

public class EntityList extends JList {
	
	private DefaultListModel entityListModel;
	
	private EntityListPopupMenu listPopupMenu;
	
	private PropertiesPanel propertiesPanel;
	
	private JRaster3D raster;
	
	//
	private Entity[] copyedSelection;
	
	public EntityList(DefaultListModel entityListModel, PropertiesPanel propertiesPanel, JRaster3D raster) {
		
		super(entityListModel);
		
		this.entityListModel = entityListModel;
		listPopupMenu = new EntityListPopupMenu(this);
		this.propertiesPanel = propertiesPanel;
		this.raster = raster;

		addListSelectionListener(new SelectionListener());
		addMouseListener(new PopupListener());
		addKeyListener(new MnemonicListener());
	}
	
	private class SelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent lse) {
			try {
				
				if((Categorizable)EntityList.this.getSelectedValue() != null) {
					
					propertiesPanel.showProperties(
							(Categorizable)EntityList.this.entityListModel.getElementAt(EntityList.this.getLeadSelectionIndex()));
					propertiesPanel.getParent().validate();
					//EntityList.this.getSelectedValuesList().get(EntityList.this.getSelectedValuesList().size() - 1)
				}
			} catch(ClassCastException cce) {
				propertiesPanel.showError("Unsupported Format!");
				propertiesPanel.getParent().validate();
			}
		}
	}

	private class PopupListener extends MouseAdapter {

		public void mousePressed(MouseEvent me) {
	        
			maybeShowPopup(me);
	    }

	    public void mouseReleased(MouseEvent me) {
	        
	    	maybeShowPopup(me);
	    }
	    
	    private void maybeShowPopup(MouseEvent me) {
	        
	    	if (me.isPopupTrigger()) {
	    		
	    		listPopupMenu.show(EntityList.this, me.getX(), me.getY());
	        }
	    }
	}
	
	private class MnemonicListener extends KeyAdapter {
		
		private Set<Integer> keyCodes = new HashSet<>();
		
		@Override
		public void keyPressed(KeyEvent ke) {
			
			keyCodes.add(ke.getKeyCode());
			if(ke.getKeyCode() == KeyEvent.VK_DELETE) {
				
				int[] indices = getSelectedIndices();
				if(indices.length != 0) {
					
					for(int i = indices.length - 1;i >= 0;i--) {
						
						getJRaster3D().getSpace3D().removeEntity(indices[i]);
					}
					entityListModel.removeRange(indices[0], indices[indices.length - 1]);
					
					getJRaster3D().repaint();
					getPropertiesPanel().showError("No Selection!");
					propertiesPanel.getParent().validate();
				}
			} else if(ke.getKeyCode() == KeyEvent.VK_C && keyCodes.contains(KeyEvent.VK_CONTROL)) {
				
				if(getSelectedIndices().length != 0) {
					
					setCopyedSelection((Entity[]) getSelectedValuesList().toArray(new Entity[getSelectedValuesList().size()]));
				}
			} else if(ke.getKeyCode() == KeyEvent.VK_V && keyCodes.contains(KeyEvent.VK_CONTROL)) {
				
				for(int i = 0; i < getCopyedSelection().length;i++) {
					
					Entity entity;
					try {
						entity = (Entity) getCopyedSelection()[i].clone();
						getJRaster3D().getSpace3D().addEntity(entity);
						entityListModel.addElement(entity);
					} catch (CloneNotSupportedException e) {e.printStackTrace();}	
				}
				
				getJRaster3D().repaint();
				setSelectedIndex(entityListModel.getSize() - 1);
			}
		}
		
		@Override
		public void keyReleased(KeyEvent ke) {
			
			keyCodes.remove(ke.getKeyCode());
		}
	}
	
	public JRaster3D getJRaster3D() {return raster;}
	
	public PropertiesPanel getPropertiesPanel() {return propertiesPanel;}
	
	public Entity[] getCopyedSelection() {return copyedSelection;}
	public void setCopyedSelection(Entity[] copyedSelection) {this.copyedSelection = copyedSelection;}
}
