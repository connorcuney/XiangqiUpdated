package Piece;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.shape.Rectangle;
import java.lang.Math;
//Extended by all piece classes
public class Piece{
	double x;
	double y;
	int pane_x;
	int pane_y;
	double alpha;
	char side; // "R" or "B"
	Pane pane;
	final double RATIO = 0.6;
	ImageView image_view;
	
	double org_sceneX, org_sceneY, org_translateX, org_translateY;
	

	public Piece(double x, double y, double alpha, char side, Pane pane, boolean togglePiece){
		this.x = x;
		this.y = y;
		this.alpha = alpha;
		this.side = side;
		this.pane = pane;
	}

	// Accessors
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public int getPaneX(){
		return pane_x;
	}
	public int getPaneY(){
		return pane_y;
	}
	public Pane getPane(){
		return pane;
	}
	public double getAlpha(){
		return alpha;
	}
	public ImageView getImageView(){
		return image_view;
	}
	public char getSide(){
		return side;
	}

	// Modifiers
	public void setPaneXY(int x, int y){
		this.pane_x = x;
		this.pane_y = y;
	}
	public void setAlpha(double alpha){
		this.alpha = alpha;
		image_view.setOpacity(alpha);
	}

// Helper Functions
	public ImageView createImageView(String image_location){
		Image image = new Image(image_location);
		ImageView image_view = new ImageView(image);
		image_view.setFitWidth(image.getWidth() * RATIO);
		image_view.setPreserveRatio(true);
		image_view.setSmooth(true);
		image_view.setCache(true);
		return image_view;
	}

	public void setupImageView(){
		image_view.setOpacity(alpha);
		image_view.setMouseTransparent(true);
		pane.getChildren().add(this.image_view);
		image_view.relocate(x * RATIO, y * RATIO);
	}

	public void updateImageView(){
		pane.getChildren().remove(this.image_view);
		image_view.setOpacity(1.0);
		pane.getChildren().add(this.image_view);
	}
	
	
	public void addDragAndDrop(){
		image_view.setOnMousePressed(new EventHandler<MouseEvent>() {
		  public void handle(MouseEvent e) {
		    org_sceneX = e.getSceneX();
			org_sceneY = e.getSceneY();
			org_translateX = ((ImageView)(e.getSource())).getTranslateX();
			org_translateY = ((ImageView)(e.getSource())).getTranslateY();
		  }
		});
		image_view.setOnMouseReleased(new EventHandler<MouseEvent>() {
		  public void handle(MouseEvent e) {
		  	double end_sceneX = e.getSceneX();
		  	double end_sceneY = e.getSceneY();
	  		((ImageView)(e.getSource())).setTranslateX(org_translateX);
	  		((ImageView)(e.getSource())).setTranslateY(org_translateY);
		  }
		});
		image_view.setOnMouseDragged(new EventHandler<MouseEvent>() {
		  public void handle(MouseEvent e) {
		    double offsetX = e.getSceneX() - org_sceneX;
            double offsetY = e.getSceneY() - org_sceneY;
            double new_translateX = org_translateX + offsetX;
            double new_translateY = org_translateY + offsetY;
            ((ImageView)(e.getSource())).setTranslateX(new_translateX);
            ((ImageView)(e.getSource())).setTranslateY(new_translateY);
		  }
		});
	}

}