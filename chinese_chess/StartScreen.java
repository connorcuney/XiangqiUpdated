import Piece.*;
import javafx.application.Application;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseDragEvent;
import java.util.Collections;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
/*
 * This class is in charge of setting up all the different screens throughout the game.
 */
public class StartScreen extends Application{
	final double RATIO = 0.6;
	private Scene game_screen = null, main_menu = null, start_screen = null, accessbility_menu = null,
			account_menu = null, login_menu = null, register_menu = null, xiangqi_rules_menu = null;
	int game = 0, tutorial_game = 0, rules_game = 0;
	Board board = null;
	boolean togglePiece = false;
	boolean toggleAccess = false;
	
	Accounts newAccount = new Accounts();
	String currentAccount = null;

	DecimalFormat df = new DecimalFormat("00.0");
	
//Setup stage which is the Main frame used for all screens
	public void start(Stage primaryStage){
		primaryStage.setTitle("Chinese Chess");
		primaryStage.setWidth(1920 * RATIO);
		primaryStage.setHeight(1115 * RATIO);
		primaryStage.setResizable(false);
		setupStartMenu(primaryStage);
		primaryStage.setScene(start_screen);
		primaryStage.show();
	}
//Initial Screen the user sees before logging in
	public void setupStartMenu(Stage primaryStage){
		ImageView background = createImageView("/Images/startBackground.gif", true);
		background.setPreserveRatio(false);

		ImageView mainLogo = createImageView("/Images/mainLogo.png");
		mainLogo.setFitHeight(700);
		mainLogo.setFitWidth(700);

		Button start_button = createButton("/Images/StartTest.png");
		start_button.setStyle("-fx-focus-color:  firebrick;");
		start_button.setOnMouseClicked(e -> {
			setupLoginMenu(primaryStage);
			primaryStage.setScene(login_menu);
		});

		Button quit_button = createButton("/Images/QuitTest.png");
		quit_button.setStyle("-fx-focus-color:  firebrick;");
		quit_button.setOnMouseClicked(e -> {
			System.exit(0);
		});

		ImageView pieceLogo = createImageView("/Images/xiangqiPieceImage.png");
		pieceLogo.setFitHeight(550);
		pieceLogo.setFitWidth(550);

		Pane pane = new Pane();
		pane.getChildren().addAll(background, mainLogo, start_button, quit_button, pieceLogo);
		mainLogo.relocate(380 * RATIO, 20 * RATIO);
		start_button.relocate(640 * RATIO, 380 * RATIO);
		quit_button.relocate(1010 * RATIO, 380 * RATIO);
		pieceLogo.relocate(510 * RATIO, 580 * RATIO);
		start_screen = new Scene(pane);
		primaryStage.setScene(start_screen);
	}
//Setup Xiangqi board
	public void setupGameScreen(Stage primaryStage){
		ImageView background = createImageView("/Images/GameBoard2.png", true);
		Button quit_button = createButton("/Images/QuitTest.png");
		quit_button.setStyle("-fx-focus-color: firebrick;");
		quit_button.setOnMouseClicked(e -> {
			primaryStage.setScene(main_menu);;
		});

		Pane pane = new Pane();
		pane.getChildren().addAll(background, quit_button);
		quit_button.relocate(1300 * RATIO, 850 * RATIO);
		board = new Board(pane, primaryStage, main_menu, togglePiece, toggleAccess);
		board.setUsername(currentAccount);
		game_screen = new Scene(pane);

	}

