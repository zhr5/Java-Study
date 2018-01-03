package application;
import java.io.*;
import java.util.Scanner;
import java.util.TreeSet;
public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner input1=new Scanner(System.in);
		boolean flag=true;
		while(flag) {
			System.out.println("请选择排序方式：1.按学号  2.按姓名  3.按java成绩 4.退出");
			int choose;
			choose=input1.nextInt();
			switch(choose) {
				case 1:case1();break;
				case 2:case2();break;
				case 3:case3();break;
				case 4: flag=false;
						System.out.println("退出!");
						break;
			}
		}	
		input1.close();
		
}
	public static void case1() throws FileNotFoundException {
		File file=new File("students.txt");	
	
		Scanner input=new Scanner(file);
		TreeSet<Student> students=new TreeSet<Student>(new StudentComparator1());
		while(input.hasNext()) {
			Student s=new Student(input.nextInt(),input.next(),input.nextInt());
			students.add(s);
		}
		for(Student s:students) {
			System.out.println(s.ToString());
		}
		input.close();		
}
	
	public static void case2() throws FileNotFoundException {
		File file=new File("students.txt");
		
		Scanner input=new Scanner(file);
		TreeSet<Student> students=new TreeSet<Student>(new StudentComparator2());
		while(input.hasNext()) {
			Student s=new Student(input.nextInt(),input.next(),input.nextInt());
			students.add(s);
		}
		for(Student s:students) {
			System.out.println(s.ToString());
		}
		input.close();		
}	

	public static void case3() throws FileNotFoundException {
		File file=new File("students.txt");
		
		Scanner input=new Scanner(file);
		TreeSet<Student> students=new TreeSet<Student>(new StudentComparator3());
		while(input.hasNext()) {
			Student s=new Student(input.nextInt(),input.next(),input.nextInt());
			students.add(s);
		}
		for(Student s:students) {
			System.out.println(s.ToString());
		}
		input.close();		
	}

}
