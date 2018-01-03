package application;

import java.util.Comparator;

public class StudentComparator2 implements Comparator<Student> {

	@Override
	public int compare(Student s1, Student s2) {
		// TODO Auto-generated method stub
		if(s1.getName().compareTo(s2.getName())<0) {
			return 1;
		}else {
			return -1;
		}
	}

}
