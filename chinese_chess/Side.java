import Piece.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/*
 * This class sets up each side of the board (black and red) with the correct pieces. It creates a new instance of
 * each piece
 */
public class Side{
	General general;
	Advisor left_advisor, right_advisor;
	Elephant left_elephant, right_elephant;
	Horse left_horse, right_horse;
	Chariot left_chariot, right_chariot;
	Cannon left_cannon, right_cannon;
	Soldier soldier_one, soldier_two, soldier_three, soldier_four, soldier_five;

	public Side(char side, Board board1, Boolean togglePiece){ // 'R' or 'B'
		StackPane[][] board = board1.getSPArr();
		Piece[][] data_board = board1.getPArr();
		if(side == 'R'){ // Red side

			left_chariot = new Chariot(0, 0, 1, side, board[9][0], togglePiece);
			left_horse = new Horse(0, 0, 1, side, board[9][1], togglePiece);
			left_elephant = new Elephant(0, 0, 1, side, board[9][2], togglePiece);
			left_advisor = new Advisor(0, 0, 1, side, board[9][3], togglePiece);
			general = new General(0, 0, 1, side, board[9][4], togglePiece);
			right_advisor = new Advisor(0, 0, 1, side, board[9][5], togglePiece);
			right_elephant = new Elephant(0, 0, 1, side, board[9][6], togglePiece);
			right_horse = new Horse(0, 0, 1, side, board[9][7], togglePiece);
			right_chariot = new Chariot(0, 0, 1, side, board[9][8], togglePiece);
			left_cannon = new Cannon(0, 0, 1, side, board[7][1], togglePiece);
			right_cannon = new Cannon(0, 0, 1, side, board[7][7], togglePiece);
			soldier_one = new Soldier(0, 0, 1, side, board[6][0], togglePiece);
			soldier_two = new Soldier(0, 0, 1, side, board[6][2], togglePiece);
			soldier_three = new Soldier(0, 0, 1, side, board[6][4], togglePiece);
			soldier_four = new Soldier(0, 0, 1, side, board[6][6], togglePiece);
			soldier_five = new Soldier(0, 0, 1, side, board[6][8], togglePiece);
			data_board[9][0] = left_chariot;
			data_board[9][1] = left_horse;
			data_board[9][2] = left_elephant;
			data_board[9][3] = left_advisor;
			data_board[9][4] = general;
			data_board[9][5] = right_advisor;
			data_board[9][6] = right_elephant;
			data_board[9][7] = right_horse;
			data_board[9][8] = right_chariot;
			data_board[7][1] = left_cannon;
			data_board[7][7] = right_cannon;
			data_board[6][0] = soldier_one;
			data_board[6][2] = soldier_two;
			data_board[6][4] = soldier_three;
			data_board[6][6] = soldier_four;
			data_board[6][8] = soldier_five;
			left_chariot.setPaneXY(0, 9);
			left_horse.setPaneXY(1, 9);
			left_elephant.setPaneXY(2, 9);
			left_advisor.setPaneXY(3, 9);
			general.setPaneXY(4, 9);
			right_advisor.setPaneXY(5, 9);
			right_elephant.setPaneXY(6, 9);
			right_horse.setPaneXY(7, 9);
			right_chariot.setPaneXY(8, 9);
			left_cannon.setPaneXY(1, 7);
			right_cannon.setPaneXY(7, 7);
			soldier_one.setPaneXY(0, 6);
			soldier_two.setPaneXY(2, 6);
			soldier_three.setPaneXY(4, 6);
			soldier_four.setPaneXY(6, 6);
			soldier_five.setPaneXY(8, 6);
		}
		else{ // Black side
			left_chariot = new Chariot(0, 0, 1, side, board[0][0], togglePiece);
			left_horse = new Horse(0, 0, 1, side, board[0][1], togglePiece);
			left_elephant = new Elephant(0, 0, 1, side, board[0][2], togglePiece);
			left_advisor = new Advisor(0, 0, 1, side, board[0][3], togglePiece);
			general = new General(0, 0, 1, side, board[0][4], togglePiece);
			right_advisor = new Advisor(0, 0, 1, side, board[0][5], togglePiece);
			right_elephant = new Elephant(0, 0, 1, side, board[0][6], togglePiece);
			right_horse = new Horse(0, 0, 1, side, board[0][7], togglePiece);
			right_chariot = new Chariot(0, 0, 1, side, board[0][8], togglePiece);
			left_cannon = new Cannon(0, 0, 1, side, board[2][1], togglePiece);
			right_cannon = new Cannon(0, 0, 1, side, board[2][7], togglePiece);
			soldier_one = new Soldier(0, 0, 1, side, board[3][0], togglePiece);
			soldier_two = new Soldier(0, 0, 1, side, board[3][2], togglePiece);
			soldier_three = new Soldier(0, 0, 1, side, board[3][4], togglePiece);
			soldier_four = new Soldier(0, 0, 1, side, board[3][6], togglePiece);
			soldier_five = new Soldier(0, 0, 1, side, board[3][8], togglePiece);
			data_board[0][0] = left_chariot;
			data_board[0][1] = left_horse;
			data_board[0][2] = left_elephant;
			data_board[0][3] = left_advisor;
			data_board[0][4] = general;
			data_board[0][5] = right_advisor;
			data_board[0][6] = right_elephant;
			data_board[0][7] = right_horse;
			data_board[0][8] = right_chariot;
			data_board[2][1] = left_cannon;
			data_board[2][7] = right_cannon;
			data_board[3][0] = soldier_one;
			data_board[3][2] = soldier_two;
			data_board[3][4] = soldier_three;
			data_board[3][6] = soldier_four;
			data_board[3][8] = soldier_five;
			left_chariot.setPaneXY(0, 0);
			left_horse.setPaneXY(1, 0);
			left_elephant.setPaneXY(2, 0);
			left_advisor.setPaneXY(3, 0);
			general.setPaneXY(4, 0);
			right_advisor.setPaneXY(5, 0);
			right_elephant.setPaneXY(6, 0);
			right_horse.setPaneXY(7, 0);
			right_chariot.setPaneXY(8, 0);
			left_cannon.setPaneXY(1, 2);
			right_cannon.setPaneXY(7, 2);
			soldier_one.setPaneXY(0, 3);
			soldier_two.setPaneXY(2, 3);
			soldier_three.setPaneXY(4, 3);
			soldier_four.setPaneXY(6, 3);
			soldier_five.setPaneXY(8, 3);
		}
	}
}