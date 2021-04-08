package Piece;
import javafx.scene.layout.Pane;
public class Horse extends Piece{
	public Horse(double x, double y, double alpha, char side, Pane pane, boolean togglePiece){
		super(x, y, alpha, side, pane, togglePiece);
		if(side == 'R'){
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/red_horse.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/RedHorseTraditional.png");
			}
		}
		else{
			
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/black_horse.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/BlackHorseTraditional.png");
			}
			
		}
		setupImageView();
	}

}