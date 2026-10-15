/**
 * Course.java
 * Represents a single academic course with a code, title, and credit units.
 */
public class Course {
    private String courseCode;
    private String courseTitle;
    private int units;

    public Course(String courseCode, String courseTitle, int units) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.units = units;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getUnits() {
        return units;
    }

    @Override
    public String toString() {
        return courseCode + " | " + courseTitle + " | Units: " + units;
    }
}