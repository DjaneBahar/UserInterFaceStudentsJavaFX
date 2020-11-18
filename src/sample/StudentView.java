package sample;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class StudentView {

    StudentModel model;
    Controller control;
    private GridPane Startview;
    //Labels
    //Select student and print out course of the student and grade and average grade

    Label StudentsHeader = new Label("Find grades and courses taken, and the average grade for a selected student");
    Label StudentsLbl = new Label("Select Student: ");

    Label ChooseGradeLbl = new Label("Insert missing grade for SD2020:");


    //Find average grade on a selected course
    Label CourseHeader = new Label("Find average grade on a selected course");
    Label SelectCourseLbl = new Label("Select a Course and Semester: ");

    //Buttons
    Button avgCourseBtn = new Button("Show Average grade for course");
    Button showRstBtn = new Button("Show Results for student");
    Button exitBtn = new Button("Exit");
    Button insertBtn = new Button("Insert Grade");

    TextArea StudentText = new TextArea();
    TextArea CourseText = new TextArea();

    ComboBox<String> StudentComB = new ComboBox<String>();
    ComboBox<String> CoursesComB = new ComboBox<String>();
    ComboBox<Integer> GradesComB = new ComboBox<Integer>();

    public StudentView(StudentModel model, Controller control){
        this.model = model;
        this.control = control;
        createAndConfigure();

    }

    private void createAndConfigure() {

        Startview= new GridPane();
        Startview.setMinSize(100, 50);
        Startview.setPadding(new Insets(10,10,10,10));
        Startview.setVgap(5);
        Startview.setHgap(1);

        //Print for Students
        Startview.add(StudentsHeader,1,1);

        Startview.add(StudentsLbl,1,3);
        Startview.add(ChooseGradeLbl, 2,3);


        ObservableList<String>StudentList = control.getStudents();
        StudentComB.setItems(StudentList);
        StudentComB.getSelectionModel().selectFirst();
        Startview.add(StudentComB,1,5);
        Startview.add(showRstBtn,1,7);
        Startview.add(StudentText, 1,9,30,10);



        ObservableList<Integer>GradesList = control.getSetGrades();
        GradesComB.setItems(GradesList);
        GradesComB.getSelectionModel().selectFirst();
        Startview.add(GradesComB,2,5);

        Startview.add(insertBtn,2,7);

        //Print for Courses
        Startview.add(CourseHeader,1,21);
        Startview.add(SelectCourseLbl,1,23);

        ObservableList<String>CourseList = control.getCourses();
        CoursesComB.setItems(CourseList);
        CoursesComB.getSelectionModel().selectFirst();
        Startview.add(CoursesComB,1,25);
        Startview.add(avgCourseBtn,1,27);
        Startview.add(CourseText,1,29,30,10);

         Startview.add(exitBtn,1,41);

    }
        public Parent asParent(){
            return Startview;
        }

}
