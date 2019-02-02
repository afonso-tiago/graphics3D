package de.tiago.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.tiago.graphics3D.JRaster3D;
import de.tiago.util.Categorizable;
import de.tiago.util.Property;

public class PropertiesPanel extends JPanel {
	
	private Set<ChangeListener> changeListeners = new HashSet<>();
	
	public PropertiesPanel(String message) {
		
		super();
		showError(message);
	}
	
	public void showProperties(Categorizable categorizable) {
		
		removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.anchor = GridBagConstraints.LINE_START;
		gbc.weightx = 1;
		gbc.gridy = 0;
		for(final Property p : categorizable.getProperties()) {
			
			add(new JLabel(p.getDescription()),gbc);
			add(Box.createHorizontalGlue(),gbc);
			if(p.isMutable()) {
				
				final JTextField field = new JTextField();
				field.setText(p.getValue().toString());
				field.setPreferredSize(new Dimension(40,field.getPreferredSize().height));
				field.addCaretListener(new CaretListener() {
					
					@Override
					public void caretUpdate(CaretEvent arg0) {
						try {
							
							p.executeAction(Double.parseDouble(field.getText()));
							for(ChangeListener l : changeListeners) {
								
								l.stateChanged(new ChangeEvent(field));
							}
						} catch(Exception e) {}
					}
				});
				add(field,gbc);
			} else {
				
				add(new JLabel(p.getValue().toString()),gbc);
			}
			gbc.gridy++;
		}
		validate();
		repaint();
	}
	
	public void showError(String errorMessage) {
		
		removeAll();
		setLayout(new GridBagLayout());
		add(new JLabel(errorMessage));
		validate();
		repaint();
	}
	
	public void addChangeListener(ChangeListener l) {
		
		changeListeners.add(l);
	}
}
