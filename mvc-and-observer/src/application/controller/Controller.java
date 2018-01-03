package application.controller;

import application.model.Message;
import application.view.MessagePane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class Controller {
	@FXML private MessagePane pane;
	private Message message;
	private static int number=1;
@FXML
public void clickMe() {
	System.out.println("CLick");
	message=Message.getInstance();
	++number;
	message.setContent("Clicke"+number);

}
}
