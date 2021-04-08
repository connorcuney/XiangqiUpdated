package Piece;
import javafx.scene.layout.Pane;
public class General extends Piece{
	public General(double x, double y, double alpha, char side, Pane pane, boolean togglePiece){
		super(x, y, alpha, side, pane, togglePiece);
		
		if(side == 'R'){
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/red_general.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/RedGeneralTraditional.png");
			}
		}
		else{
			
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/black_general.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/BlackGeneralTraditional.png");
			}
			
		}
		
		setupImageView();
	}

}