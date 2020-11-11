package sample;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

 //user can select a student and get print out courses taken and the grades
   //       and the average grade for student and a course
//       and get average
     //    grade on a selected course.

public class StudentView {

    StudentModel model;
    Controller control;
    private GridPane Startview;
    //Labels
    //Select student and print out course of the student and grade and average grade

    Label StudentsHeader = new Label("Find grades and courses taken, and the average grade for a selected student (and course)");
    Label StudentsLbl = new Label("Select Student");

    //Find average grade on a selected course
    Label CourseHeader = new Label("Find average grade on a selected course");
    Label SelectCourseLbl = new Label("Select a Course and Semester");
    Label SemesterLbl = new Label("Select Semester");

    //Buttons
    Button PrintOutResults = new Button("Show Results");
    Button exitBtn = new Button("Exit");



    TextArea StudentText = new TextArea();

    ComboBox<String> StudentComB = new ComboBox<String>();
    ComboBox<String> CoursesComB = new ComboBox<String>();
    ComboBox<Integer> GradesComB = new ComboBox<Integer>();
    ComboBox<Integer> AvgGradeComb = new ComboBox<Integer>();
    ComboBox<Integer> AvgCourseComb = new ComboBox<Integer>();

    //ComboBox<Integer> AverageGradeComB=new ComboBox<Integer>();
    //ComboBox<Integer> AverageGradeCourseComb=new ComboBox<Integer>();

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

        Startview.add(StudentsHeader,1,1);
        Startview.add(StudentsLbl,1,3);
        ObservableList<String>StudentList = control.getStudents();
        StudentComB.setItems(StudentList);
        StudentComB.getSelectionModel().selectFirst();
        Startview.add(StudentComB,4,3);


        Startview.add(CourseHeader,1,7);
        Startview.add(SelectCourseLbl,1,9);

        Startview.add(exitBtn,1,25);
        Startview.add(PrintOutResults,1,10);
        Startview.add(StudentText, 1,12,15,7);


        ObservableList<String>CourseList = control.getCourses();
        CoursesComB.setItems(CourseList);
        CoursesComB.getSelectionModel().selectFirst();
        Startview.add(CoursesComB,4,9);

        ObservableList<Integer>GradesList = control.getGrades();
        GradesComB.setItems(GradesList);
        GradesComB.getSelectionModel().selectFirst();

        ObservableList<Integer>AverageGradesList = control.getAvgGrades();
        GradesComB.setItems(AverageGradesList);
        GradesComB.getSelectionModel().selectFirst();

        ObservableList<Integer>AverageCourseList = control.getAvgCourse();
        GradesComB.setItems(AverageCourseList);
        GradesComB.getSelectionModel().selectFirst();


    }
        public Parent asParent(){
            return Startview;
        }

}
