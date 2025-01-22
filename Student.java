import java.util.Map;
import java.util.TreeMap;

public class Student{
    private int id;
    private String name;
    private String lastName;
    private double average;
    private Map<String, Double> scoreCard;

    public Student(){}

    public Student(Integer id, String name,String lastName){
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.scoreCard = new TreeMap<>();
    }

    public void addClassGrade(String className, double grade){
        this.scoreCard.put(className, grade);
    }

    public void setStudentId(int id){
        this.id = id;
    }

    public void setStudentName(String name){
        this.name = name;
    }

    public void setStudentLastName(String lastName){
        this.lastName = lastName;
    }

    public void setAverage(double average){
        this.average = average;
    }

    public void setStudentScoreCard(Map<String, Double> scoreCard){
        this.scoreCard = scoreCard;
    }

    public String getStudentName(){
        return this.name;
    }

    public String getStudentLastName(){
        return this.lastName;
    }

    public int getStudentId(){
        return this.id;
    }

    public double getAverage(){
        return this.average;
    }

    public Map<String, Double> getStudentScoreCard(){
        return this.scoreCard;
    }



}