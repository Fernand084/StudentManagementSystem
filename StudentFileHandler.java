import java.io.*;
import java.util.*;

public class StudentFileHandler {

    public void saveStudentsToFile(List<Student> students,String filePath){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for(Student student : students){
                writer.write(formatStudentData(student) + "\n");
            }
            System.out.println("Students data saved successfully to "+filePath);
        } catch (IOException e) {
            System.out.println("Error while saving student data: " + e.getMessage());
        }
    }

    public void appendStudentToFile(Student student, String filePath){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(formatStudentData(student) + "\n");
            System.out.println("Student data append  successfully to "+filePath);
        } catch (IOException e) {
            System.out.println("Error while saving student data: " + e.getMessage());
        }
    }

    // Método para cargar estudiantes desde el archivo
    public List<Student> loadStudentsFromFile(String filePath){
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = parseStudentData(line);
                students.add(student);
            }
            System.out.println("Data loaded from file.");
        } catch (IOException e) {
            System.out.println("Error while loading student data: " + e.getMessage());
        }
        return students;
    }

    // Formatea los datos de un estudiante para guardarlos en el archivo
    private String formatStudentData(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(student.getStudentId()).append(", ")
          .append(student.getStudentName()).append(" ")
          .append(student.getStudentLastName());
        for (Map.Entry<String, Double> entry : student.getStudentScoreCard().entrySet()) {
            sb.append(", ").append(entry.getKey()).append(": ").append(entry.getValue());
        }
        return sb.toString();
    }

    // Convierte una línea del archivo en un objeto Student
    private Student parseStudentData(String data) {
        String[] parts = data.split(",");
        String[] IdData = parts[0].split(":");
        int id = Integer.parseInt(IdData[1]);
        String[] nameParts = parts[1].split(" ");
        String firstName = nameParts[0];
        String lastName = nameParts[1];
        Map<String, Double> scoreCard = new TreeMap<>();

        for (int i = 2; i < parts.length; i++) {
            String[] gradeData = parts[i].split(":");
            scoreCard.put(gradeData[0], Double.parseDouble(gradeData[1]));
        }

        Student student = new Student(id,firstName, lastName);
        student.setStudentScoreCard(scoreCard);
        return student;
    }

    public void updateStudentInFile(String file, Student updatedStudent) {
        List<Student> students = loadStudentsFromFile(file); //TO FIX
                                                                                                                                                         
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId() == updatedStudent.getStudentId()) {
                students.set(i, updatedStudent);
                break;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Student student : students) {
                writer.write(formatStudentData(student));
                writer.newLine();
            }
            System.out.println("Students file updated.");
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

    public void updateFileAfterRemoval(String file) {
        List<Student> students = loadStudentsFromFile(file); //TO FIX
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Student student : students) { //TO FIX
                writer.write(formatStudentData(student)); // Convierte cada estudiante al formato de archivo.
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating file after removal: " + e.getMessage());
        }
    }
}
