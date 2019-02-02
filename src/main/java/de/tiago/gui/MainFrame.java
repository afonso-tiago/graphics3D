package de.tiago.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.tiago.graphics3D.Space3D;
import de.tiago.graphics3D.entities.Camera;
import de.tiago.graphics3D.entities.Entity;

public class MainFrame extends JFrame {
	
	private JSplitPane contentSplitPane;
	
	private JSplitPane attributesSplitPane;
	
	private DefaultListModel<Entity> entityListModel; 
	private EntityList entityList;
	private JScrollPane listScrollPane;
	
	private PropertiesPanel propertiesPanel;
	private JScrollPane propertiesScrollPane;
	
	private Raster raster;
	
	public MainFrame(String title) {
		
		super(title);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {e.printStackTrace();}
		setSize(960,540);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		init();
		setVisible(true);
	}

	private void init() {

		//raster
		raster = new Raster(new Space3D(new Camera(0, 0, 0, Math.PI / 2, Math.PI / 2)));
		
		//properties
		propertiesPanel = new PropertiesPanel("No Selection!");
		propertiesPanel.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent ce) {
				
				repaint();
				entityList.validate();
			}
		});
		propertiesScrollPane = new JScrollPane(propertiesPanel);
		propertiesScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		
		//list
		entityListModel = new DefaultListModel<>();
		for(Entity entity : raster.getSpace3D().getEntities()) {
			
			entityListModel.addElement(entity);
		}
		entityList = new EntityList(entityListModel, propertiesPanel, raster);
		listScrollPane = new JScrollPane(entityList);

		//
		attributesSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, listScrollPane, propertiesScrollPane);
		attributesSplitPane.setResizeWeight(0.5);
		attributesSplitPane.setDividerLocation((int)(getHeight() * 0.50));
		
		//
		contentSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, raster, attributesSplitPane);
		contentSplitPane.setResizeWeight(1);
		contentSplitPane.setDividerLocation((int)(getWidth() * 0.80));
		
		add(contentSplitPane, BorderLayout.CENTER);
	}
}
