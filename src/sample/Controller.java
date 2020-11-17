package sample;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Handler;

public class Controller {
    StudentModel model;
    StudentView view;

    public Controller(StudentModel model){

        this.model=model;

        try{
            model.connect();
            model.CreateStatement();

        }catch (SQLException e){
        e.printStackTrace();
        System.out.println(e.getMessage());
    }
}

    public ObservableList<String> getStudents(){
        ArrayList<String> SNames = model.StudentNameQuerystmt();
        ObservableList<String> StudentNames = FXCollections.observableArrayList(SNames);
        return StudentNames;
    }

    public ObservableList<String> getCourses(){
        ArrayList<String> CNames = model.CourseNameQuerystmt();
        ObservableList <String> CourseNames = FXCollections.observableArrayList(CNames);
        return CourseNames;
    }

    /*public ObservableList<Integer> getGrades(){
        ArrayList<Integer> Grades = model.GradesQuerystmt();
        ObservableList<Integer> SGrade = FXCollections.observableArrayList(Grades);

        return SGrade;
    }

    public ObservableList<Integer> getAvgCourse(){
        ArrayList<Integer> AvgGradesCourse = model.AverageCourseQuerystmt();
        ObservableList<Integer> AvgGradesCourseObs = FXCollections.observableArrayList(AvgGradesCourse);

        return AvgGradesCourseObs;
    }

    public  ObservableList<Integer> getAvgGrades(){
        ArrayList<Integer> AvgGrades = model.AverageGradesQuerystmt();

        ObservableList<Integer> AvgGradesObs = FXCollections.observableArrayList(AvgGrades);
        return AvgGradesObs;
    }*/


    public void setView(StudentView view){
       this.view=view;
       view.exitBtn.setOnAction(e-> Platform.exit());
       EventHandler<ActionEvent> DisplayPrintOutStudent = e-> HandlePrintPart(view.StudentComB.getValue(),
               view.CoursesComB.getValue(),view.StudentText);

        view.PrintOutResults.setOnAction(DisplayPrintOutStudent);
    }

     public void HandlePrintPart(String Students, String Courses, TextArea studentText){

          studentText.clear();
          model.preparedStmtToFromQuery();
          ArrayList<PrintOutStudent>Print = model.FindPrintOutStudent(Students);
          System.out.println("ArraySize;  " + Print.size());
            for(int i=0; i<Print.size(); i++){
                studentText.appendText(Print.get(i).StudentName + " has taken the courses: "
                        + Print.get(i).CoursesTaken + "\n" + (Print.get(i).StudentName + " has gotten the grades: "
                          + Print.get(i).TotalGrades +
                        " and has the average grade: " + Print.get(i).AvgGrades ));




            }


 }
}
