package com.view;

import com.model.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class CellImg {
	
	private int Border = 5;
	private int s = 0;	// length of one side
	private int t = 0;	// short side of triangle outside of each hex
	private int r = 0;	// radious of inscribed circle( center to middle of each side) r = h / 2.
	private int h = 0;	// height. Distance between center of two adjacent hexes.Distance between two opposite sides in a hex.
    private Color color = Color.white;
	public CellImg(int side){
		setSide(side);
	}
	
	public void setBorders(int b){
		Border = b;
	}
	
	public void setSide(int side){
		s = side;
		t = (int)(s/2);
		r = (int)(s * 0.8660254037844);
		h = 2 * r;
	}
	
	public void setHeight(int height){
		s = height;
		r = h/2;
		s = (int) (h / 1.73205);
		t = (int) (r / 1.73205);
	}
	
	public Polygon genHex(int x0,int y0){
		int x = x0 + Border;
		int y = y0 + Border;
		
		int[] cx, cy;
		
		cx = new int[] { x, x + r, x + r, x, x - r, x - r };
		cy = new int[] { y, y + t , y + t + s, y + s + t + t, y + t + s, y + t };
 		
		return new Polygon(cx,cy,6);
	}
	
	public void drawHex(int i,int j, Graphics2D g2,Color col){
		int x = i * h + (j%2) * h/2;
		int y = j * (s+t);
		Polygon poly = genHex(x, y);
        color = col;
		g2.setColor(color);
		g2.fillPolygon(poly);
		g2.drawPolygon(poly);
    }

    public void updateColor(Graphics2D g2, Color col){
        color = col;
    }


}
