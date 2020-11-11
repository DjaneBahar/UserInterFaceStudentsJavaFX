package sample;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class StudentModel {
    Connection conn= null;
    Statement stmt =null;
    PreparedStatement pstmt=null;
    String url;
    public StudentModel(String url){
        this.url=url;
    }

    public void connect() throws SQLException {
        conn=getConnection(this.url);
    }
    public void CreateStatement() throws SQLException{
        this.stmt=conn.createStatement();
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

    public ArrayList<Integer> GradesQuerystmt(){
        ArrayList<Integer> Grades=new ArrayList<Integer>();
        String sql = "SELECT grade FROM Grades;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            while (rs!=null && rs.next()){
                int grade=rs.getInt(1);
                Grades.add(grade);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return Grades;
    }

    public ArrayList<Integer> AverageGradesQuerystmt(){
        ArrayList<Integer> AvgGrade = new ArrayList<Integer>();
        String sql = "SELECT SID, avg(grade) FROM Grades GROUP BY SID ORDER BY SID;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            while (rs!=null && rs.next()){
                int average = rs.getInt(1);
                AvgGrade.add(average);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return AvgGrade;
    }

    public ArrayList<Integer> AverageCourseQuerystmt(){
        ArrayList<Integer> AvgCourse = new ArrayList<Integer>();
        String sql = "SELECT CID, avg(grade) FROM Grades GROUP BY CID ORDER BY CID;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            while (rs!=null && rs.next()){
                int averageC = rs.getInt(1);
                AvgCourse.add(averageC);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return AvgCourse;
    }


    public  void preparedStmtToFromQuery(){

        String sql="SELECT D1.StudentID, D1.name FROM Student as D1 SELECT "+
                "D2.coursename FROM Course as D2 SELECT D3.grade FROM Grades as D3 "+
                "SELECT D4.SID, avg(grade) FROM Grades GROUP BY SID ORDER BY SID as D4 "
                + "WHERE D1.name=? AND D2.courseID=? AND D3.grade = ? AND D4 = ?;";


        try {
            pstmt = conn.prepareStatement(sql);
        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<PrintOutStudent> FindPrintOutStudent(String name, String courses,
                           Integer grades, double avgGr, double avgC){
        ArrayList<PrintOutStudent> Print = new ArrayList<PrintOutStudent>();
        try {
            pstmt.setString(1, name);
            pstmt.setString(2, courses);
            pstmt.setInt(3,  grades);
            pstmt.setFloat(4, (float) avgGr);
            pstmt.setFloat(5,(float) avgC);
            ResultSet rs = pstmt.executeQuery();

            while (rs != null && rs.next()){
                PrintOutStudent print = new PrintOutStudent(rs.getString(1),
                        rs.getString(2), rs.getInt(3),
                        (int) rs.getFloat(4), (int) rs.getFloat(5));
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
    double AverageGrade;
    double AverageGradeCourse;

    public PrintOutStudent(String name, String courses, Integer grades,
                           double avgGr, double avgC){

        this.StudentName = name;
        this.CoursesTaken = courses;
        this.TotalGrades = grades;
        this.AverageGrade = avgGr;
        this.AverageGradeCourse = avgC;
    }

}
