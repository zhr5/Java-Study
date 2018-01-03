package application.view;

import application.model.IView;
import application.model.Message;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class MessagePane extends Pane implements IView {
   private Message message;
   private Canvas canvas;
   private GraphicsContext gc;
   public MessagePane() {
	   this.message=Message.getInstance();
	   this.message.addView(this);
	   canvas=new Canvas(300,300);
	   this.gc=canvas.getGraphicsContext2D();
	   getChildren().add(canvas);
   }
   
   public void update() {
	  gc.strokeText(message.getContent(), 20,20);   
   }
}
