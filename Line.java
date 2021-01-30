import java.util.Date;
import java.applet.Applet;  
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;



public class Line extends Shape{
	
	int y2;
	int x2;
	
	Line(int x, int y, int x2, int y2, Color color){
		super(x, y, color);
		this.x2 = x2;
		this.y2 = y2;
	}
	
}

