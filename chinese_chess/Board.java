import Piece.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.control.Button;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import java.util.Collection;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Board{

	private char turn = 'R';
	private boolean win = false;
	private StackPane[][] board;
	private Piece[][] dataBoard;
	final double RATIO = 0.6;
	private int x = 24, y = 40, spacing = 100;
	private int piece_oneX, piece_oneY;
	private boolean piece_clicked = false, togglePiece = false, toggleAccess = false;
	private StackPane clicked_pane;
	private ArrayList<Point2D> validPoints;
	private Behavior behavior;
	private Side red_side, black_side;
	AI ai = new AI(dataBoard, this);
	Captured captured_red_side, captured_black_side;
	private Stage primaryStage;
	private Scene main_menu;
	Accounts newAccount = new Accounts();
	String currentUsername = null;
	
	
/*
 * This class is in charge of creating the game board and populating it with pieces. Include action listeners for
 * when the player clicks and moves pieces. Updates the board accordingly and sends the data to the AI class so that
 * it can make  its move.
 */
	public Board(Pane pane, Stage primaryStage, Scene main_menu, Boolean togglePiece, Boolean toggleAccess){
		
		this.togglePiece = togglePiece;
		this.toggleAccess = toggleAccess;
		setupCapturedPieces(pane);
		board = new StackPane[10][9];
		dataBoard = new Piece[10][9];
		validPoints = new ArrayList<Point2D>();
		behavior = new Behavior();
		this.primaryStage = primaryStage;
		this.main_menu = main_menu;
		
		
//Populates Board with Squares set over background board image
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 9; j++){
				StackPane s_pane = new StackPane();
				Rectangle rect = new Rectangle((x + j * spacing) * RATIO, (y + i * spacing) * RATIO, spacing * RATIO, spacing * RATIO);

//Outlines spaces when hovered over
				rect.setFill(Color.TRANSPARENT);
				rect.setStroke(Color.TRANSPARENT);
				s_pane.getChildren().add(rect);
				rect.setMouseTransparent(true);

				s_pane.setOnMouseEntered(new EventHandler<MouseEvent>() {
				  public void handle(MouseEvent e) {
				      rect.setStroke(Color.BLACK);
				  }
				});

				s_pane.setOnMouseExited(new EventHandler<MouseEvent>() {
				  public void handle(MouseEvent e) {
				  		rect.setStroke(Color.TRANSPARENT);
				  }
				});

				s_pane.setOnMouseClicked(new EventHandler<MouseEvent>(){
					public void handle(MouseEvent e) {
//Moving piece after selecting						
						if(piece_clicked){
							if(e.getTarget() instanceof StackPane){
								double origX = clicked_pane.getLayoutX();
		  						double origY = clicked_pane.getLayoutY();
		  						Piece old_piece = dataBoard[piece_oneY][piece_oneX];
		  						int old_x = piece_oneX;
		  						int old_y = piece_oneY;
		  						getArrCoor(s_pane);

		  						boolean valid_point = false;
		  						for(Point2D point : validPoints){
		  							if(point.getX() == piece_oneX && point.getY() == piece_oneY){
		  								valid_point = true;
		  							}
		  						}

		  						((Rectangle)(board[piece_oneY][piece_oneX].getChildren().get(0))).setFill(Color.TRANSPARENT);
		  						for(Point2D point : validPoints){
									int rectX = (int)point.getX();
									int rectY = (int)point.getY();
									((Rectangle)(board[rectY][rectX].getChildren().get(0))).setFill(Color.TRANSPARENT); 
								}

		  						if(valid_point){
		  							Piece target_piece = null;
									if(s_pane.getChildren().size() > 1 && s_pane.getChildren().get(1) instanceof ImageView){
										target_piece = dataBoard[piece_oneY][piece_oneX];
			  							target_piece.setPaneXY(piece_oneX, piece_oneY);
			  						
			  							// Remove the piece from the data board
										dataBoard[piece_oneY][piece_oneX] = null; 
										s_pane.getChildren().remove(1);
										
										
										captured_black_side.updateCaptured(target_piece);
									}
									
									if(target_piece instanceof General) {
										
										win('R');
									}

									//Switches pieces on board
									target_piece = dataBoard[piece_oneY][piece_oneX];
									old_piece.setPaneXY(piece_oneX, piece_oneY);
									dataBoard[piece_oneY][piece_oneX] = old_piece;
									dataBoard[old_y][old_x] = target_piece;
									StackPane swap = board[piece_oneY][piece_oneX];
									board[piece_oneY][piece_oneX] = board[old_y][old_x];
									board[old_y][old_x] = swap;
									clicked_pane.relocate(s_pane.getLayoutX(), s_pane.getLayoutY());
									s_pane.relocate(origX, origY);
									if(turn == 'R'){
										turn = 'B';
//Calls AI class to calculate and make its move
										ai.calculateMove(dataBoard, board);
										turn = 'R';
									}
									
									
								}
		  					}
		  					clicked_pane.setOpacity(1.0);
		  					clicked_pane = null;
	  						piece_clicked = false;
						}
//Clicking on own piece						
						else{
							
							//Checks if playable piece is clicked then makes sure it is the correct side's piece
							if(s_pane.getChildren().size() > 1 && s_pane.getChildren().get(1) instanceof ImageView){
								Piece piece = getPieceFromPane(s_pane);
								if(piece.getSide() == turn){
									validPoints = new ArrayList<Point2D>();
									validPoints = behavior.getLegalMoves(piece, dataBoard);
									
									if(toggleAccess) {
									//Checks valid move spaces and highlights
										for(int i = 0; i < validPoints.size(); i++){
											int rectX = (int)(validPoints.get(i).getX());
											int rectY = (int)(validPoints.get(i).getY());
										
											((Rectangle)(board[rectY][rectX].getChildren().get(0))).setFill(Color.rgb(12, 128, 34, 0.4)); 
										}
									}
									getArrCoor(s_pane);
									piece_clicked = true;
									s_pane.setOpacity(0.5);
									clicked_pane = s_pane;
								}
							}
						}
					}
				});
				pane.getChildren().add(s_pane);
				s_pane.setManaged(true);
				s_pane.relocate((x + j * spacing) * RATIO, (y + i * spacing) * RATIO);
				board[i][j] = s_pane;
			}
		}
		red_side = new Side('R', this, togglePiece);
		black_side = new Side('B', this, togglePiece);

	}



