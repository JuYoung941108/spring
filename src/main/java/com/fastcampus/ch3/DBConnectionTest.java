package com.fastcampus.ch3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;

public class DBConnectionTest {
    public static void main(String[] args) throws Exception {
    // JDBC
//    String DB_URL="jdbc:mysql://localhost:3306/springbasic?useUnicode=true&charaterEncoding=utf8";
//    String DB_USER="jy";
//    String DB_PASSWORD="1234";
//
//    Connection c= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD); // DB connection
//    Statement stmt=c.createStatement(); // Statement create
//
//    String query="select now()"; // 시스템의 현재 날짜, 시간을 출력하는 query
//    ResultSet rs=stmt.executeQuery(query); // query를 실행한 결과를 rs에 담는다.
//
//    while (rs.next()) { // 실행 결과가 담긴 rs를 한 줄씩 읽어서 출력한다.
//        String curDate=rs.getString(1); // 읽어온 행의 첫번째 컬럼 값을 String으로 읽어서 curDate에 저장한다.
//        System.out.println(curDate);
//    }

    // Spring JDBC
    ApplicationContext ac=new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
    DataSource ds=ac.getBean(DataSource.class);

    Connection c=ds.getConnection(); // DB Connection

    System.out.println("c = " + c);
//    assertTrue(c!=null);
    }
}
