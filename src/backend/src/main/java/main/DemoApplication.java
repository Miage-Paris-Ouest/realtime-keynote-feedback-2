package main;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.validation.constraints.Size;

import org.opencv.core.Point;
import org.opencv.*;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"controller","service"})
public class DemoApplication {
	/*private static BufferedImage ConvertMat2Image(Mat imgContainer){
	    MatOfByte byteMatData = new MatOfByte();
	    //image formatting
	    Imgcodecs.imencode(".avi", imgContainer,byteMatData);
	    // Convert to array
	    byte[] byteArray = byteMatData.toArray();
	    BufferedImage img= null;
	    try {
	        InputStream in = new ByteArrayInputStream(byteArray);
	        //load image
	        img=  ImageIO.read(in);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	    return img;
	}*/
	
	        public static void main(String[] args) throws IOException{
   // 		SpringApplication.run(DemoApplication.class, args);
	      
	        	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

	            //Create new MAT object
	            Mat frame = new Mat();

	            //Create new VideoCapture object
	            VideoCapture camera = new VideoCapture("D:\\Master_Miage\\java\\projet\\realtime\\realtime-keynote-feedback-2\\src\\backend\\images\\test5.mp4");
	    		String xmlFile = "xml/lbpcascade_frontalface.xml";

	            //Create new JFrame object
	            JFrame jframe = new JFrame("Video Title");

	            //Inform jframe what to do in the event that you close the program
	            jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	            //Create a new JLabel object vidpanel
	            JLabel vidPanel = new JLabel();

	            //assign vidPanel to jframe
	            jframe.setContentPane(vidPanel);

	            //set frame size
	            jframe.setSize(2000, 4000);

	            //make jframe visible
	            jframe.setVisible(true);
	            MatOfRect faceDetection = new MatOfRect();
	            while (true) {
	                //If next video frame is available
	                if (camera.read(frame)) {
	                    //Create new image icon object and convert Mat to Buffered Image
	                    

	    	    		CascadeClassifier cc = new CascadeClassifier(xmlFile);
	    	    		
	    	    		
	    	    		cc.detectMultiScale(frame, faceDetection);
	    	    		for(Rect rect: faceDetection.toArray()) {
	    	    			Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height) , new Scalar(0, 0, 255), 3);
	    	    		}
	    	    		System.out.println(String.format("Detected faces: %d", faceDetection.toArray().length));
	    	    		
	    	    		ImageIcon image = new ImageIcon(Mat2BufferedImage(frame));
	                    //Update the image in the vidPanel
	                    vidPanel.setIcon(image);
	                    //Update the vidPanel in the JFrame
	                    vidPanel.repaint();

	                }
	            }
	        }

	        public static BufferedImage Mat2BufferedImage(Mat m) {
	            //Method converts a Mat to a Buffered Image
	            int type = BufferedImage.TYPE_BYTE_GRAY;
	             if ( m.channels() > 1 ) {
	                 type = BufferedImage.TYPE_3BYTE_BGR;
	             }
	             int bufferSize = m.channels()*m.cols()*m.rows();
	             byte [] b = new byte[bufferSize];
	             m.get(0,0,b); // get all the pixels
	             BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
	             final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	             System.arraycopy(b, 0, targetPixels, 0, b.length);  
	             return image;
	            }
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	
	      /*
	        	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    		
	    		String imgFile = "D:\\Master_Miage\\java\\projet\\realtime\\realtime-keynote-feedback-2\\src\\backend\\images\\test3.jpg";
	    		Mat src = Imgcodecs.imread(imgFile);
	    		String xmlFile = "xml/lbpcascade_frontalface.xml";
	    		VideoCapture camera = new VideoCapture("D:\\\\Master_Miage\\\\java\\\\projet\\\\realtime\\\\realtime-keynote-feedback-2\\\\src\\\\backend\\\\images\\\\test5.avi");
	    	
	    		
	    		CascadeClassifier cc = new CascadeClassifier(xmlFile);
	    		
	    		MatOfRect faceDetection = new MatOfRect();
	    		cc.detectMultiScale(src, faceDetection);
	    		System.out.println(String.format("Detected faces: %d", faceDetection.toArray().length));
	    		
	    		for(Rect rect: faceDetection.toArray()) {
	    			Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height) , new Scalar(0, 0, 255), 3);
	    		}
	    		
	    		Imgcodecs.imwrite("images/messivsronaldo_out.png", src);
	    		System.out.println("Image Detection Finished");
        */
        /*	File f = new File("f.png");
        	 // Load Native Library
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            // image container object
            Mat imageArray = new Mat();
            // Video device acces
            VideoCapture videoDevice = new VideoCapture();
            // 0:Start default video device 1,2 etc video device id
            videoDevice.open(0);
            // is contected
            while (videoDevice.isOpened()) {
            // Get frame from camera
                videoDevice.read(imageArray);
                // image array
                System.out.println(imageArray.toString());
                // Release video device
                videoDevice.release();
                BufferedImage i = ConvertMat2Image(imageArray);
                System.out.print(i);
                ImageIO.write(i, "avi", f );
            } 
                System.out.println("Error.");
            */
        

			private static String Mat2bufferedImage(Mat frame) {
				// TODO Auto-generated method stub
				return null;
			}
}


	
	

