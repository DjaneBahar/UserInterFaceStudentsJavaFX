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
        String sql = "SELECT courseID FROM Course;";
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

    public ArrayList<String> CourseSemesterQuerystmt(){
        ArrayList<String> CourseSemester=new ArrayList<String>();
        String sql = "SELECT semester FROM Course;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            while (rs!=null && rs.next()){
                String semester = rs.getString(1);
                CourseSemester.add(semester);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return CourseSemester;
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



    public  void preparedStmtToFromQuery(){
        String sql="SELECT D1.StudentID, D1.name FROM Student as D1 SELECT "+
                "D2.courseID, D2.semester FROM Course as D2 SELECT D3.grade FROM Grades as D3"
                + "WHERE D1.name=? AND D2.courseID=? AND D3.grade = ?;"

                ;


        try {
            pstmt = conn.prepareStatement(sql);
        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<PrintOut> FindPrintOut(String from, String to, double time){
        ArrayList<PrintOut> Trips=new ArrayList<PrintOut>();
        try {
            pstmt.setString(1, from);
            pstmt.setString(2, to);
            pstmt.setFloat(3, (float) time);
            ResultSet rs=pstmt.executeQuery();
            while (rs!=null && rs.next()){
                PrintOut trip=new PrintOut(rs.getInt(1),
                        rs.getString(2), rs.getFloat(3),
                        rs.getString(4),rs.getDouble(5));
                Trips.add(trip);
            }
        }catch(SQLException e)
        {
            e.printStackTrace();

        }
        return Trips;
    }

}


class PrintOut{
    Integer TrainID;
    String From;
    double departure;
    String To;
    double arrival;
    public PrintOut(Integer TID,String from, double dep, String to, double arr ){
        this.arrival=arr;
        this.departure=dep;
        this.From=from;
        this.To = to;
        this.TrainID=TID;
    }

}
