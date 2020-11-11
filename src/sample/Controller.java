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
        ArrayList<String> SNames= model.StudentNameQuerystmt();
        ObservableList<String> StudentNames = FXCollections.observableArrayList(SNames);
        return StudentNames;
    }

    public ObservableList<String> getCourses(){
        ArrayList<String> CNames= model.CourseNameQuerystmt();
        ObservableList <String> CourseNames = FXCollections.observableArrayList(CNames);
        return CourseNames;
    }

   // public ObservableList<Integer> getAvgCourse(){
    //    ArrayList<Integer> avgCourse = model.CourseNameQuerystmt();
     //   ObservableList <Integer> AvgC = FXCollections.observableArrayList(avgCourse);
     //   return AvgC;
   // }




    public  ObservableList<Integer> getGrades(){
        ArrayList<Integer> Grades = new ArrayList<>();
        for(int i=0;i<12;i++){
            Grades.add(i);
        }

        ObservableList<Integer> GradesObs = FXCollections.observableArrayList(Grades);
        return GradesObs;
    }

    public  ObservableList<Integer> getAvgGrades(){
        ArrayList<Integer> AvgGrades = new ArrayList<>();
        for(int i=0;i<12;i++){
            AvgGrades.add(i);
        }

        ObservableList<Integer> AvgGradesObs = FXCollections.observableArrayList(AvgGrades);
        return AvgGradesObs;
    }


}

   // public void setView(StudentView view){
    //    this.view=view;
     //   view.exitBtn.setOnAction(e-> Platform.exit());
     //   EventHandler<ActionEvent> PrintFirstPart = e->HandlePrintFirstPart(view.StudentComB.getValue(), view.CoursesComB.getValue(),
      //          view.GradesComB.getValue(), view.StudentText);
      //  view.PrintOutResults.setOnAction(PrintFirstPart);
   // }

     // public void HandlePrintFirstPart(String Students, String Courses, Integer Grades,
   //         TextArea studentText){
//
       //     StudentText.clear();
       //     StudentText.appendText("Student Name:                , Courses:           ,Grades:\n");
       //     model.preparedStmtToFromQuery();
       //     double AverageGrade = ((double) Grades/2);
       //     ArrayList<PrintOut>Print=model.FindPrintOut();




//}
//user can select a student and get print out courses taken and the grades
//       and the average grade for student and a course
//       and get average
//    grade on a selected course.