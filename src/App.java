/**
 * App.java
 * Main entry point for the Student Course Management System.
 * Handles user interaction, course storage, file I/O, and recursive calculations.
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class App {
    
    private static ArrayList<Course> courseList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        System.out.println("=== Starting Course Management System ===");

        while (running) {
            displayMenu();
            
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    viewAllCourses();
                    break;
                case 3:
                    searchCourse();
                    break;
                case 4:
                    computeTotalUnits();
                    break;
                case 5:
                    saveToFile();
                    break;
                case 6:
                    loadFromFile();
                    break;
                case 7:
                    System.out.println("\n[Action]: Exiting System. Goodbye!");
                    running = false; 
                    break;
                default:
                    System.out.println("\n[Error]: Invalid selection. Please try again.");
            }
        }
        scanner.close(); 
    }

    // Displays the main interactive menu
    private static void displayMenu() {
        System.out.println("\n=======================================");
        System.out.println("   STUDENT COURSE MANAGEMENT SYSTEM");
        System.out.println("=======================================");
        System.out.println("1. Add Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search Course by Code");
        System.out.println("4. Compute Total Units");
        System.out.println("5. Save to File");
        System.out.println("6. Load from File");
        System.out.println("7. Exit Program");
        System.out.print("Enter your choice (1-7): ");
    }

    // Prompts user for course details and adds it to the list
    private static void addCourse() {
        System.out.println("\n--- Add a New Course ---");
        System.out.print("Enter Course Code (e.g., COS 201): ");
        String code = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Enter Course Units: ");
        int units = scanner.nextInt();
        scanner.nextLine(); 
        
        Course newCourse = new Course(code, title, units);
        courseList.add(newCourse);
        
        System.out.println("[Success]: " + code + " has been added.");
    }

    // Iterates through and displays all recorded courses
    private static void viewAllCourses() {
        System.out.println("\n--- All Recorded Courses ---");
        
        if (courseList.isEmpty()) {
            System.out.println("[Info]: No courses recorded yet.");
            return; 
        }
        
        for (Course course : courseList) {
            System.out.println(course); 
        }
    }

    // Searches for a specific course by its code (case-insensitive)
    private static void searchCourse() {
        System.out.println("\n--- Search for a Course ---");
        
        if (courseList.isEmpty()) {
            System.out.println("[Info]: No courses recorded yet.");
            return;
        }

        System.out.print("Enter Course Code to search: ");
        String searchCode = scanner.nextLine().trim();

        boolean found = false;
        
        for (Course course : courseList) {
            if (course.getCourseCode().equalsIgnoreCase(searchCode)) {
                System.out.println("\n[Course Found]:");
                System.out.println(course); 
                found = true;
                break; 
            }
        }

        if (!found) {
            System.out.println("[Error]: Course '" + searchCode + "' not found.");
        }
    }

    // Initiates the recursive calculation of total units
    private static void computeTotalUnits() {
        if (courseList.isEmpty()) {
            System.out.println("[Info]: No courses recorded. Total Units: 0");
            return;
        }

        int total = calculateUnitsRecursive(0);
        System.out.println("\n[Result]: Total Credit Units: " + total);
    }

    // Recursive helper method to sum course units
    private static int calculateUnitsRecursive(int index) {
        if (index >= courseList.size()) {
            return 0; // Base case
        }
        return courseList.get(index).getUnits() + calculateUnitsRecursive(index + 1); // Recursive step
    }

    // Saves the current course list to a text file using comma separation
    private static void saveToFile() {
        System.out.println("\n--- Saving to File ---");
        try {
            FileWriter writer = new FileWriter("courses.txt");
            for (Course c : courseList) {
                writer.write(c.getCourseCode() + "," + c.getCourseTitle() + "," + c.getUnits() + "\n");
            }
            writer.close(); 
            System.out.println("[Success]: Courses saved to courses.txt");
        } catch (Exception e) {
            System.out.println("[Error]: Could not save to file. " + e.getMessage());
        }
    }

    // Loads courses from the text file into memory
    private static void loadFromFile() {
        System.out.println("\n--- Loading from File ---");
        try {
            File file = new File("courses.txt");
            if (!file.exists()) {
                System.out.println("[Info]: No saved file found.");
                return;
            }
            
            Scanner fileReader = new Scanner(file);
            courseList.clear(); 
            
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(","); 
                
                if (parts.length == 3) {
                    String code = parts[0];
                    String title = parts[1];
                    int units = Integer.parseInt(parts[2]); 
                    
                    courseList.add(new Course(code, title, units));
                }
            }
            fileReader.close();
            System.out.println("[Success]: Courses loaded successfully.");
        } catch (Exception e) {
            System.out.println("[Error]: Could not read file. " + e.getMessage());
        }
    }
}