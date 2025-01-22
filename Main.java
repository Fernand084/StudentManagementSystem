import java.util.*;

public class Main{
    
    public static void interaction(Scanner scn, StudentsManager sm, StudentFileHandler fh){
        clearScreen();
        boolean loop = true;
        while(loop){
            System.out.println("Student Management System");
            System.out.println("Enter a command:");
            System.out.println("- add: add students to system");
            System.out.println("- disp: display students");
            System.out.println("- setGrades: set student's grades");
            System.out.println("- save: save students list to a file");
            System.out.println("- appendStudent: save last student registered to file");
            System.out.println("- updateStudent: updates current student on file");
            System.out.println("- remove: remove a student from the system");
            String userInput = scn.nextLine();
            switch(userInput){
                case "add":
                    addCmd(scn, sm);
                    break;
                case "disp":
                    dispCmd(scn, sm);
                    break;
                case "setGrades":
                    setGradesCmd(scn, sm);
                    break;
                case "save":
                    saveStudentsToFileCmd(sm, fh);
                    break;
                case "saveStudent":
                    appendStudentCmd(scn, sm, fh);
                    break;
                case "updateStudent":
                    updateStudentCmd(scn, sm, fh);
                    break;
                case "remove":
                    removeStudentCmd(scn, sm, fh);
                    break;
                default:
                    clearScreen();
                    System.out.println("\nPlease enter a valid option.\n");
                    //scn.nextLine();
                    interaction(scn, sm, fh);
            }
            loop = false;
        }
    }

    public static void addCmd(Scanner scn, StudentsManager sm){
        clearScreen();
        System.out.println("Enter student data with this format: Id,First name(s),Last name(s)\n");
        boolean exit = false;
        while(!exit){ 
            String usrInput = scn.nextLine();
            if(usrInput.equals("exit")){
                exit = true;
            }
            try{
                String[] studentData = usrInput.split(",");
                Student student = new Student(Integer.valueOf(studentData[0]),studentData[1],studentData[2]);
                sm.addStudent(student);
            }catch(ArrayIndexOutOfBoundsException | NumberFormatException e){

            }
        }
        dispCmd(scn,sm);
    }

    public static void setGradesCmd(Scanner scn, StudentsManager sm){
        clearScreen();
        System.out.println("Enter student ID\n");
        String studentId = scn.nextLine();
        int id = 0;
        Student student = null;
        try{
            id = Integer.valueOf(studentId);
            student = sm.getStudentById(id, sm.getStudents());
        }catch(ArrayIndexOutOfBoundsException | NumberFormatException e){

        }
        
        if(student!=null){
            System.out.println("Enter student grades with this format: Class name,6.5\n");
            boolean exit = false;
            Map<String,Double> grades = new TreeMap<>();
            while(!exit){
                String usrInput = scn.nextLine();
                if(usrInput.equals("exit")){
                    exit = true;
                }
                try{
                    String[] gradesData = usrInput.split(",");
                    grades.put(gradesData[0], Double.valueOf(gradesData[1]));
                    student.setStudentScoreCard(grades);
                }catch(ArrayIndexOutOfBoundsException e){

                }
            }
            dispCmd(scn,sm);
        }else{
            System.out.println("Student does not exists.");
            scn.nextLine();
        }
    }

    public static void dispCmd(Scanner scn, StudentsManager sm){
        clearScreen();
        sm.displayStudentsInfo(sm);
        sm.displayStudentsAverage(sm);
        scn.nextLine();
    }

    public static void saveStudentsToFileCmd( StudentsManager sm, StudentFileHandler fh){
        clearScreen();
        System.out.println("Students saved to "+FILE_PATH);
        fh.saveStudentsToFile(sm.getStudents(),FILE_PATH);
    }

    public static void appendStudentCmd(Scanner scn, StudentsManager sm, StudentFileHandler fh){
        clearScreen();
        ArrayList<Student> students = sm.getStudents();
        if (!students.isEmpty()) {
            Student student = students.get(students.size() - 1);
            fh.appendStudentToFile(student, FILE_PATH); //TO FIX
            System.out.println("Student saved successfully.");
        } else {
            System.out.println("No students to save.");
        }
        scn.nextLine();
        interaction(scn, sm, fh);
    }

    public static void updateStudentCmd(Scanner scn, StudentsManager sm, StudentFileHandler fh) {
        clearScreen();
        System.out.println("Enter student ID to be updated:");
        String studentId = scn.nextLine();
        try {
            int id = Integer.parseInt(studentId);
            Student student = sm.getStudentById(id, sm.getStudents());
            
            if (student != null) {
                System.out.println("Current data: " + student.getStudentId() + " " 
                                + student.getStudentName() + " " + student.getStudentLastName());
                System.out.println("Enter new data in the format: Id,First name(s),Last name(s)");
                
                String usrInput = scn.nextLine();
                String[] studentData = usrInput.split(",");
                
                if(studentData.length == 3){
                    student.setStudentId(Integer.parseInt(studentData[0]));
                    student.setStudentName(studentData[1].trim());
                    student.setStudentLastName(studentData[2].trim());
                    fh.updateStudentInFile("students.txt", student);
                    System.out.println("Student updated successfully.");
                }else{
                    System.out.println("Incomplete data for student.");
                }
            } else {
                System.out.println("Student with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a numeric ID.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect format. Please enter ID, first name(s), and last name(s).");
        }
        scn.nextLine();
        interaction(scn, sm, fh);
    }

    public static void removeStudentCmd(Scanner scn, StudentsManager sm, StudentFileHandler fh) {
        clearScreen();
        System.out.println("Enter student ID to remove:");
        String studentId = scn.nextLine();
        try {
            int id = Integer.parseInt(studentId);
            Student student = sm.getStudentById(id, sm.getStudents());

            if (student != null) {
                sm.removeStudent(student);
                fh.updateFileAfterRemoval("students.txt"); // Asegúrate de tener este método implementado.
                System.out.println("Student removed successfully.");
            } else {
                System.out.println("Student with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a numeric ID.");
        }
        scn.nextLine();
        interaction(scn, sm, fh);
    }


    public static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static final String FILE_PATH = "students.txt";
    
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        StudentFileHandler fileHandler = new StudentFileHandler();
        
        // Cargar los estudiantes del archivo
        students.addAll(fileHandler.loadStudentsFromFile(FILE_PATH));
        
        StudentsManager manager = new StudentsManager(students);
        Scanner scn = new Scanner(System.in);

        // Iniciar interacción
        interaction(scn, manager, fileHandler);
    }
}
