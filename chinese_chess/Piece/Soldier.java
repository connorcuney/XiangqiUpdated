package Piece;
import javafx.scene.layout.Pane;
public class Soldier extends Piece{
	public Soldier(double x, double y, double alpha, char side, Pane pane, boolean togglePiece){
		super(x, y, alpha, side, pane, togglePiece);
		
		if(side == 'R'){
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/red_soldier.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/RedSoldierTraditional.png");
			}
		}
		else{
			
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/black_soldier.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/BlackSoldierTraditional.png");
			}
			
		}

		setupImageView();
	}

}