package main;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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

import controller.AnalyseVideo;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"controller","service"})
public class DemoApplication {


	public static void main(String[] args) throws IOException{
		// 		SpringApplication.run(DemoApplication.class, args);

		AnalyseVideo analyse = new AnalyseVideo();
		ArrayList<Integer> nb = new ArrayList<>();
		nb=analyse.analyse("D:\\\\Master_Miage\\\\java\\\\projet\\\\realtime\\\\realtime-keynote-feedback-2\\\\src\\\\backend\\\\images\\\\test5.mp4");
		for (Integer i : nb) {
			System.out.println(i.toString());

		}
	}

}





