package application;

import java.util.Comparator;

public class StudentComparator1 implements Comparator<Student> {

	@Override
	public int compare(Student s1, Student s2) {
		// TODO Auto-generated method stub
		if(s1.getID()<s2.getID()) {
			return 1;
		}else {
			return -1;
		}
	}

}
