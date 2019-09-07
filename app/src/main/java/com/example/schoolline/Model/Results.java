package com.example.schoolline.Model;

public class Results {
    String col01;
    String col02;
    String col03;
    String col04;
    String col05;
    String col06;
    String col07;
    double total;
    double avg;
    double sum;
    String studentName;
    String subject;
    String grade;
    String classroom;

    public Results() {

    }

    public Results(String col01, String col02, String col03, String col04, String col05, String col06, String col07, int total, double avg, double sum, String studentName, String subject, String grade, String classroom) {
        this.col01 = col01;
        this.col02 = col02;
        this.col03 = col03;
        this.col04 = col04;
        this.col05 = col05;
        this.col06 = col06;
        this.col07 = col07;
        this.total = total;
        this.avg = avg;
        this.sum = sum;
        this.studentName = studentName;
        this.subject = subject;
        this.grade = grade;
        this.classroom = classroom;
    }

    public String getCol01() {
        return col01;
    }

    public void setCol01(String col01) {
        this.col01 = col01;
    }

    public String getCol02() {
        return col02;
    }

    public void setCol02(String col02) {
        this.col02 = col02;
    }

    public String getCol03() {
        return col03;
    }

    public void setCol03(String col03) {
        this.col03 = col03;
    }

    public String getCol04() {
        return col04;
    }

    public void setCol04(String col04) {
        this.col04 = col04;
    }

    public String getCol05() {
        return col05;
    }

    public void setCol05(String col05) {
        this.col05 = col05;
    }

    public String getCol06() {
        return col06;
    }

    public void setCol06(String col06) {
        this.col06 = col06;
    }

    public String getCol07() {
        return col07;
    }

    public void setCol07(String col07) {
        this.col07 = col07;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
