package pl.mmajcherski.rps.ui.common;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class GradientJPanel extends JPanel {

	private static final long serialVersionUID = 2396136911899196256L;
	
	@Override
	protected void paintComponent(Graphics grphcs) {
		super.paintComponent(grphcs);
		
		Graphics2D g2d = (Graphics2D) grphcs;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setPaint(new GradientPaint(0, 0, getBackground(), 0, getHeight() / 3, Color.WHITE));
		g2d.fillRect(0, 0, getWidth(), getHeight() / 3);
		
		g2d.setPaint(Color.WHITE);
		g2d.fillRect(0, getHeight() / 3, getWidth(), getHeight() * 2 / 3);
		
		g2d.setPaint(new GradientPaint(0, getHeight() * 2 / 3, Color.WHITE, 0, getHeight(), getBackground()));
		g2d.fillRect(0, getHeight() * 2 / 3, getWidth(), getHeight());
	}
}
