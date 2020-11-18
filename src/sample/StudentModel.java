package sample;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class StudentModel {
    Connection conn= null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;
    PreparedStatement pstmt3 = null;
    String url;
    PreparedStatement pstmtAddGrade = null;

    public StudentModel(String url){
        this.url=url;
    }

    public void connect() throws SQLException {
        conn = getConnection(this.url);
    }
    public void CreateStatement() throws SQLException{
        this.stmt = conn.createStatement();
    }
    public ArrayList <String> StudentNameQuerystmt(){
        ArrayList <String> StudentNames = new ArrayList <String>();
        String sql = "SELECT name FROM Student ORDER BY name;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            while (rs!=null && rs.next()){
                String name = rs.getString(1);
                StudentNames.add(name);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return StudentNames;
    }

    public ArrayList <String> CourseNameQuerystmt(){
        ArrayList <String> CourseNames = new ArrayList <String>();
        String sql = "SELECT coursename FROM Course;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            while (rs!=null && rs.next()){
                String courseName = rs.getString(1);
                CourseNames.add(courseName);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return CourseNames;
    }

    public  void preparedStmtToFromQuery(){

        String sql="SELECT D1.name, D2.coursename, D3.grade " +
                "FROM Grades as D3 " +
                "JOIN Student as D1 ON D3.SID = D1.studentID " +
                "JOIN Course as D2 ON D3.CID = D2.courseID " +
                "WHERE D1.name = ? ;";


        String sqlAvgGrade ="SELECT  avg(D3.grade) " +
                "FROM Grades as D3 " +
                "JOIN Student as D1 ON D3.SID = D1.studentID " +
                "JOIN Course as D2 ON D3.CID = D2.courseID " +
                "WHERE D1.name = ? ;";


        String sqlAvgCourse = "SELECT  coursename, avg(grade) "+
                "FROM Grades " +
                "JOIN Course ON CID = courseID " +
                "WHERE coursename = ?; ";


        try {
            pstmt = conn.prepareStatement(sql);
            pstmt2 = conn.prepareStatement(sqlAvgGrade);
            pstmt3 = conn.prepareStatement(sqlAvgCourse);

        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<PrintOutStudent> FindPrintOutStudent(String name){
        ArrayList<PrintOutStudent> Print = new ArrayList<PrintOutStudent>();
        try {
            pstmt.setString(1, name);
            pstmt2.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            ResultSet rs2 = pstmt2.executeQuery();

            while (rs != null && rs.next()){
                PrintOutStudent print = new PrintOutStudent(rs.getString(1), rs.getString(2),
                        rs.getInt(3), rs2.getFloat(1));
                //
                Print.add(print);
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return Print;
    }

    public ArrayList<PrintOutCourse> FindPrintOutCourse(String coursename){
        ArrayList<PrintOutCourse> Print = new ArrayList<PrintOutCourse>();
        try {
            pstmt3.setString(1, coursename);


            ResultSet rs3 = pstmt3.executeQuery();

            while (rs3 != null && rs3.next()){
                PrintOutCourse print = new PrintOutCourse(rs3.getString(1), rs3.getFloat(2));
                //
                Print.add(print);
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return Print;


    }

    public void setGrades(String course, Integer student, Integer grade){
        //Lortet virker!!!
        System.out.println(course);
        System.out.println(student);
        System.out.println(grade);

        //Kunne måske have statement hvor jeg henter Studentid fra  Student klassen først. Men hvordan gjorde de i den anden? den med student.

        String sqlSetGrade = "UPDATE Grades " +
        "SET grade = " +
                grade +
        " WHERE SID = 'S" +
                (student + 1) +
                "'" +
                " AND CID = '" +
                course +
                "';";
        /*
        String sqlGetGrades="SELECT D1.name, D2.coursename, D3.grade " +
                "FROM Grades as D3 " +
                "JOIN Student as D1 ON D3.SID = D1.studentID " +
                "JOIN Course as D2 ON D3.CID = D2.courseID " +
                "WHERE D1.name = ? ;";

         */

        try {
            //Statement updAddGrade = conn.prepareStatement(sqlSetGrade);
            //updAddGrade.executeUpdate(sqlSetGrade);
            conn.createStatement().executeUpdate(sqlSetGrade);



        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    class PrintOutStudent{
        String StudentName;
        String CoursesTaken;
        Integer TotalGrades;
        float AvgGrades;

        public PrintOutStudent(String name, String courses, Integer grades, float avgGr){
//
            this.StudentName = name;
            this.CoursesTaken = courses;
            this.TotalGrades = grades;
            this.AvgGrades = avgGr;

        }

    }

    class PrintOutCourse{


        String CourseName;
        float AvgCourseGrade;

        public PrintOutCourse(String cName, float avgC){

            this.CourseName = cName;
            this.AvgCourseGrade = avgC;
        }
    }

}