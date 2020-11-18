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
        //for(int i=0;i<7;i++){
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


        EventHandler<ActionEvent> AddGrade = e-> HandleAddGrade(view.GradesComB.getValue(), view.StudentComB.getSelectionModel().getSelectedIndex(), "C2");




        view.showRstBtn.setOnAction(DisplayPrintOutStudent);
        view.avgCourseBtn.setOnAction((DisplayPrintOutCourse));
        view.insertBtn.setOnAction(AddGrade);



    }

    public void HandleAddGrade(Integer grade, Integer Student, String course){
        /*
        System.out.println(grade);
        System.out.println(Student);
        System.out.println(course);
         */
        model.setGrades(course, Student, grade);
        System.out.println(view.StudentComB.getSelectionModel().getSelectedIndex());
    }

    public void HandlePrintStudent(String Students, TextArea studentText){

        studentText.clear();
        model.preparedStmtToFromQuery();
        ArrayList<StudentModel.PrintOutStudent> Print = model.FindPrintOutStudent(Students);


        for(int i=0; i<Print.size(); i++){
            // if (Print.get(i).TotalGrades == 0){
            //  studentText.appendText(Print.get(i).StudentName  +
            //  " has gotten null in this course. Please enter a grade!");

            //}
            studentText.appendText(Print.get(i).StudentName + " has taken the course "
                    + Print.get(i).CoursesTaken + ".\n" + Print.get(i).StudentName + " has gotten the grade "
                    + Print.get(i).TotalGrades + " in this course, " +
                    " \nand has the average grade " + Print.get(i).AvgGrades + " from all courses taken.\n\n");
            if(Print.get(i).TotalGrades==0){
                System.out.println("Bigschlong");
                studentText.appendText("typegrade\n");
                view.ChooseGradeLbl.setVisible(true);
                view.GradesComB.setVisible(true);
                view.insertBtn.setVisible(true);

                //køre metode der sender info til model
                //model.setGrades(Print.get(i).CoursesTaken, Print.get(i).StudentName);
//Print.get(i).CoursesTaken.//Prøveatbrugedettetilatsættekarakterenlignoget.
            }

        }

    }

    public void HandlePrintPrintCourse(String Courses, TextArea courseText){

        courseText.clear();
        model.preparedStmtToFromQuery();
        ArrayList<StudentModel.PrintOutCourse> Print = model.FindPrintOutCourse(Courses);

        for(int j=0; j<Print.size(); j++){
            courseText.appendText("The course " + Print.get(j).CourseName +
                    " has the average grade " + Print.get(j).AvgCourseGrade);
        }

    }

}