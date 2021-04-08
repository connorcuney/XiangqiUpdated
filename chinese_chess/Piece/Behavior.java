package Piece;
import javafx.geometry.Point2D;
import java.util.Collection;
import java.util.ArrayList;


public class Behavior{
	
	ArrayList<Point2D> ret;
	
	public Behavior(){
	
	}

	public ArrayList<Point2D> getLegalMoves(Piece piece, Piece[][] dataBoard){
		ret = new ArrayList<Point2D>();
		int x = piece.getPaneX();
		int y = piece.getPaneY();

//Checks which way pieces can move depending on the color. Then makes sure there aren't any pieces blocking it.
//If not then adds valid moves to array to be returned.		
		if(piece instanceof Soldier){
			if(piece.getSide() == 'R'){
				if(y <= 4){ // Crossed river
					if(x - 1 >= 0){
						addToList(x - 1, y, piece, dataBoard, ret);
					}
					if(x + 1 <= 8){
						addToList(x + 1, y, piece, dataBoard, ret);
					}
				}
				if(y - 1 >= 0){
					addToList(x, y - 1, piece, dataBoard, ret);
				}
			}
			else{ //Black side
				if(y >= 5){ // Crossed river
					if(x - 1 >= 0){
						addToList(x - 1, y, piece, dataBoard, ret);
					}
					if(x + 1 <= 8){
						addToList(x + 1, y, piece, dataBoard, ret);
					}
				}
				if(y + 1 <= 9){
					addToList(x, y + 1, piece, dataBoard, ret);
				}
			}
			return ret;
		}

		if(piece instanceof Horse){
			if(y + 2  <= 9 && noPiece(x, y + 1, dataBoard)){ // Down
				if(x + 1 <= 8){ // Bottom right
						addToList(x + 1, y + 2, piece, dataBoard, ret);
				}
				if(x - 1 >= 0){ // Bottom left
						addToList(x - 1, y + 2, piece, dataBoard, ret);
				}

			}
			if(y - 2 >= 0 && noPiece(x, y - 1, dataBoard)){ // Up
				if(x + 1 <= 8){ // Upper right
						addToList(x + 1, y - 2, piece, dataBoard, ret);
				}
				if(x - 1 >= 0){ // Upper left
						addToList(x - 1, y - 2, piece, dataBoard, ret);
				}

			}
			if(x + 2 <= 8 && noPiece(x + 1, y, dataBoard)){ // Right
				if(y + 1 <= 9){ // Right down
						addToList(x + 2, y + 1, piece, dataBoard, ret);
				}
				if(y - 1 >= 0){ // Right up
						addToList(x + 2, y - 1, piece, dataBoard, ret);
				}

			}
			if(x - 2 >= 0 && noPiece(x - 1, y, dataBoard)){ // Left
				if(y + 1 <= 9){ // Left down
						addToList(x - 2, y + 1, piece, dataBoard, ret);
				}
				if(y - 1 >= 0){ // Left up
						addToList(x - 2, y - 1, piece, dataBoard, ret);
				}
			}
			return ret;
		}

		if(piece instanceof Elephant){
			if(piece.getSide() == 'R'){
				if(y + 2  <= 9){ // Down
					if(x + 2 <= 8 && noPiece(x + 1, y + 1, dataBoard)){ // Bottom right
							addToList(x + 2, y + 2, piece, dataBoard, ret);
					}
					if(x - 2 >= 0 && noPiece(x - 1, y + 1, dataBoard)){ // Bottom left
							addToList(x - 2, y + 2, piece, dataBoard, ret);
					}

				}
				if(y - 2 >= 5){ // Up
					if(x + 2 <= 8 && noPiece(x + 1, y - 1, dataBoard)){ // Upper right
							addToList(x + 2, y - 2, piece, dataBoard, ret);
					}
					if(x - 2 >= 0 && noPiece(x - 1, y - 1, dataBoard)){ // Upper left
							addToList(x - 2, y - 2, piece, dataBoard, ret);
					}
				}
				return ret;
			}
			else{
				if(y + 2  <= 4){ // Down
					if(x + 2 <= 8 && noPiece(x + 1, y + 1, dataBoard)){ // Bottom right
							addToList(x + 2, y + 2, piece, dataBoard, ret);
					}
					if(x - 2 >= 0 && noPiece(x - 1, y + 1, dataBoard)){ // Bottom left
							addToList(x - 2, y + 2, piece, dataBoard, ret);
					}

				}
				if(y - 2 >= 0){ // Up
					if(x + 2 <= 8 && noPiece(x + 1, y - 1, dataBoard)){ // Upper right
							addToList(x + 2, y - 2, piece, dataBoard, ret);
					}
					if(x - 2 >= 0 && noPiece(x - 1, y - 1, dataBoard)){ // Upper left
							addToList(x - 2, y - 2, piece, dataBoard, ret);
					}
				}
				return ret;
			}
		}

		if(piece instanceof Advisor){
			if(piece.getSide() == 'R'){
				if(x == 3 || x == 5){ // Advisor at corner
					addToList(4, 8, piece, dataBoard, ret);
				}
				else{ // Advisor at center
					addToList(3, 9, piece, dataBoard, ret);
					addToList(3, 7, piece, dataBoard, ret);
					addToList(5, 9, piece, dataBoard, ret);
					addToList(5, 7, piece, dataBoard, ret);
				}
			}
			else{
				if(x == 3 || x == 5){ // Advisor at corner
					addToList(4, 1, piece, dataBoard, ret);
				}
				else{ // Advisor at center
					addToList(3, 0, piece, dataBoard, ret);
					addToList(3, 2, piece, dataBoard, ret);
					addToList(5, 0, piece, dataBoard, ret);
					addToList(5, 2, piece, dataBoard, ret);
				}
			}
			return ret;
		}

		if(piece instanceof General){
			if(piece.getSide() == 'R'){
				if(x - 1 >= 3){
					addToList(x - 1, y, piece, dataBoard, ret);
				}
				if(x + 1 <= 5){
					addToList(x + 1, y, piece, dataBoard, ret);
				}
				if(y - 1 >= 7){
					addToList(x, y - 1, piece, dataBoard, ret);
				}
				if(y + 1 <= 9){
					addToList(x, y + 1, piece, dataBoard, ret);
				}
			}
			else{
				if(x - 1 >= 3){
					addToList(x - 1, y, piece, dataBoard, ret);
				}
				if(x + 1 <= 5){
					addToList(x + 1, y, piece, dataBoard, ret);
				}
				if(y - 1 >= 0){
					addToList(x, y - 1, piece, dataBoard, ret);
				}
				if(y + 1 <= 2){
					addToList(x, y + 1, piece, dataBoard, ret);
				}
			}

			
			return ret;
		}

		if(piece instanceof Cannon){
			int x2 = x + 1 ;
			while(x2 <= 8){
				if(noPiece(x2, y, dataBoard)){
					addToList(x2, y, piece, dataBoard, ret);
				}
				else{
					x2++;
					break;
				}
				x2++;
			}
			while(x2 <= 8){
				if(!noPiece(x2, y, dataBoard)){
					addToList(x2, y, piece, dataBoard, ret);
					break;
				}
				x2++;
			}

			x2 = x - 1;
			while(x2 >= 0){
				if(noPiece(x2, y, dataBoard)){
					addToList(x2, y, piece, dataBoard, ret);
				}
				else{
					x2--;
					break;
				}
				x2--;
			}
			while(x2 >= 0){
				if(!noPiece(x2, y, dataBoard)){
					addToList(x2, y, piece, dataBoard, ret);
					break;
				}
				x2--;
			}

			int y2 = y + 1;
			while(y2 <= 9){
				if(noPiece(x, y2, dataBoard)){
					addToList(x, y2, piece, dataBoard, ret);
				}
				else{
					y2++;
					break;
				}
				y2++;
			}
			while(y2 <= 9){
				if(!noPiece(x, y2, dataBoard)){
					addToList(x, y2, piece, dataBoard, ret);
					break;
				}
				y2++;
			}

			y2 = y - 1;
			while(y2 >= 0){
				if(noPiece(x, y2, dataBoard)){
					addToList(x, y2, piece, dataBoard, ret);
				}
				else{
					y2--;
					break;
				}
				y2--;
			}
			while(y2 >= 0){
				if(!noPiece(x, y2, dataBoard)){
					addToList(x, y2, piece, dataBoard, ret);
					break;
				}
				y2--;
			}
			return ret;
		}

		if(piece instanceof Chariot){
			
			int x2 = x + 1;
			while(x2 <= 8){
				addToList(x2, y, piece, dataBoard, ret);
				if(!noPiece(x2, y, dataBoard)){
					break;
				}
				x2++;
			}
			x2 = x - 1;
			while(x2 >= 0){
				addToList(x2, y, piece, dataBoard, ret);
				if(!noPiece(x2, y, dataBoard)){
					break;
				}
				x2--;
			}
			int y2 = y + 1;
			while(y2 <= 9){
				addToList(x, y2, piece, dataBoard, ret);
				if(!noPiece(x, y2 + 1, dataBoard)){
					break;
				}
				y2++;
			}
			y2 = y - 1;
			while(y2 >= 0){
				addToList(x, y2, piece, dataBoard, ret);
				if(!noPiece(x, y2, dataBoard)){
					break;
				}
				y2--;
			}
			return ret;
		}
		return null;
	}

// Helper function makes sure that piece is not moving in a space occupied by own side's piece
	public void addToList(int x, int y, Piece piece, Piece[][] dataBoard, ArrayList<Point2D> ret){
		if(dataBoard[y][x] == null || dataBoard[y][x].getSide() != piece.getSide()){
			Point2D newPoint = new Point2D(x, y);
			ret.add(newPoint);
		}
	}
//Makes sure there isn't a piece blocking it before showing valid points
//True if no piece exists at dataBoard[x][y], false otherwise
	public boolean noPiece(int x, int y, Piece[][] dataBoard){ 
		if(x < 0 || x > 8 || y < 0 || y > 9){
			return false; // Out of bounds
		}
		return dataBoard[y][x] == null;
	}
}