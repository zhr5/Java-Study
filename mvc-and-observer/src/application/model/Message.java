package application.model;

import java.util.ArrayList;
import java.util.List;

public class Message implements ISubject{
   private String content;
   private static Message instance=null;
   private List<IView> list;
   private Message() {
	   this.content="one";
	   list=new ArrayList<>();
   }
   
   public static Message getInstance() {
	   if(instance==null)
		   instance=new Message();
	   return instance;
   }
   public void setContent(String content) {
	   this.content=content;
	   notifyViews();
   }
   
   public String getContent() {
	   return this.content;
   }

@Override
public void addView(IView view) {
	// TODO Auto-generated method stub
	list.add(view);
}

@Override
public void removeView(IView view) {
	// TODO Auto-generated method stub
	list.remove(view);
}

@Override
public void notifyViews() {
	// TODO Auto-generated method stub
	for(IView v:list) {
		v.update();
	}
}
}
