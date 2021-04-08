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
import java.util.ArrayList;
public class AI {

	private StackPane [][] board;
	private Behavior behavior;
	private ArrayList<Point2D> validPoints;
	final double RATIO = 0.6;
	int spacing = 100;
	int oldX;
	int oldY;
	private Board boardClass;
	
/* This class handles all aspects of the AI. It first goes through all of its available moves to find which would
 * leave it in the best position overall on the board. It then executes the move and updates the board	
 */
	public AI(Piece [][] databoard, Board boardClass) {

		behavior = new Behavior();
		this.boardClass = boardClass;
		
	}

//This method takes a possible move for the AI and calculates the overall score of the board to decide which move to make.	
	private int evaluate(Piece [][] databoard, int bonus) {
		int score = 0;
		score+= bonus;
		for(int i = 0; i < databoard.length; i++) {
			
			for(int j = 0; j < databoard[0].length; j++) {
				
				if (databoard[i][j] != null && databoard[i][j].getSide() == 'B') {
					
					if(databoard[i][j] instanceof Soldier) {
						score += 5;
					}else if(databoard[i][j] instanceof Horse) {
						score += 10;
					}else if(databoard[i][j] instanceof General) {
						score += 50;
					}else if(databoard[i][j] instanceof Elephant) {
						score += 15;
					}else if(databoard[i][j] instanceof Chariot) {
						score += 15;
					}else if(databoard[i][j] instanceof Cannon) {
						score += 15;
					}else if(databoard[i][j] instanceof Advisor) {
						score += 25;
					}
				
				}else if (databoard[i][j] != null && databoard[i][j].getSide() == 'R') {
					
					if(databoard[i][j] instanceof Soldier) {
						score -= 5;
					}else if(databoard[i][j] instanceof Horse) {
						score -= 10;
					}else if(databoard[i][j] instanceof General) {
						score -= 50;
					}else if(databoard[i][j] instanceof Elephant) {
						score -= 15;
					}else if(databoard[i][j] instanceof Chariot) {
						score -= 15;
					}else if(databoard[i][j] instanceof Cannon) {
						score -= 15;
					}else if(databoard[i][j] instanceof Advisor) {
						score -= 25;
					}
				}
			}
		}
		
		return score;
	}
//This method goes through each move available to the AI and each piece left on the board. It then sends this move to
//Evaluate score to find the best possible move.	
	public void calculateMove(Piece [][] dataBoard, StackPane [][] board) {
		this.board = board;
		Piece original = null;
		Piece captured = null;
		Piece moved = null;
		Piece target = null;
		boolean wasCaptured = false;
		int best = -500;
		int movedX = 0;
		int movedY = 0;
		int targetX = 0;
		int targetY = 0;
		int bonus = 0;
		
		for(int i = 0; i < dataBoard.length; i++) {
			
			for(int j = 0; j < dataBoard[0].length; j++) {
				
				validPoints = new ArrayList<Point2D>();

				if(dataBoard[i][j] != null && dataBoard[i][j].getSide() == 'B') {
					
				validPoints = behavior.getLegalMoves(dataBoard[i][j], dataBoard);
				original = dataBoard[i][j];

				for(int k = 0; k < validPoints.size(); k++) {
					int x = (int)validPoints.get(k).getX();
					int y = (int)validPoints.get(k).getY();

					if(dataBoard[y][x] != null) {
						
						captured = dataBoard[y][x];
						wasCaptured = true;
						dataBoard[y][x] = null;
					}
					
					if(y > i && !(dataBoard[i][j] instanceof Advisor) && !(dataBoard[i][j] instanceof General)) {
						
						bonus = 5;
					}
					
					if(dataBoard[i][j] instanceof Cannon && y == 9) {
						bonus = -5;
					}
					
					dataBoard[y][x] = original;
					dataBoard[i][j] = null;
					int evaluateScore = evaluate(dataBoard, bonus);
					dataBoard[i][j] = original;

					if(wasCaptured) {
						
						dataBoard[y][x] = captured;
						wasCaptured = false;
					
					}else {
						
						dataBoard[y][x] = null;
					}
					
					if(evaluateScore > best) {
						
						best = evaluateScore;
						moved = dataBoard[i][j];
						movedX = j;
						movedY = i;
						target = dataBoard[y][x];
						targetX = x;
						targetY = y;

					}		
					
					
				}
				
			}
			}
		}

		makeMove(dataBoard, moved, target, movedX, movedY, targetX, targetY);

	}
//This method is called after the best move for the AI is found. The piece is then moved and the board is updated	
	public void makeMove(Piece [][] dataBoard, Piece moved, Piece target, int movedX, int movedY, int targetX, int targetY) {
		
		double origX = board[movedY][movedX].getLayoutX();
		double origY = board[movedY][movedX].getLayoutY();

		if(board[targetY][targetX].getChildren().size() > 1 && board[targetY][targetX].getChildren().get(1) instanceof ImageView) {

			boardClass.captured_red_side.updateCaptured(dataBoard[targetY][targetX]);
			if(dataBoard[targetY][targetX] instanceof General) {
				
				boardClass.win('B');
			}
			dataBoard[targetY][targetX] = null;
			board[targetY][targetX].getChildren().remove(1);
		
		}
		
		
		
		dataBoard[targetY][targetX] = moved;
		dataBoard[movedY][movedX] = null;
		
		StackPane swap = board[targetY][targetX];
		board[targetY][targetX] = board[movedY][movedX];
		board[movedY][movedX] = swap;
		board[targetY][targetX].relocate(swap.getLayoutX(), swap.getLayoutY());
		board[movedY][movedX].relocate(origX, origY);
		moved.setPaneXY(targetX, targetY);
		
	}


}
