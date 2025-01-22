import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class StudentsManager{

    private ArrayList<Student> students;

    public StudentsManager(ArrayList<Student> students){
        this.students = students;
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    public void removeStudent(Student student){
        this.students.remove(students.indexOf(student));
    }

    public void setStudentGrades(Student student, Map<String, Double> classGrades){
        student.setStudentScoreCard(classGrades);
    }
    
    public void displayStudentsInfo(StudentsManager sm){
        System.out.println("\nStudents\n");
        System.out.println("========================\n");
        ArrayList<Student> students = sm.getStudents();
        for(Student student : students){
            Map<String, Double> scoreCard = student.getStudentScoreCard();
            System.out.println(student.getStudentId() + " - "
                              +student.getStudentName() + ", "
                              +student.getStudentLastName() +" \n");
            try{
                for(String key : scoreCard.keySet()){
                    System.out.println(key + ": " + scoreCard.get(key));
                }
            }catch(NullPointerException e){
                System.out.println("Missing score card data for this Student. \n");
                System.out.println("========================\n");
            }
            System.out.println("__________________________\n");
            
        }
    }

    public Double calculateAverage(Student student){
        Map<String, Double> scoreCard = student.getStudentScoreCard();
        double total = 0.0;
        double avrg = 0.0;
        if(!scoreCard.isEmpty()){
            for(String className : scoreCard.keySet()){
                total += scoreCard.get(className);
            }
            avrg = total/scoreCard.size();
        }

        return avrg;
    }

    public void displayStudentsAverage(StudentsManager sm){
        DecimalFormat df=new DecimalFormat("#.##");
        ArrayList<Student> students = sm.getStudents();
        for(int i=0;i<students.size();i++){
            students.get(i).setAverage(sm.calculateAverage(students.get(i)));
            System.out.println(students.get(i).getStudentId()
                                +" | "+students.get(i).getStudentName() 
                                + ", "+students.get(i).getStudentLastName()
                                +" | average: "
                                + df.format(students.get(i).getAverage()));
        }
    }

    public void setStudents(ArrayList<Student> students){
        this.students = students;
    }

    public ArrayList<Student> getStudents(){
        return this.students;
    }

    public Student getStudentById(int id, ArrayList<Student> students) {
        for (Student student : students) {
            if (student.getStudentId() == id) {
                return student;
            }
        }
        return null;
    }


}
