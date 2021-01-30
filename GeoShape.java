import java.util.Date;
import java.applet.Applet;  
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;



public class GeoShape extends Shape {
	int width = 0;
	int height = 0;
	boolean filled = false;
	GeoShape(int x, int y, int width, int height, Color color, boolean filled){
		super(x, y, color);
		this.width = width;
		this.height = height;
		this.filled = filled;
	}
}
