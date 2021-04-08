package Piece;
import javafx.scene.layout.Pane;
public class Cannon extends Piece{
	public Cannon(double x, double y, double alpha, char side, Pane pane, boolean togglePiece){
		super(x, y, alpha, side, pane, togglePiece);
		
		if(side == 'R'){
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/red_cannon.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/RedCannonTraditional.png");
			}
		}
		else{
			
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/black_cannon.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/BlackCannonTraditional.png");
			}
			
		}
		
		setupImageView();
	}

}