package Piece;
import javafx.scene.layout.Pane;
public class Elephant extends Piece{
	public Elephant(double x, double y, double alpha, char side, Pane pane, boolean togglePiece){
		super(x, y, alpha, side, pane, togglePiece);
		
		if(side == 'R'){
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/red_elephant.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/RedElephantTraditional.png");
			}
		}
		else{
			
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/black_elephant.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/BlackElephantTraditional.png");
			}
			
		}

		setupImageView();
	}

}