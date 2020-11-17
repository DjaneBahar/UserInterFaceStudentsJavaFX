package sample;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class StudentModel {
    Connection conn= null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;
    String url;

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

        //String sqlAvgCourse = "SELECT D1.coursename, avg()";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt2 = conn.prepareStatement(sqlAvgGrade);

        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<PrintOutStudent> FindPrintOutStudent(String name, String course){
        ArrayList<PrintOutStudent> Print = new ArrayList<PrintOutStudent>();
        try {
            pstmt.setString(1, name);
            pstmt2.setString(1, name);
            //pstmt2.setString(2,course);
            ResultSet rs = pstmt.executeQuery();
            ResultSet rs2 = pstmt2.executeQuery();

            while (rs != null && rs.next()){
            PrintOutStudent print = new PrintOutStudent(rs.getString(1), rs.getString(2),
            rs.getInt(3), rs2.getFloat(1));
            //,rs2.getString(2), rs2.getFloat(1)
            Print.add(print);
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return Print;
    }}

class PrintOutStudent{
    String StudentName;
    String CoursesTaken;
    Integer TotalGrades;
    float AvgGrades;
    //String CourseName;
    //float AvgCourse;


    public PrintOutStudent(String name, String courses, Integer grades, float avgGr){

        this.StudentName = name;
        this.CoursesTaken = courses;
        this.TotalGrades = grades;
        this.AvgGrades = avgGr;
        //this.CourseName = courseN;
        //this.AvgCourse = avgC;


    }

}
