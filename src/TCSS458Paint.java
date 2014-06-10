import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class TCSS458Paint extends JFrame
{
    int width;
    int height;
    int imageSize;
    int[] pixels;
    private int red;
    private int green;
    private int blue;
    
    void drawPixel(int x, int y, int r, int g, int b) {
        pixels[(height-y-1)*width*3+x*3] = r;
        pixels[(height-y-1)*width*3+x*3+1] = g;
        pixels[(height-y-1)*width*3+x*3+2] = b;                
    }
    
    void createImage() {
    
//        width = 512;
//        height = 512;
//        imageSize = width * height;
//        pixels = new int[imageSize * 3]; //786432
//        
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                drawPixel( x, y, x*255/width, y*255/height, 0);
//            }
//        }
//        for (int d = 0; d < width; d++) {
//            drawPixel( d, d, 255, 255, 255);
//        }
    	
    	
    	String fileName = null;
    	
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Please Enter a file name: ");
    	fileName = sc.next();
    	sc.close();
    	
    	File input = new File(fileName);
    	Scanner in = null;
    	
    	try{
    		in = new Scanner(input);
    		
    	} catch (FileNotFoundException e) {
    	}
    	
    	String command = null;
    	
    	while(in.hasNext()) {
    		command = in.next();
    		
    		if(command.equals("DIM")) {
    			width = in.nextInt();
    			height = in.nextInt();
    			
    			imageSize = height * width;
    	    	pixels = new int[imageSize * 3];
    	    	for (int i = 0;i < pixels.length; i++) {
    	    		pixels[i] = 255;
    	    	}
    			
    		} else if(command.equals("RGB")) {
    			red = (int)(in.nextFloat() *255);
    			green = (int)(in.nextFloat()*255);
    			blue = (int)(in.nextFloat()*255);
    			
    		} else if(command.equals("LINE")) {
    			int x1 = (int)((width - 1) * ((in.nextFloat() +1.0) /2.0));
    			int y1 = (int)((height - 1) * ((in.nextFloat() +1.0) /2.0));
    			int x2 = (int)((width - 1) * ((in.nextFloat() +1.0) /2.0));
    			int y2 = (int)((height - 1) * ((in.nextFloat() +1.0) /2.0));
    			
    			ArrayList<MyPoint> line = null;
    			if(Math.abs(x2-x1) >= Math.abs(y2 - y1)){
    				if (x1 >= x2) {
    					line = createLine(x2,y2,x1,y1);
    				} else {
    					line = createLine(x1,y1,x2,y2);
    				}
    			} else {
    				if(y1 >= y2) {
    					line = createLine(x2,y2,x1,y1);
    				} else {
    					line = createLine(x1,y1,x2,y2);
    				}
    			}
    			drawLine(line);
    			
    		} else if(command.equals("TRI")) {
    			int x1 = (int)((width - 1) * ((in.nextFloat() +1.0) /2.0));
    			int y1 = (int)((height - 1) * ((in.nextFloat() +1.0) /2.0));
    			int x2 = (int)((width - 1) * ((in.nextFloat() +1.0) /2.0));
    			int y2 = (int)((height - 1) * ((in.nextFloat() +1.0) /2.0));
    			int x3 = (int)((width - 1) * ((in.nextFloat() +1.0) /2.0));
    			int y3 = (int)((height - 1) * ((in.nextFloat() +1.0) /2.0));
    			
    			ArrayList<MyPoint> line1 = createLine(x1,y1,x2,y2);
    			ArrayList<MyPoint> line2 = createLine(x2,y2,x3,y3);
    			ArrayList<MyPoint> line3 = createLine(x3,y3,x1,y1);
    			
    			createTriangle(line1, line2, line3);
    			
    		} else {
    			System.err.println("The selected file is not formatted propperly.");
    		}
    	}
    }
    
    private ArrayList<MyPoint> createLine(int x1, int y1, int x2, int y2) {
    	ArrayList<MyPoint> points = new ArrayList<MyPoint>();
    	if (x1 == x2) {
    		while (y1 <= y2) {
    			points.add(new MyPoint(x1, y1));
    			y1++;
    		}
    	} else if (y1 == y2) {
    		while (x1 <= x2) {
    			points.add(new MyPoint(x1,y1));
    			x1++;
    		}
    	} else {
    		double m = (double)(y2-y1)/(double)(x2-x1);
    		points.add(new MyPoint(x1,y1));
    		if (m <= 1 && m >= -1) {
    			double y = y1;
    			for (int x = x1+1; x < x2; x++){
    				y+=m;
    				points.add(new MyPoint(x,(int)Math.round(y)));
    			}
    		
    		} else {
    			double x = x1;
    			for (int y= y1+1; y < y2; y++){
    				x = x + (1/m);
    				points.add(new MyPoint(y,(int)Math.round(x)));
    			}
    		}
    	}
    	
    	return points;
    }
    
    private void drawLine(ArrayList<MyPoint> line) {
    	
    	for(MyPoint p : line) {
    		drawPixel(p.getX(), p.getY(), red, green, blue);
    	}
    	
    }
    
    private void createTriangle(ArrayList<MyPoint> line1, ArrayList<MyPoint> line2, ArrayList<MyPoint> line3) {
    	MinMax[] triangle = new MinMax[height];
    	
    	for (MyPoint p : line1) {
    		if (triangle[p.getY()] == null) {
    			triangle[p.getY()] = new MinMax(p.getX(),p.getX());
    		} else {
    			if(triangle[p.getY()].getMin() > p.getX()) {
    				triangle[p.getY()].setMin(p.getX());
    			} else if(triangle[p.getY()].getMax() < p.getX()) {
    				triangle[p.getY()].setMax(p.getX());
    			}
    		}
    	}
    	for (MyPoint p : line2) {
    		if (triangle[p.getY()] == null) {
    			triangle[p.getY()] = new MinMax(p.getX(),p.getX());
    		} else {
    			if(triangle[p.getY()].getMin() > p.getX()) {
    				triangle[p.getY()].setMin(p.getX());
    			} else if(triangle[p.getY()].getMax() < p.getX()) {
    				triangle[p.getY()].setMax(p.getX());
    			}
    		}
    	}
    	for (MyPoint p : line3) {
    		if (triangle[p.getY()] == null) {
    			triangle[p.getY()] = new MinMax(p.getX(),p.getX());
    		} else {
    			if(triangle[p.getY()].getMin() > p.getX()) {
    				triangle[p.getY()].setMin(p.getX());
    			} else if(triangle[p.getY()].getMax() < p.getX()) {
    				triangle[p.getY()].setMax(p.getX());
    			}
    		}
    	}
    	
    	drawTriangle(triangle);
    }
    
    public void drawTriangle(MinMax[] triangle) {
    	for (int i = 0; i < height; i++) {
    		if (triangle[i] != null) {
    			for(int j = triangle[i].getMin(); j <= triangle[i].getMax(); j++) {
    				drawPixel(i,j, red, green, blue);
    			}
    		}
    	}
    }

    public TCSS458Paint() {
        createImage();
        getContentPane().add( createImageLabel(pixels) );
    }

    private JLabel createImageLabel(int[] pixels) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        raster.setPixels(0, 0, width, height, pixels);
        JLabel label = new JLabel( new ImageIcon(image) );
        return label;
    }

    public static void main(String args[]) {
        JFrame frame = new TCSS458Paint();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible( true );
    }
}
