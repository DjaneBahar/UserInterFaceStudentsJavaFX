package sample;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
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

    public ObservableList<Integer> getSetGrades(){
        Integer [] setGrade = { -3, 0, 2, 4, 7, 10,12 };
        ObservableList<Integer> SetGrades = FXCollections.observableArrayList(setGrade);
        return SetGrades;
    }

    public void setView(StudentView view){
        this.view=view;

        view.exitBtn.setOnAction(e-> Platform.exit());

        EventHandler<ActionEvent> DisplayPrintOutStudent = e-> HandlePrintStudent(view.StudentComB.getValue(),
                view.StudentText);

        EventHandler<ActionEvent> DisplayPrintOutCourse = e-> HandlePrintPrintCourse(view.CoursesComB.getValue(),
               view.CourseText);


        EventHandler<ActionEvent> AddGrade = e-> HandleAddGrade(view.GradesComB.getValue(), getStudents().indexOf(view.StudentComB.getValue()), "C2", view.StudentComB.getValue());
        getStudents().indexOf(view.StudentComB.getValue());


        view.showRstBtn.setOnAction(DisplayPrintOutStudent);
        view.avgCourseBtn.setOnAction((DisplayPrintOutCourse));
        view.insertBtn.setOnAction(AddGrade);



    }

    public void HandleAddGrade(Integer grade, Integer Student, String course, String aStudent){
        model.setGrades(course, Student, grade, aStudent);
        System.out.println(Student);
        view.ChooseGradeLbl.setVisible(false);
        view.GradesComB.setVisible(false);
        view.insertBtn.setVisible(false);

    }

    public void HandlePrintStudent(String Students, TextArea studentText){

        studentText.clear();
        model.preparedStmtToFromQuery();
        ArrayList<StudentModel.PrintOutStudent> Print = model.FindPrintOutStudent(Students);


        for(int i=0; i<Print.size(); i++){
             if (Print.get(i).TotalGrades == 0){
              studentText.appendText(Print.get(i).StudentName  +
                " hasnt received a grade in Software Development 2020 in spring semester yet.\n" +
                "Please enter a grade above to proceed!\n\n");

            } else
            studentText.appendText(Print.get(i).StudentName + " has taken the course "
                    + Print.get(i).CoursesTaken + ".\n" + Print.get(i).StudentName + " has gotten the grade "
                    + Print.get(i).TotalGrades + " in this course, " +
                    " \nand has the average grade " + Print.get(i).AvgGrades + " from all courses taken.\n\n");

            if(Print.get(0).TotalGrades==0){
                view.ChooseGradeLbl.setVisible(true);
                view.GradesComB.setVisible(true);
                view.insertBtn.setVisible(true);
            } else {
                view.ChooseGradeLbl.setVisible(false);
                view.GradesComB.setVisible(false);
                view.insertBtn.setVisible(false);
            }

        }

    }

    public void HandlePrintPrintCourse(String Courses, TextArea courseText){

        courseText.clear();
        model.preparedStmtToFromQuery();
        ArrayList<StudentModel.PrintOutCourse> Print = model.FindPrintOutCourse(Courses);

        for(int j=0; j<Print.size(); j++){

            if (Print.get(j).AvgCourseGrade == 0){
                 courseText.appendText("The students haven't received any grades yet in " + Print.get(j).CourseName + ".\n"+
                                          "Remember to enter the grades above for all the students,\n" +
                                          "in order to get the total average grade of the course!\n\n");

            } else

            courseText.appendText("The course " + Print.get(j).CourseName +
                    " has the average grade " + Print.get(j).AvgCourseGrade);
        }

    }

}