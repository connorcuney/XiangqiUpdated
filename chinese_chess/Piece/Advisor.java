package Piece;
import javafx.scene.layout.Pane;
public class Advisor extends Piece{
	public Advisor(double x, double y, double alpha, char side, Pane pane, boolean togglePiece){
		super(x, y, alpha, side, pane, togglePiece);
		
		if(side == 'R'){
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/red_advisor.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/RedAdvisorTraditional.png");
			}
		}
		else{
			
			if(togglePiece) {
				
				image_view = createImageView("/Images/Pieces/black_advisor.png");
			}else {
				
				image_view = createImageView("/Images/Pieces/BlackAdvisorTraditional.png");
			}
			
		}
		
		setupImageView();
	}

}