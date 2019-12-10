<<<<<<< HEAD
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends Application {

	private BorderPane borderPane;
	private Button easyButton;
	private Button hardButton;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		borderPane = new BorderPane();
		Scene scene = new Scene(borderPane, 1000, 800);
		setUp();
		buttonEvent(primaryStage);
		primaryStage.setTitle("Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void setUp() throws FileNotFoundException {
		HBox hBox = new HBox();
		Image title = new Image("./images/title.png");
		ImageView titleImageView = new ImageView(title);
		VBox mainVBox = new VBox();
		mainVBox.setSpacing(20);
		Text welcomeText = new Text("Have fun, good luck!");
		welcomeText.setFont(Font.font(40));
		easyButton = new Button();
		hardButton = new Button();
		easyButton.setText("Easy model");
		easyButton.setFont(Font.font(20));
		hardButton.setText("Hard model");
		hardButton.setFont(Font.font(20));
		mainVBox.getChildren().add(titleImageView);
		mainVBox.getChildren().add(welcomeText);
		mainVBox.getChildren().add(easyButton);
		mainVBox.getChildren().add(hardButton);
		hBox.getChildren().add(mainVBox);
		hBox.setAlignment(Pos.CENTER);
		mainVBox.setAlignment(Pos.CENTER);
		borderPane.setCenter(hBox);

		// create a image
		Image image = new Image("./images/menu.jpeg");
=======
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends Application {

	private BorderPane borderPane;
	private Button easyButton;
	private Button hardButton;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		borderPane = new BorderPane();
		Scene scene = new Scene(borderPane, 1000, 800);
		setUp();
		buttonEvent(primaryStage);
		primaryStage.setTitle("Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void setUp() throws FileNotFoundException {
		HBox hBox = new HBox();
		FileInputStream in = new FileInputStream(
				"/Users/yunxiaohu/git/csc335-towerdef-markhardy-zhiyulong-donshawhu-jiaxukang/src/images/title.png");
		Image title = new Image(in);
		ImageView titleImageView = new ImageView(title);
		VBox mainVBox = new VBox();
		mainVBox.setSpacing(20);
		Text welcomeText = new Text("Have fun, good luck!");
		welcomeText.setFont(Font.font(40));
		easyButton = new Button();
		hardButton = new Button();
		easyButton.setText("Easy model");
		easyButton.setFont(Font.font(20));
		hardButton.setText("Hard model");
		hardButton.setFont(Font.font(20));
		mainVBox.getChildren().add(titleImageView);
		mainVBox.getChildren().add(welcomeText);
		mainVBox.getChildren().add(easyButton);
		mainVBox.getChildren().add(hardButton);
		hBox.getChildren().add(mainVBox);
		hBox.setAlignment(Pos.CENTER);
		mainVBox.setAlignment(Pos.CENTER);
		borderPane.setCenter(hBox);
		// need to change address
		FileInputStream input = new FileInputStream(
				"/Users/yunxiaohu/git/csc335-towerdef-markhardy-zhiyulong-donshawhu-jiaxukang/src/images/menu.jpeg");

		// create a image
		Image image = new Image(input);
>>>>>>> branch 'master' of https://github.com/csc335-fall-2019/csc335-towerdef-markhardy-zhiyulong-donshawhu-jiaxukang.git

		// create a background image
		BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

		// create Background
		Background background = new Background(backgroundimage);

		borderPane.setBackground(background);
	}

	public void buttonEvent(Stage stage) {
		easyButton.setOnAction((ActionEvent e1) -> {
			// load Easy model
			Platform.runLater(new Runnable() {
				public void run() {
					try {
						new View().start(new Stage());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			stage.hide();

		});
		hardButton.setOnAction((ActionEvent e2) -> {
			// load Hard model

//	//		stage.hide();
		});
	}

}
