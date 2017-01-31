package cluedo;

import java.io.IOException;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.JOptionPane;

public class Main extends Application {
	private Image img;
	public static int[][][] correctMatrix;
	public static final int maxRuns = 10000000;

	public static void main(String[] args) throws IOException {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
//		String input = JOptionPane.showInputDialog("Path to image: ");
		String input = "circlcard.png";
		try{
			img = new Image(input);
		} catch(IllegalArgumentException e){
			JOptionPane.showMessageDialog(null, "Invalid Path", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		loadImageData(img);

		WritableImage wImg = new WritableImage((int) img.getWidth(), (int) img.getHeight());
		PixelWriter pW = wImg.getPixelWriter();

		ImageView iv1 = new ImageView(wImg);
		StackPane p = new StackPane();

		p.getChildren().add(iv1); // add imageView to stackPane
		stage.setScene(new Scene(p));
		
		
		stage.show();
		
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.exit(0);
            }
        });  

		printImageData(pW);

	}


	private void printImageData(PixelWriter pW) {
		int mult = 100;
		Random rand = new Random(1);
		int[][][] outMatrix = new int[correctMatrix.length][correctMatrix[0].length][3];
		int sD = 6;

		outMatrix[sD][0] = correctMatrix[sD][0];

		for (int x = sD; x < correctMatrix.length; x+=sD){
			for (int y = sD; y < correctMatrix[0].length; y+=sD){

				int res[] = new int[3];
				int currRand = rand.nextInt(mult);
				for (int i = 0; i < 3; i++){
					int correct = correctMatrix[x][y][i];
					int toUse = Math.abs(correct - outMatrix[x-sD][y][i]) < Math.abs(correct - outMatrix[x][y-sD][i]) ? outMatrix[x-sD][y][i] : outMatrix[x][y-sD][i];
//					int toUse = outMatrix[x-sD][y][i];
					double add = toUse < correct ? 1 : -1;
					int currRes = (int) Math.max(0, Math.min(255, currRand*add + toUse));
					res[i] = currRes;
				}
				outMatrix[x][y] = res;
				
				
				int[] uL = outMatrix[x-sD][y-sD];
				int[] uR = outMatrix[x][y-sD];
				int[] lL = outMatrix[x-sD][y];
				int[] lR = outMatrix[x][y];				

				int[] res2 = new int[3];
				for (int x2 = 0; x2 < sD; x2++){
					for (int y2 = 0; y2 < sD; y2++){
						for (int i = 0; i < 3; i++){
								int fy1 = (int) (uL[i] + ((uR[i]-uL[i])/(double)sD)*x2);
								int fy2 = (int) (lL[i] + ((lR[i]-lL[i])/(double)sD)*x2);
								res2[i] = (int) (fy1 + ((fy2 - fy1)/(double) sD)*y2);
							
						}
						outMatrix[x+x2-sD][y+y2-sD] = res2;

					}

				}
			}
		}
		
		
		for (int x = sD; x < correctMatrix.length; x++){
			for (int y = sD; y < correctMatrix[0].length; y++){
				pW.setColor(x, y, new Color(outMatrix[x][y][0]/255f, outMatrix[x][y][1]/255f, outMatrix[x][y][2]/255f, 1));

			}
		
		}
	}

	private void loadImageData(Image img) {
		PixelReader pReader = img.getPixelReader();
		correctMatrix = new int[(int) img.getWidth()][(int) img.getHeight()][3];
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int red = (int) (pReader.getColor(x, y).getRed() * 255);
				int green = (int) (pReader.getColor(x, y).getGreen() * 255);
				int blue = (int) (pReader.getColor(x, y).getBlue() * 255);
				correctMatrix[x][y][0] = red;
				correctMatrix[x][y][1] = green;
				correctMatrix[x][y][2] = blue;
			}
		}

	}



}
