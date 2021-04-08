package Piece;
import javafx.scene.layout.Pane;
public class Chariot extends Piece{
	public Chariot(double x, double y, double alpha, char side, Pane pane, boolean togglePiece){
		super(x, y, alpha, side, pane, togglePiece);
		
		if(side == 'R'){
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/red_chariot.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/RedChariotTraditional.png");
			}
		}
		else{
			
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/black_chariot.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/BlackChariotTraditional.png");
			}
			
		}
		
		setupImageView();
	}

}