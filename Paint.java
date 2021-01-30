import java.util.Stack;
import java.applet.Applet;  
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


public class Paint extends Applet implements MouseMotionListener, MouseListener{
	
	int x1 = 0;
	int y1 = 0;
	int x2 = 0;
	int y2 = 0;
	int xx2 = 0;
	int yy2 = 0;
	int tempx1 = 0;
	int tempy1 = 0;
	int counter = 0;
	int counter1 = 0;
	Graphics gf,ge;
	
	boolean lineF = true, circleF = false, rectangleF = false, fillF = false, drag = false, freehandF = false, eraseF = false;
	Vector<Shape> shapes = new Vector<>();
	Stack<Shape> deletedShapes = new Stack<>();
	
	Color currentColor = Color.BLACK;
	
	public void init(){
		this.addMouseMotionListener(this);
        addMouseListener(this);
		Button lineButton = new Button("|");
		Button circleButton = new Button("O");
		Button rectangleButton = new Button("[]");
		Button freehandButton = new Button("S");
		Button erase = new Button("erase");
		Button red = new Button("");
		red.setBackground(Color.red);
		Button green = new Button("");
		green.setBackground(Color.green);
		Button blue = new Button("");
		blue.setBackground(Color.blue);
		Button undo = new Button("Undo");
		Button redo = new Button("Redo");
		Button clearall = new Button("Clear All");
		Checkbox fill = new Checkbox("fill");
		lineButton.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ev){
										lineF = true;
										circleF = false;
										rectangleF = false;
										freehandF = false;
										eraseF = false;
									}
								});
		add(lineButton);

		circleButton.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ev){
										lineF = false;
										circleF = true;
										rectangleF = false;
										freehandF = false;
										eraseF = false;
									}
								});
		add(circleButton);
		rectangleButton.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ev){
										lineF = false;
										circleF = false;
										rectangleF = true;
										freehandF = false;
										eraseF = false;
									}
								});
		add(rectangleButton);

		freehandButton.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ev){
										lineF = false;
										circleF = false;
										rectangleF = false;
										freehandF = true;
										eraseF = false;
									}
								});
		add(freehandButton);
		
		erase.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ev){
										lineF = false;
										circleF = false;
										rectangleF = false;
										freehandF = false;
										eraseF = true;
									}
								});
		add(erase);
		
		red.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ev){
										currentColor = Color.RED;
									}
								});
		add(red);

		green.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ev){
										currentColor = Color.GREEN;
									}
								});
		add(green);
		blue.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ev){
										currentColor = Color.BLUE;
									}
								});
		add(blue);
		
		undo.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ev){
										if (shapes.size() > 0 & eraseF){
											int sz = shapes.size();
											for(int i = 0; i<counter; i++){
												deletedShapes.push(shapes.remove(shapes.size()-1));	
											
											}
											repaint();
											eraseF = false;
											counter=0;
											
										}

										else if (shapes.size() > 0){
											deletedShapes.push(shapes.remove(shapes.size()-1));
											repaint();
										}
									}
								});
		add(undo);
		redo.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ev){
										if (!deletedShapes.isEmpty()){
											shapes.add(deletedShapes.pop());
											repaint();
										}
									}
								});
		add(redo);
		clearall.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent ev){
										while(shapes.size() > 0){
											deletedShapes.push(shapes.remove(shapes.size()-1));
										}
										repaint();
									}
								});
		add(clearall);
		fill.addItemListener(new ItemListener(){
									public void itemStateChanged(ItemEvent ev){
										fillF = !fillF;
									}
								});
		add(fill);
	}	
	public void mouseDragged(MouseEvent e) {
		gf = getGraphics();
		ge = getGraphics();
		int tempx2 = e.getX();
		int tempy2 = e.getY();		
		drag = true;
		if(circleF || rectangleF){
			if(tempx1 > tempx2) {
				x1 = tempx2;
				xx2 = tempx1;
			}
			else{
				x1 = tempx1;
				xx2 = tempx2;
			}
			if(tempy1 > tempy2) {
				y1 = tempy2;
				yy2 = tempy1;
			}
			else{
				y1 = tempy1;
				yy2 = tempy2;
			}
			
		}
		else if(freehandF){
			Line line = new Line(tempx1, tempy1, tempx2, tempy2, currentColor);
			shapes.add(line);
			paint(gf);
			tempx1 = tempx2;
			tempy1 = tempy2;
		}
		else if(eraseF){
			counter++;
			gf.setColor(Color.WHITE);
			Rectangle era = new Rectangle(tempx2,tempy2,5, 5, Color.WHITE, true);
			shapes.add(era);
			paint(ge);
		}
		else {
			xx2 = tempx2;
			yy2 = tempy2;
		}

		repaint();			
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }


    public void mousePressed(MouseEvent e) {
		if(!drag){
			x1=e.getX();
			y1=e.getY();
			tempx1 = x1;
			tempy1 = y1;
			
			if(freehandF){
				x1 = x2;
				y1 = y2;
			}

			
		}
    }

    public void mouseReleased(MouseEvent e) {
		if(drag){
			x2 = xx2;
			y2 = yy2;
			if(lineF){
				shapes.add(new Line(x1, y1, x2, y2, currentColor));
				drag = false;
			}
			else if(rectangleF){
				shapes.add(new Rectangle(x1,y1,Math.abs(x2 - x1),Math.abs(y2 - y1),currentColor, fillF));
				drag = false;
			}
			else if(circleF){
				shapes.add(new Circle(x1,y1,Math.abs(x2 - x1),Math.abs(y2 - y1),currentColor, fillF));
				drag = false;
			}
			else if(freehandF){
				drag = false;
			}
			else if(eraseF) {
				drag = false;
			}
			repaint();
			
		}
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

	public void paint(Graphics g) {
		if(drag){
			g.setColor(currentColor);
			if(lineF) g.drawLine(x1,y1,xx2,yy2);
			else if(rectangleF){
				g.drawRect(x1,y1,Math.abs(xx2-x1),Math.abs(yy2-y1));
			}
			else if(circleF){
				g.drawOval(x1,y1,Math.abs(xx2-x1),Math.abs(yy2-y1));
			}
		}

		

		for(int i = 0; i<shapes.size(); i++){
			drawObject(g, shapes.get(i));
		}
    }
	
	private void drawObject(Graphics g, Shape shape) {
		g.setColor(shape.color);
			switch(shape.getClass().getName()){
			case("Line"):
				Line line =(Line) shape;
				g.drawLine(line.x,line.y,line.x2,line.y2);
				break;
			case("Circle"):
				Circle circle =(Circle) shape;
				if(!circle.filled) g.drawOval(circle.x, circle.y, circle.width, circle.height);
				else g.fillOval(circle.x, circle.y, circle.width, circle.height);
				break;
			case("Rectangle"):
				Rectangle rectangle =(Rectangle) shape;
				if(!rectangle.filled) g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
				else g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
				break;
			}
	}
}


					
					
