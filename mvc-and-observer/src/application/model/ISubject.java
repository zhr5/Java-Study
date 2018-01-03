package application.model;

public interface ISubject {
  public void addView(IView view);
  public void removeView(IView view);
  public void notifyViews();
}