// Helper function for calculating x and y values for the 2D arrays through coordinates
	public void getArrCoor(StackPane s_pane){
		Bounds point = s_pane.localToScene(s_pane.getBoundsInLocal());
		piece_oneX = (int)((point.getMinX()) / (spacing * RATIO));
		piece_oneY = (int)((point.getMinY()) / (spacing * RATIO));

	}

	public Piece getPieceFromPane(StackPane s_pane){
		getArrCoor(s_pane);
		return dataBoard[piece_oneY][piece_oneX];
	}

	public StackPane[][] getSPArr(){
		return board;
	}
	

	public Piece[][] getPArr(){
		return dataBoard;
	}
	
	
	public void setupCapturedPieces(Pane pane){
		captured_red_side = new Captured('R', pane, true, togglePiece); // Setup captured red pieces
		captured_black_side = new Captured('B', pane, true, togglePiece); // Setup captured black pieces
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
		}
		else{
			image_view.setFitWidth(image.getWidth() * RATIO);
		}
		image_view.setPreserveRatio(true);
		image_view.setSmooth(true);
		image_view.setCache(true);
		return image_view;
	}

	public Button createButton(String image_location){
		return new Button("", createImageView(image_location, false)); 
	}
	
	public void win(char side)  {
		ImageView background = createImageView("/Images/MainMenu.png", true);
		ImageView winner = null, loser = null;
		if(side == 'R') {
			
			win = true;
			try {
				newAccount.updateWins(currentUsername);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			winner = createImageView("/Images/YouWon.png");	
			
		}else if(side == 'B') {
			
			win = false;
			try {
				newAccount.updateLosses(currentUsername);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			loser = createImageView("/Images/YouLost.png");
		}
		
		Button continueButton = createButton("/Images/PlayAgain.png");
		
		continueButton.setOnMouseClicked(f-> {
			primaryStage.setScene(main_menu);
		});
		
		Pane pane = new Pane();
		
		if(win == true) {
			
			pane.getChildren().addAll(background, winner, continueButton);
			winner.relocate(670 * RATIO, 70 * RATIO);
			continueButton.relocate(670 * RATIO, 780 * RATIO);
			
		}else if(win == false){
			
			pane.getChildren().addAll(background, loser, continueButton);
			loser.relocate(670 * RATIO, 70 * RATIO);
			continueButton.relocate(670 * RATIO, 780 * RATIO);
		}
		
		Scene win_screen = new Scene(pane);
		primaryStage.setScene(win_screen);
	}

	public void setUsername(String username) {
		this.currentUsername = username;
	}
	
}