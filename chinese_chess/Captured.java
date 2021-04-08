import Piece.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
/* 
 * This class is in charge of setting up the captured pieces area on the side of the board. When a piece is captured
 * on the board, this class updates the captured boxes with the correct piece
 */
public class Captured{
	General general;
	Advisor left_advisor, right_advisor;
	Elephant left_elephant, right_elephant;
	Horse left_horse, right_horse;
	Chariot left_chariot, right_chariot;
	Cannon left_cannon, right_cannon;
	Soldier soldier_one, soldier_two, soldier_three, soldier_four, soldier_five;
	int soldier_cnt, advisor_cnt, elephant_cnt, horse_cnt, chariot_cnt, cannon_cnt;

	public Captured(char side, Pane pane, boolean captured, boolean togglePiece){
		soldier_cnt = 1;
		advisor_cnt = 1;
		elephant_cnt = 1;
		horse_cnt = 1;
		chariot_cnt = 1;
		cannon_cnt = 1;
		int row, col, spacing = 100;
		if(!captured){ // On board
			int c_row, c_col, s_row;
			if(side == 'R'){ // Red side
				col = 22;
				row = 952;
				c_row = 750;
				c_col = 124;
				s_row = 646;
			}
			else{ // Black side
				col = 22;
				row = 34;
				c_row = 238;
				c_col = 124;
				s_row = 338;
			}
			left_chariot = new Chariot(col, row, 1, side, pane, togglePiece);
			left_horse = new Horse((col + spacing), row, 1, side, pane, togglePiece);
			left_elephant = new Elephant((col + 2 * spacing), row, 1, side, pane, togglePiece);
			left_advisor = new Advisor((col + 3 * spacing), row, 1, side, pane, togglePiece);
			general = new General((col + 4 * spacing), row, 1, side, pane, togglePiece);
			right_advisor = new Advisor((col + 5 * spacing), row, 1, side, pane, togglePiece);
			right_elephant = new Elephant((col + 6 * spacing), row, 1, side, pane, togglePiece);
			right_horse = new Horse((col + 7 * spacing), row, 1, side, pane, togglePiece);
			right_chariot = new Chariot((col + 8 * spacing), row, 1, side, pane, togglePiece);
			left_cannon = new Cannon(c_col, c_row, 1, side, pane, togglePiece);
			right_cannon = new Cannon((c_col + 6 * spacing), c_row, 1, side, pane, togglePiece);
			soldier_one = new Soldier(col, s_row, 1, side, pane, togglePiece);
			soldier_two = new Soldier((col + 2 * spacing), s_row, 1, side, pane, togglePiece);
			soldier_three = new Soldier((col + 4 * spacing), s_row, 1, side, pane, togglePiece);
			soldier_four = new Soldier((col + 6 * spacing), s_row, 1, side, pane, togglePiece);
			soldier_five = new Soldier((col + 8 * spacing), s_row, 1, side, pane, togglePiece);
			left_chariot.addDragAndDrop();
			left_horse.addDragAndDrop();
			left_elephant.addDragAndDrop();
			left_advisor.addDragAndDrop();
			general.addDragAndDrop();
			right_advisor.addDragAndDrop();
			right_elephant.addDragAndDrop();
			right_horse.addDragAndDrop();
			right_chariot.addDragAndDrop();
			left_cannon.addDragAndDrop();
			right_cannon.addDragAndDrop();
			soldier_one.addDragAndDrop();
			soldier_two.addDragAndDrop();
			soldier_three.addDragAndDrop();
			soldier_four.addDragAndDrop();
			soldier_five.addDragAndDrop();
		}
		else{ // Captured
			if(side == 'R'){ // Red side
				row = 598;
				col = 996;
			}
			else{ // Black side
				row = 368;
				col = 996;
			}
			soldier_one = new Soldier(col, row, 0.5, side, pane, togglePiece);
			soldier_two = new Soldier((col + spacing), row, 0.5, side, pane, togglePiece);
			soldier_three = new Soldier((col + 2 * spacing), row, 0.5, side, pane, togglePiece);
			soldier_four = new Soldier((col + 3 * spacing), row, 0.5, side, pane, togglePiece);
			soldier_five = new Soldier((col + 4 * spacing), row, 0.5, side, pane, togglePiece);
			left_cannon = new Cannon((col + 5 * spacing), row, 0.5, side, pane, togglePiece);
			right_cannon = new Cannon((col + 6 * spacing), row, 0.5, side, pane, togglePiece);
			left_chariot = new Chariot((col + 7 * spacing), row, 0.5, side, pane, togglePiece);
			right_chariot = new Chariot(col, row + spacing, 0.5, side, pane, togglePiece);
			left_horse = new Horse((col +  spacing), row + spacing, 0.5, side, pane, togglePiece);
			right_horse = new Horse((col + 2 * spacing), row + spacing, 0.5, side, pane, togglePiece);
			left_elephant = new Elephant((col + 3 * spacing), row + spacing, 0.5, side, pane, togglePiece);
			right_elephant = new Elephant((col + 4 * spacing), row + spacing, 0.5, side, pane, togglePiece);
			left_advisor = new Advisor((col + 5 * spacing), row + spacing, 0.5, side, pane, togglePiece);
			right_advisor = new Advisor((col + 6 * spacing), row + spacing, 0.5, side, pane, togglePiece);
			general = new General((col + 7 * spacing), row + spacing, 0.5, side, pane, togglePiece);
		}
	}

	public void updateCaptured(Piece piece){
		if(piece instanceof Soldier){
			switch(soldier_cnt){
				case(1): 
				soldier_one.updateImageView();
				break;

				case(2):
				soldier_two.updateImageView();
				break;

				case(3):
				soldier_three.updateImageView();
				break;

				case(4):
				soldier_four.updateImageView();
				break;

				case(5):
				soldier_five.updateImageView();
				break;
			}
			soldier_cnt++;
			return;
		}
		if(piece instanceof Cannon){
			switch(cannon_cnt){
				case(1):
				left_cannon.updateImageView();
				break;

				case(2):
				right_cannon.updateImageView();
				break;
			}
			cannon_cnt++;
			return;
		}

		if(piece instanceof Chariot){
			switch(chariot_cnt){
				case(1):
				left_chariot.updateImageView();
				break;

				case(2):
				right_chariot.updateImageView();
				break;
			}
			chariot_cnt++;
			return;
		}
		if(piece instanceof Horse){
			switch(horse_cnt){
				case(1):
				left_horse.updateImageView();
				break;

				case(2):
				right_horse.updateImageView();
				break;
			}
			horse_cnt++;
			return;
		}
		if(piece instanceof Elephant){
			switch(elephant_cnt){
				case(1):
				left_elephant.updateImageView();
				break;

				case(2):
				right_elephant.updateImageView();
				break;
			}
			elephant_cnt++;
			return;
		}
		if(piece instanceof Advisor){
			switch(advisor_cnt){
				case(1):
				left_advisor.updateImageView();
				break;

				case(2):
				right_advisor.updateImageView();
				break;
			}
			advisor_cnt++;
			return;
		}
		if(piece instanceof General){
			general.updateImageView();
		}
	}
}