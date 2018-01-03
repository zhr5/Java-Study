package application;

import java.util.Comparator;

public class StudentComparator3 implements Comparator<Student> {

	@Override
	public int compare(Student s1, Student s2) {
		// TODO Auto-generated method stub
		if(s1.getScore()<s2.getScore()) {
			return 1;
		}else {
			return -1;
		}
	}

}
