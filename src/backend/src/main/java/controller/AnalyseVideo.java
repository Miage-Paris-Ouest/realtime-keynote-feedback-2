package controller;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class AnalyseVideo {

	/**
	 * La fonction converti une matrice en image 
	 * @param m: la matrice à convertir
	 * @return une image 
	 */
	public static BufferedImage Mat2BufferedImage(Mat m) {

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

	/**
	 * 
	 * @param chemin : représente le chemin de la video
	 * @return	: un tableau d'entier qui represente le nombre d'étudiant qui suivent (t[i]) à l'instant i
	 */
	public  ArrayList<Integer> analyse(String chemin) {


		ArrayList<Integer> nb = new ArrayList<Integer>();

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		//Create new MAT object
		Mat frame = new Mat();
		//Create new VideoCapture object
		VideoCapture camera = new VideoCapture(chemin);
		String xmlFile = "xml/lbpcascade_frontalface.xml";
		//Create new JFrame object
		// JFrame jframe = new JFrame("Video Title");

		//Inform jframe what to do in the event that you close the program
		//  jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create a new JLabel object vidpanel
		// JLabel vidPanel = new JLabel();

		//assign vidPanel to jframe
		//  jframe.setContentPane(vidPanel);

		//set frame size
		// jframe.setSize(2000, 4000);

		//make jframe visible
		//  jframe.setVisible(true);
		MatOfRect faceDetection = new MatOfRect();
		while (camera.read(frame)) {
			//If next video frame is available
			if (camera.read(frame)) {
				//Create new image icon object and convert Mat to Buffered Image


				CascadeClassifier cc = new CascadeClassifier(xmlFile);


				cc.detectMultiScale(frame, faceDetection);
			/*	for(Rect rect: faceDetection.toArray()) {
					Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height) , new Scalar(0, 0, 255), 3);
				}*/
			//	System.out.println(String.format("Detected faces: %d", faceDetection.toArray().length));
				nb.add(faceDetection.toArray().length);
				//	ImageIcon image = new ImageIcon(Mat2BufferedImage(frame));
				//Update the image in the vidPanel
				//  vidPanel.setIcon(image);
				//Update the vidPanel in the JFrame
				//  vidPanel.repaint();

			}else {break;}
			
		}
	return nb ; 
	}






}
