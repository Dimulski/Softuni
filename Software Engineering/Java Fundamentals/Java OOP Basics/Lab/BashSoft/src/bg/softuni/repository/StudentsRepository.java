package bg.softuni.repository;

import bg.softuni.models.Course;
import bg.softuni.models.Student;
import bg.softuni.staticData.SessionData;
import bg.softuni.io.OutputWriter;
import bg.softuni.staticData.ExceptionMessages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentsRepository {

    private LinkedHashMap<String, Course> courses;
    private LinkedHashMap<String, Student> students;

    private boolean isDataInitialized;
    private RepositoryFilter filter;
    private RepositorySorter sorter;

    public StudentsRepository(RepositoryFilter filter, RepositorySorter sorter) {
        this.filter = filter;
        this.sorter = sorter;
    }

    public void loadData(String fileName) throws IOException {
        if (this.isDataInitialized) {
            OutputWriter.displayException(ExceptionMessages.DATA_ALREADY_INITIALIZED);
            return;
        }

        this.students = new LinkedHashMap<>();
        this.courses = new LinkedHashMap<>();
        readData(fileName);
    }

    public void unloadData() {
        if (!this.isDataInitialized) {
            OutputWriter.displayException(ExceptionMessages.DATA_NOT_INITIALIZED);
        }

        this.students = null;
        this.courses = null;
        this.isDataInitialized = false;
    }

    private void readData(String fileName) throws IOException {
        String regex = "([A-Z][a-zA-Z#\\+]*_[A-Z][a-z]{2}_\\d{4})\\s+([A-Za-z]+\\d{2}_\\d{2,4})\\s([\\s0-9]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;

        String path = SessionData.currentPath + "\\" + fileName;
        List<String> lines = Files.readAllLines(Paths.get(path));

        // int lineIndex ?
        for (String line : lines) {
            matcher = pattern.matcher(line);

            if (!line.isEmpty() && matcher.find()) {
                String courseName = matcher.group(1);
                String studentName = matcher.group(2);
                String scoresStr = matcher.group(3);
                try {
                    String[] splitScores = scoresStr.split("\\s+");
                    int[] scores = new int[scoresStr.length()];
                    for (int i = 0; i < splitScores.length; i++) {
                        scores[i] = Integer.parseInt(splitScores[i]);
                    }
                    if (Arrays.stream(scores).anyMatch(score -> score > 100 || score < 0)) {
                        OutputWriter.displayException(ExceptionMessages.INVALID_SCORE);
                        continue;
                    }
                    if (scores.length > Course.NUMBER_OF_TASKS_ON_EXAM) {
                        OutputWriter.displayException(ExceptionMessages.INVALID_NUMBER_OF_SCORES);
                        continue;
                    }
                    if (!this.students.containsKey(studentName)) {
                        this.students.put(studentName, new Student(studentName));
                    }
                    if (!this.courses.containsKey(courseName)) {
                        this.courses.put(courseName, new Course(courseName));
                    }
                    Course course = this.courses.get(courseName);
                    Student student = this.students.get(studentName);
                    student.enrollInCourse(course);
                    student.serMarksInCourse(courseName, scores);
                    course.enrollStudent(student);
                } catch (NumberFormatException nfe) {
                    OutputWriter.displayException(nfe.getMessage()); // + " at line: " + lineIndex
                }
            }
        }

        isDataInitialized = true;
        OutputWriter.writeMessageOnNewLine("Data read.");
    }

    public void getStudentMarkInCourse(String courseName, String studentName) { // done
        if (!isQueryForStudentPossible(courseName, studentName)) {
            return;
        }

        double mark = this.courses.get(courseName).studentsByName
                .get(studentName).marksByCourseName.get(courseName);
        OutputWriter.printStudent(studentName, mark);
    }

    public void getStudentsByCourse(String courseName) {
        if (!isQueryForCoursePossible(courseName)) {
            return;
        }

        OutputWriter.writeMessageOnNewLine(courseName + ":");
        for (Map.Entry<String, Student> student : this.courses.get(courseName).studentsByName.entrySet()) {
            this.getStudentMarkInCourse(courseName, student.getKey());
        }
    }

    private boolean isQueryForCoursePossible(String courseName) {
        if (!isDataInitialized) {
            OutputWriter.displayException(ExceptionMessages.DATA_NOT_INITIALIZED);
            return false;
        }

        if (!this.courses.containsKey(courseName)) {
            OutputWriter.displayException(ExceptionMessages.NON_EXISTING_COURSE);
            return false;
        }

        return true;
    }

    private boolean isQueryForStudentPossible(String courseName, String studentName) {
        if (!isQueryForCoursePossible(courseName)) {
            return false;
        }

        if (!this.courses.get(courseName).studentsByName.containsKey(studentName)) {
            OutputWriter.displayException(ExceptionMessages.NON_EXISTING_STUDENT);
            return false;
        }

        return true;
    }

    public void filterAndTake(String courseName, String filter) {
        int studentsToTake = this.courses.get(courseName).studentsByName.size();
        filterAndTake(courseName, filter, studentsToTake);
    }

    public void filterAndTake(
            String courseName, String filter, int studentsToTake) {
        if (!isQueryForCoursePossible(courseName)) {
            return;
        }

        LinkedHashMap<String, Double> marks = new LinkedHashMap<>();
        for (Map.Entry<String, Student> entry : this.courses.get(courseName).studentsByName.entrySet()) {
            marks.put(entry.getKey(), entry.getValue().marksByCourseName.get(courseName));
        }

        this.filter.printFilteredStudents(marks, filter, studentsToTake);
    }

    public void orderAndTake(
            String courseName, String orderType, int studentsToTake) {
        if (!isQueryForCoursePossible(courseName)) {
            return;
        }

        LinkedHashMap<String, Double> marks = new LinkedHashMap<>();
        for (Map.Entry<String, Student> entry : this.courses.get(courseName).studentsByName.entrySet()) {
            marks.put(entry.getKey(), entry.getValue().marksByCourseName.get(courseName));
        }

        this.sorter.printSortedStudents(marks, orderType, studentsToTake);
    }

    public void orderAndTake(String courseName, String orderType) {
        int studentsToTake = this.courses.get(courseName).studentsByName.size();
        orderAndTake(courseName, orderType, studentsToTake);
    }
}
