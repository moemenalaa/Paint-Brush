import java.util.Date;
import java.applet.Applet;  
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


public	abstract class Shape {
	public Color color;
	public int x = 0;
	public int y = 0;
	Shape(int x, int y, Color color){
		this.x = x;
		this.y = y;
		this.color = color;
		
	}
}
