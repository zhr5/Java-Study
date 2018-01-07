package Control;

import java.net.URL;
import java.util.ResourceBundle;
import Model.Queen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class MainControl implements Initializable{
	public Queen[] Pane=new Queen [8];
	Image image=new Image("/View/Queen.png");
	@FXML
	private AnchorPane root;
	@FXML
	private Canvas canvas;
	@FXML
	private TextArea note;
	@FXML
	protected void Click(MouseEvent e) {
		GraphicsContext gc=canvas.getGraphicsContext2D();
		note.clear();
		int point_x=(int)e.getX();
		int point_y=(int)e.getY();
		int status_x = 0;
		int status_y = 0;
		for(int i=0;i<8;i++) {
			if(point_x>(i)*64&&point_x<(i+1)*64) {
				status_x=i;
			}
			if(point_y>(i)*64&&point_y<(i+1)*64) {
				status_y=i;
			}
		}
		System.out.println(status_x+" "+status_y);
		if(Pane[status_x]==null||Pane[status_x].isPlaced()==false) {
			if(IsLegal(status_x,status_y)) {
				Queen queen=new Queen(true,status_y);
				Pane[status_x]=queen;
				gc.setFill(Color.PURPLE);
				gc.fillArc((status_x)*64, (status_y)*64, 64, 64, 0, 360, ArcType.ROUND);
			}
			
		}
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	//判断合法性
	public boolean IsLegal(int xIndex,int yIndex) {
		boolean flag = true;
		for(int i=0;i<xIndex;i++) {
			
			System.out.println(Pane[i].getY());
			
			if(Math.abs(i-xIndex)==Math.abs(Pane[i].getY()-yIndex)||yIndex==Pane[i].getY()) {
				System.out.println("不合法!");
				note.setText("不合法!");
				flag= false;
				break;
			}else {
					flag= true;
			}
		}
		return flag;
	}
}
