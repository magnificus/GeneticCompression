package image2;

import java.io.IOException;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	private Image img;
	public static int[][] correctMatrix;
	public static final int maxRuns = 10000000;

	public static void main(String[] args) throws IOException {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		img = new Image("circlcard.png");

		loadImageData(img);

		CompressionEnvironment e = new CompressionEnvironment();

		WritableImage wImg = new WritableImage((int) img.getWidth(), (int) img.getHeight());
		PixelWriter pW = wImg.getPixelWriter();

		ImageView iv1 = new ImageView(wImg);
		StackPane p = new StackPane();
//		p.setPrefSize(100, 100);// set a default size for your stackpane

		p.getChildren().add(iv1); // add imageView to stackPane
//		StackPane.setAlignment(iv1, Pos.CENTER);// set it to the Center
												// Left(by default it's
												// on the center)
		stage.setScene(new Scene(p));
		stage.show();

		execute(stage, e, pW);

	}

	private void execute(Stage stage, CompressionEnvironment e, PixelWriter pW) {
		long pre = System.currentTimeMillis();

		new Thread(new Runnable() {
			@Override
			public void run() {
				long latest = 0;
				for (int i = 0; i < maxRuns; i++) {
					if (System.currentTimeMillis() - latest > 1000) {
						Task<Void> task = new Task<Void>() {
							@Override
							public Void call() throws Exception {
								updateImage(e, pW);
								stage.show();
								return null;

							}
						};
						new Thread(task).start();
						latest = System.currentTimeMillis();

					}
					e.SimulationTick();
					calcPrintTime(pre, i, maxRuns);

				}

			}

		}).start();
	}

	private void loadImageData(Image img) {
		PixelReader pReader = img.getPixelReader();
		correctMatrix = new int[(int) img.getWidth()][(int) img.getHeight()];
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int red = (int) (pReader.getColor(x, y).getRed() * 255);
				correctMatrix[x][y] = red;
			}
		}

	}

	private void updateImage(CompressionEnvironment e, PixelWriter pW) {
		int[][] best = e.getBest();
		for (int x = 0; x < img.getWidth(); ++x) {
			for (int y = 0; y < img.getHeight(); ++y) {
				double color = Math.min(1, (double) best[x][y] / 255);
				color = Math.max(0, color);
				pW.setColor(x, y,
						new Color(color, color, color, 1));
				// pW.setColor(x, y, new Color(pReader.getColor(x, y).getRed(),
				// pReader.getColor(x, y).getRed(),
				// pReader.getColor(x, y).getRed(), 1));
			}
		}
	}

	private void calcPrintTime(long pre, int i, int max) {
		long passed = System.currentTimeMillis() - pre;
		double pct = (double) i / max;

		long remaining = (long) ((1 - pct) * (passed / pct));

		long second = (remaining / 1000) % 60;
		long minute = (remaining / (1000 * 60)) % 60;
		long hour = (remaining / (1000 * 60 * 60)) % 24;

		String time = String.format("%02d:%02d:%02d", hour, minute, second);

		System.out.println("\t" + i + " of " + max + " Remaining: " + time);

	}

}