	public void setupLoginMenu(Stage primaryStage) {
		ImageView background = createImageView("/Images/mainBackgroundGradient.png", true);

		ImageView loginLogo = createImageView("/Images/loginLogo.png");
		loginLogo.setFitHeight(250);
		loginLogo.setFitWidth(250);

		Font koreanFont = Font.loadFont(getClass().getResource("/Fonts/Korean_Calligraphy.ttf").toExternalForm(), 50);
		Font spaceFont = Font.loadFont(getClass().getResource("/Fonts/ethnocentric.ttf").toExternalForm(), 22);

		Label usernameText = new Label("Username:");
		usernameText.setFont(koreanFont);

		//GET THE USERNAME 
		TextField usernameTextField = new TextField();
		usernameTextField.setStyle("-fx-focus-color:  firebrick;");
		usernameTextField.setPrefWidth(550);
		usernameTextField.setPrefHeight(50);
		usernameTextField.setFont(new Font("Arial", 20));

		Label passwordText = new Label("Password:");
		passwordText.setFont(koreanFont);

		//GET THE PASSWORD 
		PasswordField passwordTextField = new PasswordField();
		passwordTextField.setStyle("-fx-focus-color:  firebrick;");
		passwordTextField.setPrefWidth(550);
		passwordTextField.setPrefHeight(50);
		passwordTextField.setFont(new Font("Arial", 20));

		Button loginButton = new Button("Login");
		loginButton.setStyle("-fx-focus-color: firebrick;");
		loginButton.setPrefHeight(50);
		loginButton.setPrefWidth(150);
		loginButton.setFont(spaceFont);
		loginButton.setOnMouseClicked(e -> {
			// IF LOGIN SUCCESSFUL
			try {
				if(newAccount.userInfo(usernameTextField.getText(), passwordTextField.getText()) == false) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.initStyle(StageStyle.UTILITY);
					alert.setHeaderText(null);
					alert.setContentText("Invalid Username or Password.");
					alert.show();
				}else {
					newAccount.loadUserInfo(usernameTextField.getText(), passwordTextField.getText());
					this.currentAccount = newAccount.getUserName();
					setupMainMenu(primaryStage);
					primaryStage.setScene(main_menu);
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		Button registerButton = new Button("Register");
		registerButton.setStyle("-fx-focus-color: firebrick;");
		registerButton.setPrefHeight(50);
		registerButton.setPrefWidth(190);
		registerButton.setFont(spaceFont);
		registerButton.setOnMouseClicked(e -> {
			setupRegisterMenu(primaryStage);
			primaryStage.setScene(register_menu);
		});

		ImageView elephant = createImageView("/Images/elephant.png");
		elephant.setFitWidth(269);
		elephant.setFitWidth(222);

		Pane pane = new Pane();
		pane.getChildren().addAll(background, loginLogo, loginButton, usernameText, usernameTextField, passwordText, 
				passwordTextField, registerButton, elephant);
		loginLogo.relocate(775 * RATIO, 80 * RATIO);
		usernameText.relocate(225 * RATIO, 250 * RATIO);
		usernameTextField.relocate(600 * RATIO, 275 * RATIO);
		passwordText.relocate(225 * RATIO, 375 * RATIO);
		passwordTextField.relocate(600 * RATIO, 400 * RATIO);
		loginButton.relocate(700 * RATIO, 525 * RATIO);
		registerButton.relocate(1025 * RATIO, 525 * RATIO);
		elephant.relocate(795 * RATIO, 680 * RATIO);
		login_menu = new Scene(pane);
	}

	public void setupRegisterMenu(Stage primaryStage){
		ImageView background = createImageView("/Images/mainBackgroundGradient.png", true);

		Font koreanFont = Font.loadFont(getClass().getResource("/Fonts/Korean_Calligraphy.ttf").toExternalForm(), 50);
		Font spaceFont = Font.loadFont(getClass().getResource("/Fonts/ethnocentric.ttf").toExternalForm(), 22);

		ImageView registerLogo = createImageView("/Images/createAccount.png");
		registerLogo.setFitHeight(650);
		registerLogo.setFitWidth(650);
		
		Label usernameText = new Label("Username:");
		usernameText.setFont(koreanFont);

		Label usernameRules = new Label("Username must be distinct from any other usernames.");
		usernameRules.setFont(new Font("Arial", 16));
		usernameRules.setStyle("-fx-text-fill: red;");
		
		//GET REGISTER USERNAME 
		TextField usernameTextField = new TextField();
		usernameTextField.setStyle("-fx-focus-color:  firebrick;");
		usernameTextField.setPrefWidth(550);
		usernameTextField.setPrefHeight(50);
		usernameTextField.setFont(new Font("Arial", 20));
		
		Label passwordText = new Label("Password:");
		passwordText.setFont(koreanFont);

		//GET THE REGISTER PASSWORD 
		PasswordField passwordTextField = new PasswordField();
		passwordTextField.setStyle("-fx-focus-color:  firebrick;");
		passwordTextField.setPrefWidth(550);
		passwordTextField.setPrefHeight(50);
		passwordTextField.setFont(new Font("Arial", 20));
		
		Label confirmPasswordText = new Label("Confirm Pass:");
		confirmPasswordText.setFont(koreanFont);

		//GET THE REGISTER PASSWORD 
		PasswordField confirmPasswordTextField = new PasswordField();
		confirmPasswordTextField.setStyle("-fx-focus-color:  firebrick;");
		confirmPasswordTextField.setPrefWidth(550);
		confirmPasswordTextField.setPrefHeight(50);
		confirmPasswordTextField.setFont(new Font("Arial", 20));
		
		Button registerButton = new Button("Register");
		registerButton.setStyle("-fx-focus-color: firebrick;");
		registerButton.setPrefHeight(90);
		registerButton.setPrefWidth(190);
		registerButton.setFont(spaceFont);
		registerButton.setOnMouseClicked(e -> {
			// IF REGISTER VALID
			newAccount.setUserName(usernameTextField.getText());
			try {
				if (newAccount.checkUsername(newAccount.getUserName()) == true) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.initStyle(StageStyle.UTILITY);
					alert.setHeaderText(null);
					alert.setContentText("Username already exists.");
					alert.show();
				}else if(!(passwordTextField.getText().equals(confirmPasswordTextField.getText()))){
					Alert alert = new Alert(AlertType.WARNING);
					alert.initStyle(StageStyle.UTILITY);
					alert.setHeaderText(null);
					alert.setContentText("Passwords do not match.");
					alert.show();
				}else {
					
					newAccount.setPassword(passwordTextField.getText());
					newAccount.setLoss(0);
					newAccount.setWins(0);
					newAccount.setGamesPlayed(newAccount.getWins() + newAccount.getLoss());
					
					try {
						FileWriter fr = new FileWriter(newAccount.accounts, true);
						fr.append(newAccount.getUserName() + "," + newAccount.getPassword() + "," + newAccount.getWins() + "," + newAccount.getLoss() + "\n");
						fr.close();
						this.currentAccount = newAccount.getUserName();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					setupMainMenu(primaryStage);
					primaryStage.setScene(main_menu);
				}
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		Button backButton = new Button("Back");
		backButton.setStyle("-fx-focus-color: firebrick;");
		backButton.setPrefHeight(90);
		backButton.setPrefWidth(190);
		backButton.setFont(spaceFont);
		backButton.setOnMouseClicked(e -> {
			setupLoginMenu(primaryStage);
			primaryStage.setScene(login_menu);
		});
		
		Pane pane = new Pane();
		pane.getChildren().addAll(background, registerLogo, registerButton, usernameText, usernameTextField, passwordText, passwordTextField,
				confirmPasswordText, confirmPasswordTextField, usernameRules, backButton);
		registerLogo.relocate(450 * RATIO, 80 * RATIO);
		usernameText.relocate(225 * RATIO, 250 * RATIO);
		usernameRules.relocate(750 * RATIO, 375 * RATIO);
		usernameTextField.relocate(600 * RATIO, 275 * RATIO);
		passwordText.relocate(225 * RATIO, 450 * RATIO);
		passwordTextField.relocate(600 * RATIO, 475 * RATIO);
		confirmPasswordText.relocate(125 * RATIO, 650 * RATIO);
		confirmPasswordTextField.relocate(600 * RATIO, 675 * RATIO);
		registerButton.relocate(1025 * RATIO, 850 * RATIO);
		backButton.relocate(625 * RATIO, 850 * RATIO);
		register_menu = new Scene(pane);
	}

	public void setupMainMenu(Stage primaryStage) {

		ImageView background = createImageView("/Images/mainMenuBackground.png", true);

		ImageView mainLogo = createImageView("/Images/mainLogo.png");
		mainLogo.setFitHeight(700);
		mainLogo.setFitWidth(700);

		Button gamesButton = createButtonWithSize("/Images/xiangqiButton.png", 225, 225);
		gamesButton.setStyle("-fx-focus-color: firebrick;");
		gamesButton.setOnMouseClicked(e -> {
			setupGameScreen(primaryStage);
			primaryStage.setScene(game_screen);
		});

		Button leftGameButton = createButtonWithSize("/Images/leftArrow.png", 50, 50);
		leftGameButton.setStyle("-fx-focus-color:  firebrick;");
		leftGameButton.setOnMouseClicked(e -> {
			game = (game == 0) ? 2: game - 1;
			if(game == 0) {
				ImageView xiangqiTemp = createImageView("/Images/xiangqiButton.png");
				xiangqiTemp.setFitWidth(225);
				xiangqiTemp.setFitHeight(225);
				gamesButton.setGraphic(xiangqiTemp);
				gamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
						newAccount.setGamesPlayed(newAccount.getGamesPlayed() + 1);
						setupGameScreen(primaryStage);
						primaryStage.setScene(game_screen);
					} 
				}));
			}
			else if(game == 1) {
				ImageView shogiTemp = createImageView("/Images/shogiButton.png");
				shogiTemp.setFitWidth(225);
				shogiTemp.setFitHeight(225);
				gamesButton.setGraphic(shogiTemp);
				gamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
					} 
				}));
			}
			else if (game == 2) {
				ImageView goTemp = createImageView("/Images/goButton.png");
				goTemp.setFitWidth(225);
				goTemp.setFitHeight(225);
				gamesButton.setGraphic(goTemp);
				gamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
					} 
				}));
			}

		});

		Button rightGameButton = createButtonWithSize("/Images/rightArrow.png", 50, 50);
		rightGameButton.setStyle("-fx-focus-color:  firebrick;");
		rightGameButton.setOnMouseClicked(e -> {
			game = (game == 2) ? 0: game + 1;
			if(game == 0) {
				ImageView xiangqiTemp = createImageView("/Images/xiangqiButton.png");
				xiangqiTemp.setFitWidth(225);
				xiangqiTemp.setFitHeight(225);
				gamesButton.setGraphic(xiangqiTemp);
				gamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
						newAccount.setGamesPlayed(newAccount.getGamesPlayed() + 1);
						setupGameScreen(primaryStage);
						primaryStage.setScene(game_screen);
					} 
				}));
			}
			else if(game == 1) {
				ImageView shogiTemp = createImageView("/Images/shogiButton.png");
				shogiTemp.setFitWidth(225);
				shogiTemp.setFitHeight(225);
				gamesButton.setGraphic(shogiTemp);
				gamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
					} 
				}));
			}
			else if (game == 2) {
				ImageView goTemp = createImageView("/Images/goButton.png");
				goTemp.setFitWidth(225);
				goTemp.setFitHeight(225);
				gamesButton.setGraphic(goTemp);
				gamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
					} 
				}));
			}
		});

		Button accessibilityButton = createButtonWithSize("/Images/accessibilityButton.png", 225, 225);
		accessibilityButton.setStyle("-fx-focus-color: firebrick;");
		accessibilityButton.setOnMouseClicked(e -> {
			setupAccessibilityMenu(primaryStage);
			primaryStage.setScene(accessbility_menu);
		});

		Button accountButton = createButtonWithSize("/Images/accountButton.png", 225, 225);
		accountButton.setStyle("-fx-focus-color: firebrick;");
		accountButton.setOnMouseClicked(e -> {
			setupAccountMenu(primaryStage);
			primaryStage.setScene(account_menu);
		});

		Button quitButton = createButtonWithSize("/Images/quitButton.png", 225, 225);
		quitButton.setStyle("-fx-focus-color: firebrick;");
		quitButton.setOnMouseClicked(e -> {
			System.exit(0);
		});

		Pane pane = new Pane();
		pane.getChildren().addAll(background, mainLogo, gamesButton, leftGameButton, rightGameButton,
				accessibilityButton, accountButton, quitButton);
		mainLogo.relocate(380 * RATIO, 20 * RATIO);
		gamesButton.relocate(500 * RATIO, 320 * RATIO);
		leftGameButton.relocate(420 * RATIO, 430 * RATIO );
		rightGameButton.relocate(870 * RATIO, 430 * RATIO );
		accessibilityButton.relocate(1040 * RATIO, 320 * RATIO);
		accountButton.relocate(500 * RATIO, 680 * RATIO);
		quitButton.relocate(1040 * RATIO, 680 * RATIO);
		main_menu = new Scene(pane);

	}

	public void setupAccessibilityMenu(Stage primaryStage) {
		ImageView background = createImageView("/Images/mainBackgroundGradient.png", true);

		Font koreanFont = Font.loadFont(getClass().getResource("/Fonts/Korean_Calligraphy.ttf").toExternalForm(), 30);
		Font spaceFont = Font.loadFont(getClass().getResource("/Fonts/ethnocentric.ttf").toExternalForm(), 22);
		
		ImageView accessibilityLogo = createImageView("/Images/accessibilityLogo.png");
		accessibilityLogo.setFitHeight(650);
		accessibilityLogo.setFitWidth(650);
		
		Label rulesLabel = new Label("Show Game Rules");
		rulesLabel.setFont(koreanFont);
		
		Button rulesGamesButton = createButtonWithSize("/Images/xiangqiButton.png", 225, 225);
		rulesGamesButton.setStyle("-fx-focus-color: firebrick;");
		rulesGamesButton.setOnMouseClicked(e -> {
			setupXiangqiRulesScreen(primaryStage);
			primaryStage.setScene(xiangqi_rules_menu);
		});

		Button rulesLeftGameButton = createButtonWithSize("/Images/leftArrow.png", 50, 50);
		rulesLeftGameButton.setStyle("-fx-focus-color:  firebrick;");
		rulesLeftGameButton.setOnMouseClicked(e -> {
			rules_game = (rules_game == 0) ? 2: rules_game - 1;
			if(rules_game == 0) {
				ImageView xiangqiTemp = createImageView("/Images/xiangqiButton.png");
				xiangqiTemp.setFitWidth(225);
				xiangqiTemp.setFitHeight(225);
				rulesGamesButton.setGraphic(xiangqiTemp);
				rulesGamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
						setupXiangqiRulesScreen(primaryStage);
						primaryStage.setScene(xiangqi_rules_menu);
					} 
				}));
			}
			else if(rules_game == 1) {
				ImageView shogiTemp = createImageView("/Images/shogiButton.png");
				shogiTemp.setFitWidth(225);
				shogiTemp.setFitHeight(225);
				rulesGamesButton.setGraphic(shogiTemp);
				rulesGamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
					} 
				}));
			}
			else if (rules_game == 2) {
				ImageView goTemp = createImageView("/Images/goButton.png");
				goTemp.setFitWidth(225);
				goTemp.setFitHeight(225);
				rulesGamesButton.setGraphic(goTemp);
				rulesGamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
					} 
				}));
			}

		});

		Button rulesRightGameButton = createButtonWithSize("/Images/rightArrow.png", 50, 50);
		rulesRightGameButton.setStyle("-fx-focus-color:  firebrick;");
		rulesRightGameButton.setOnMouseClicked(e -> {
			rules_game = (rules_game == 2) ? 0: rules_game + 1;
			if(rules_game == 0) {
				ImageView xiangqiTemp = createImageView("/Images/xiangqiButton.png");
				xiangqiTemp.setFitWidth(225);
				xiangqiTemp.setFitHeight(225);
				rulesGamesButton.setGraphic(xiangqiTemp);
				rulesGamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
						setupXiangqiRulesScreen(primaryStage);
						primaryStage.setScene(xiangqi_rules_menu);
					} 
				}));
			}
			else if(rules_game == 1) {
				ImageView shogiTemp = createImageView("/Images/shogiButton.png");
				shogiTemp.setFitWidth(225);
				shogiTemp.setFitHeight(225);
				rulesGamesButton.setGraphic(shogiTemp);
				rulesGamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
					} 
				}));
			}
			else if (rules_game == 2) {
				ImageView goTemp = createImageView("/Images/goButton.png");
				goTemp.setFitWidth(225);
				goTemp.setFitHeight(225);
				rulesGamesButton.setGraphic(goTemp);
				rulesGamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
					} 
				}));
			}
		});
		
		Label tutorialLabel = new Label("Play tutorial");
		tutorialLabel.setFont(koreanFont);
		
		Button tutorialGamesButton = createButtonWithSize("/Images/xiangqiButton.png", 225, 225);
		tutorialGamesButton.setStyle("-fx-focus-color: firebrick;");
		tutorialGamesButton.setOnMouseClicked(e -> {
			//LAUNCH TUTORIAL 
			
		});

		Button tutorialLeftGameButton = createButtonWithSize("/Images/leftArrow.png", 50, 50);
		tutorialLeftGameButton.setStyle("-fx-focus-color:  firebrick;");
		tutorialLeftGameButton.setOnMouseClicked(e -> {
			tutorial_game = (tutorial_game == 0) ? 2: tutorial_game - 1;
			if(tutorial_game == 0) {
				ImageView xiangqiTemp = createImageView("/Images/xiangqiButton.png");
				xiangqiTemp.setFitWidth(225);
				xiangqiTemp.setFitHeight(225);
				tutorialGamesButton.setGraphic(xiangqiTemp);
				tutorialGamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
						//LAUNCH TUTORIAL 
						
					} 
				}));
			}
			else if(tutorial_game == 1) {
				ImageView shogiTemp = createImageView("/Images/shogiButton.png");
				shogiTemp.setFitWidth(225);
				shogiTemp.setFitHeight(225);
				tutorialGamesButton.setGraphic(shogiTemp);
				tutorialGamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
					} 
				}));
			}
			else if (tutorial_game == 2) {
				ImageView goTemp = createImageView("/Images/goButton.png");
				goTemp.setFitWidth(225);
				goTemp.setFitHeight(225);
				tutorialGamesButton.setGraphic(goTemp);
				tutorialGamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
					} 
				}));
			}

		});

		Button tutorialRightGameButton = createButtonWithSize("/Images/rightArrow.png", 50, 50);
		tutorialRightGameButton.setStyle("-fx-focus-color:  firebrick;");
		tutorialRightGameButton.setOnMouseClicked(e -> {
			tutorial_game = (tutorial_game == 2) ? 0: tutorial_game + 1;
			if(tutorial_game == 0) {
				ImageView xiangqiTemp = createImageView("/Images/xiangqiButton.png");
				xiangqiTemp.setFitWidth(225);
				xiangqiTemp.setFitHeight(225);
				tutorialGamesButton.setGraphic(xiangqiTemp);
				tutorialGamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
						//LAUNCH TUTORIAL 
					} 
				}));
			}
			else if(tutorial_game == 1) {
				ImageView shogiTemp = createImageView("/Images/shogiButton.png");
				shogiTemp.setFitWidth(225);
				shogiTemp.setFitHeight(225);
				tutorialGamesButton.setGraphic(shogiTemp);
				tutorialGamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
					} 
				}));
			}
			else if (tutorial_game == 2) {
				ImageView goTemp = createImageView("/Images/goButton.png");
				goTemp.setFitWidth(225);
				goTemp.setFitHeight(225);
				tutorialGamesButton.setGraphic(goTemp);
				tutorialGamesButton.setOnMouseClicked((new EventHandler<MouseEvent>() { 
					public void handle(MouseEvent event) { 
					} 
				}));
			}
		});
		
		Label togglePieceIconsLabel = new Label("Toggle Piece Icons");
		togglePieceIconsLabel.setFont(koreanFont);
		
		Label togglePieceIconsExplanation = new Label("Switches game piece icons from traditional characters to photographic icons");
		togglePieceIconsExplanation.setFont(new Font("Arial", 12));
		togglePieceIconsExplanation.setStyle("-fx-text-fill: red;");
		
		//TOGGLE PIECE ICONS 
		CheckBox togglePieceIcons = new CheckBox();
		togglePieceIcons.setStyle("-fx-focus-color: firebrick;");
		
		Label toggleAccessibilityModeLabel = new Label("Toggle Accessibility Mode");
		toggleAccessibilityModeLabel.setFont(koreanFont);
		
		EventHandler<ActionEvent> pieces = new EventHandler<ActionEvent>() { 
			  
            public void handle(ActionEvent e) 
            { 
            	if(togglePieceIcons.isSelected()) {
        			
        			togglePiece = true;
        		} 
                else
                    
                	togglePiece = false;; 
            } 

        }; 
		
        togglePieceIcons.setOnAction(pieces); 
		
        if(togglePiece == true) {
        	togglePieceIcons.setSelected(true);
        }
        else {
        	togglePieceIcons.setSelected(false);
        }
        
        
		//TOGGLE Accessibility Mode 
		CheckBox toggleAccessibilityMode = new CheckBox();
		toggleAccessibilityMode.setStyle("-fx-focus-color: firebrick;");
		
		Label toggleAccessibilityExplanation = new Label("When Accessibility Mode is active it will reveal all possible valid moves for a piece,\n and shows a II Back pieces name when selected");
		toggleAccessibilityExplanation.setFont(new Font("Arial", 12));
		toggleAccessibilityExplanation.setStyle("-fx-text-fill: red;");
		
		EventHandler<ActionEvent> access = new EventHandler<ActionEvent>() { 
			  
            public void handle(ActionEvent e) 
            { 
            	if(toggleAccessibilityMode.isSelected()) {
        			
        			toggleAccess = true;
        		} 
                else
                    
                	toggleAccess = false;; 
            } 

        }; 
		
        toggleAccessibilityMode.setOnAction(access); 
		
        if(toggleAccess == true) {
        	toggleAccessibilityMode.setSelected(true);
        }
        else {
        	toggleAccessibilityMode.setSelected(false);
        }
        
		Button backButton = new Button("Back");
		backButton.setStyle("-fx-focus-color: firebrick;");
		backButton.setPrefHeight(75);
		backButton.setPrefWidth(150);
		backButton.setFont(spaceFont);
		backButton.setOnMouseClicked(e -> {
			setupMainMenu(primaryStage);
			primaryStage.setScene(main_menu);
		});
		
		Pane pane = new Pane();
		pane.getChildren().addAll(background, accessibilityLogo, backButton, togglePieceIconsLabel, togglePieceIcons,
				toggleAccessibilityModeLabel, toggleAccessibilityMode, rulesGamesButton, rulesLeftGameButton,
				rulesRightGameButton, rulesLabel, tutorialGamesButton,  tutorialLeftGameButton,
				 tutorialRightGameButton, tutorialLabel, togglePieceIconsExplanation, toggleAccessibilityExplanation);
		accessibilityLogo.relocate(425 * RATIO, 30 * RATIO);
		backButton.relocate(1425 * RATIO, 850 * RATIO);
		togglePieceIconsLabel.relocate(300 * RATIO, 630 * RATIO);
		togglePieceIcons.relocate(690 * RATIO, 630 * RATIO);
		togglePieceIconsExplanation.relocate(300 * RATIO, 730 * RATIO);
		toggleAccessibilityModeLabel.relocate(300 * RATIO, 830 * RATIO);
		toggleAccessibilityMode.relocate(840 * RATIO, 830 * RATIO);
		toggleAccessibilityExplanation.relocate(300 * RATIO, 930 * RATIO);
		rulesGamesButton.relocate(300 * RATIO, 270 * RATIO);
		rulesLeftGameButton.relocate(220 * RATIO, 380 * RATIO );
		rulesRightGameButton.relocate(670 * RATIO, 380 * RATIO );
		rulesLabel.relocate(330 * RATIO, 190 * RATIO );
		tutorialGamesButton.relocate(1100 * RATIO, 270 * RATIO);
		tutorialLeftGameButton.relocate(1020 * RATIO, 380 * RATIO );
		tutorialRightGameButton.relocate(1470 * RATIO, 380 * RATIO );
		tutorialLabel.relocate(1180 * RATIO, 190 * RATIO );
		accessbility_menu = new Scene(pane);
		accessbility_menu.getStylesheets().add("/CSS/checkboxStyles.css");
	}

	public void setupXiangqiRulesScreen(Stage primaryStage) {
		ImageView background = createImageView("/Images/mainBackgroundGradient.png", true);
		
		Font spaceFont = Font.loadFont(getClass().getResource("/Fonts/ethnocentric.ttf").toExternalForm(), 22);
		
		ImageView xiangqiManualLogo = createImageView("/Images/xiangqiManual.png");
		
		ImageView red_soldier= createImageView("/Images/Pieces/red_soldier.png");
		red_soldier.setFitWidth(75);
		ImageView black_soldier= createImageView("/Images/Pieces/black_soldier.png");
		black_soldier.setFitWidth(75);
		
		Label soldierRules = new Label("Pawn: Moves/Captures by only moving one place forward.\n"
				+ "One passed the river can go horizontally but never back.\n"
				+ "Cannot jump pieces.");
		soldierRules.setFont(new Font("Arial", 12));
		
		ImageView red_cannon= createImageView("/Images/Pieces/red_cannon.png");
		red_cannon.setFitWidth(75);
		ImageView black_cannon= createImageView("/Images/Pieces/black_cannon.png");
		black_cannon.setFitWidth(75);
		
		Label cannonRules = new Label("Cannon: Move same as a chariot (straight horizontally or\n"
				+ "vertically for any amount of space without jumping other\n"
				+ "pieces), but can only capture by jumping over opponents \n"
				+ "piece to capture the desired piece.");
		cannonRules.setFont(new Font("Arial", 12));
		
		ImageView red_chariot= createImageView("/Images/Pieces/red_chariot.png");
		red_chariot.setFitWidth(75);
		ImageView black_chariot= createImageView("/Images/Pieces/black_chariot.png");
		black_chariot.setFitWidth(75);
		
		Label chariotRules = new Label("Chariot: Moves/Captures straight horizontally or \n"
				+ "vertically for any amount of space without jumping other\n"
				+ "pieces. ");
		chariotRules.setFont(new Font("Arial", 12));
		
		ImageView red_horse= createImageView("/Images/Pieces/red_horse.png");
		red_horse.setFitWidth(75);
		ImageView black_horse= createImageView("/Images/Pieces/black_horse.png");
		black_horse.setFitWidth(75);
		
		Label horseRules = new Label("Horse: Moves/Captures one space in any direction,\n"
				+ "then one space diagonally in any direction.  ");
		horseRules.setFont(new Font("Arial", 12));
		
		ImageView red_elephant= createImageView("/Images/Pieces/red_elephant.png");
		red_elephant.setFitWidth(75);
		ImageView black_elephant= createImageView("/Images/Pieces/black_elephant.png");
		black_elephant.setFitWidth(75);
		
		Label elephantRules = new Label("Elephant: Must move/capture two places diagonally.\n"
				+ "Cannot pass the river. Cannot jump other pieces. ");
		elephantRules.setFont(new Font("Arial", 12));
		
		ImageView red_advisor= createImageView("/Images/Pieces/red_advisor.png");
		red_advisor.setFitWidth(75);
		ImageView black_advisor= createImageView("/Images/Pieces/black_advisor.png");
		black_advisor.setFitWidth(75);
		
		Label advisorRules = new Label("Advisor / Guard: Can only move/capture one space diagonally\n"
				+ "in any direction. Confined to the palace, cannot jump pieces.");
		advisorRules.setFont(new Font("Arial", 12));
		
		ImageView red_general= createImageView("/Images/Pieces/red_general.png");
		red_general.setFitWidth(75);
		ImageView black_general = createImageView("/Images/Pieces/black_general.png");
		black_general.setFitWidth(75);
	
		Label generalRules = new Label("General: Moves orthogonality within the palace. Cannot move out\n"
				+ "of the palace. Two Generals cannot face each, meaning if there is no \n"
				+ "piece in between the two generals, it is invalid. Cannot \n"
				+ "jump other pieces. ");
		generalRules.setFont(new Font("Arial", 12));
		
		Button backButton = new Button("Back");
		backButton.setStyle("-fx-focus-color: firebrick;");
		backButton.setPrefHeight(75);
		backButton.setPrefWidth(150);
		backButton.setFont(spaceFont);
		backButton.setOnMouseClicked(e -> {
			setupAccessibilityMenu(primaryStage);
			primaryStage.setScene(accessbility_menu);
		});
		
		Pane pane = new Pane();
		pane.getChildren().addAll(background, backButton, xiangqiManualLogo, red_soldier, black_soldier,
				red_cannon, black_cannon, red_chariot, black_chariot, red_horse, black_horse, red_elephant,
				black_elephant, red_advisor, black_advisor, red_general, black_general, soldierRules,
				cannonRules, chariotRules, horseRules, elephantRules, advisorRules, generalRules);
		xiangqiManualLogo.relocate(475 * RATIO, 30 * RATIO);
		red_soldier.relocate(175 * RATIO, 130 * RATIO);
		black_soldier.relocate(300 * RATIO, 130 * RATIO);
		soldierRules.relocate(450 * RATIO, 160 * RATIO);
		red_cannon.relocate(175 * RATIO, 330 * RATIO);
		black_cannon.relocate(300 * RATIO, 330 * RATIO);
		cannonRules.relocate(450 * RATIO, 350 * RATIO);
		red_chariot.relocate(175 * RATIO, 530 * RATIO);
		black_chariot.relocate(300 * RATIO, 530 * RATIO);
		chariotRules.relocate(450 * RATIO, 560 * RATIO);
		red_horse.relocate(175 * RATIO, 730 * RATIO);
		black_horse.relocate(300 * RATIO, 730 * RATIO);
		horseRules.relocate(450 * RATIO, 760 * RATIO);
		red_elephant.relocate(975 * RATIO, 130 * RATIO);
		black_elephant.relocate(1100 * RATIO, 130 * RATIO);
		elephantRules.relocate(1250 * RATIO, 160 * RATIO);
		red_advisor.relocate(975 * RATIO, 330 * RATIO);
		black_advisor.relocate(1100 * RATIO, 330 * RATIO);
		advisorRules.relocate(1250 * RATIO, 360 * RATIO);
		red_general.relocate(975 * RATIO, 530 * RATIO);
		black_general.relocate(1100 * RATIO, 530 * RATIO);
		generalRules.relocate(1250 * RATIO, 540 * RATIO);
		backButton.relocate(1425 * RATIO, 850 * RATIO);
		xiangqi_rules_menu = new Scene(pane);
	}
	
	public void setupAccountMenu(Stage primaryStage) {
		ImageView background = createImageView("/Images/mainBackgroundGradient.png", true);

		try {
			newAccount.loadUserInfo(currentAccount);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		Font koreanFont = Font.loadFont(getClass().getResource("/Fonts/Korean_Calligraphy.ttf").toExternalForm(), 40);
		Font spaceFont = Font.loadFont(getClass().getResource("/Fonts/ethnocentric.ttf").toExternalForm(), 22);
		
		ImageView accountLogo = createImageView("/Images/accountLogo.png");
		accountLogo.setFitHeight(650);
		accountLogo.setFitWidth(650);
		
		Label usernameLabel = new Label("Username:");
		usernameLabel.setFont(koreanFont);
		
		//GET USERNAME
		Label username = new Label(newAccount.getUserName());
		username.setFont(new Font("Arial", 20));
		
		Label totalGamesLabel = new Label("Total Games:");
		totalGamesLabel.setFont(koreanFont);
		
		//GET TOTAL GAMES
		newAccount.setGamesPlayed(newAccount.getWins() + newAccount.getLoss());
		Label totalGames = new Label(newAccount.getGamesPlayed() + " Games Played");
		totalGames.setFont(new Font("Arial", 20));
		
		Label recordLabel = new Label("Record:");
		recordLabel.setFont(koreanFont);
		
		//GET RECORD
		Label record = new Label((newAccount.getWins() + " Wins " + "/ " + newAccount.getLoss() + " Losses "));
		record.setFont(new Font("Arial", 20));

		Label winRateLabel = new Label("Winrate:");
		winRateLabel.setFont(koreanFont);
		
		//GET WINRATE
		Label winrate;
		if (newAccount.getWins() == 0 && newAccount.getGamesPlayed() == 0) {
			winrate = new Label("0%");
			winrate.setFont(new Font("Arial", 20));
		}else {
			winrate = new Label((df.format(((double)newAccount.getWins()/(double)newAccount.getGamesPlayed())*100)) + "%");
			winrate.setFont(new Font("Arial", 20));
		}
		
		Button backButton = new Button("Back");
		backButton.setStyle("-fx-focus-color: firebrick;");
		backButton.setPrefHeight(50);
		backButton.setPrefWidth(150);
		backButton.setFont(spaceFont);
		backButton.setOnMouseClicked(e -> {
			setupMainMenu(primaryStage);
			primaryStage.setScene(main_menu);
		});
		
		Button deleteButton = new Button("Delete Account");
		deleteButton.setStyle("-fx-focus-color: firebrick; -fx-text-fill: red;");
		deleteButton.setPrefHeight(50);
		deleteButton.setPrefWidth(310);
		deleteButton.setFont(spaceFont);
		deleteButton.setOnMouseClicked(e -> {
			//DELETE ACCOUNT AND SIGN OUT
			String line = newAccount.getUserName() + "," + newAccount.getPassword() + "," + newAccount.getWins() + "," + newAccount.getLoss();
			try {
				newAccount.deleteAccount(line);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			setupLoginMenu(primaryStage);
			primaryStage.setScene(login_menu);
		});
		
		Button logoutButton = new Button("Logout");
		logoutButton.setStyle("-fx-focus-color: firebrick;");
		logoutButton.setPrefHeight(50);
		logoutButton.setPrefWidth(175);
		logoutButton.setFont(spaceFont);
		logoutButton.setOnMouseClicked(e -> {
			//SIGN OUT 
			setupLoginMenu(primaryStage);
			primaryStage.setScene(login_menu);
		});
		
		ImageView cannon = createImageView("/Images/cannon.png");
		cannon.setFitWidth(300);
		cannon.setFitHeight(300);

		Pane pane = new Pane();
		pane.getChildren().addAll(background, accountLogo, backButton, deleteButton, logoutButton,
				usernameLabel, username, totalGamesLabel, totalGames, recordLabel, 
				record, winRateLabel, winrate, cannon);
		accountLogo.relocate(425 * RATIO, 30 * RATIO);
		usernameLabel.relocate(225 * RATIO, 230 * RATIO);
		username.relocate(500 * RATIO, 265 * RATIO);
		totalGamesLabel.relocate(225 * RATIO, 330 * RATIO);
		totalGames.relocate(565 * RATIO, 365 * RATIO);
		recordLabel.relocate(225 * RATIO, 430 * RATIO);
		record.relocate(435 * RATIO, 465 * RATIO);
		winRateLabel.relocate(225 * RATIO, 530 * RATIO);
		winrate.relocate(465 * RATIO, 565 * RATIO);
		deleteButton.relocate(225 * RATIO, 850 * RATIO);
		logoutButton.relocate(225 * RATIO, 750 * RATIO);
		backButton.relocate(1425 * RATIO, 850 * RATIO);
		cannon.relocate(1225 * RATIO, 125 * RATIO);
		account_menu = new Scene(pane);
	}

	//Helper functions
	public Button createButton(String image_location){
		return new Button("", createImageView(image_location, false)); 
	}

	public Button createButtonWithSize(String image_location, int width, int height) {
		ImageView picture = createImageView(image_location, false);
		picture.setFitHeight(height);
		picture.setFitWidth(width);
		return new Button("", picture);
	}

	public ImageView createImageView(String image_location){
		Image image = new Image(image_location);
		ImageView image_view = new ImageView(image);
		image_view.setFitWidth(image.getWidth() * RATIO);
		image_view.setPreserveRatio(true);
		image_view.setSmooth(true);
		image_view.setCache(true);
		return image_view;
	}

	public ImageView createImageView(String image_location, boolean background){
		Image image = new Image(image_location);
		ImageView image_view = new ImageView(image);
		if(background){
			image_view.setFitWidth(1920 * RATIO);
			image_view.setFitHeight(1080 * RATIO);
		}
		else{
			image_view.setFitWidth(image.getWidth() * RATIO);
			image_view.setFitHeight(image.getHeight() * RATIO);
		}
		image_view.setPreserveRatio(true);
		image_view.setSmooth(true);
		image_view.setCache(true);
		return image_view;
	}


	//Main Launches Stage

	public static void main(String[] args){
		launch(args);
	}
}


