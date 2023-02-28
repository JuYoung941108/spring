package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit4ClassRunner -> ApplicationContext 자동 실행
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"}) // xml 파일 위치 설정
public class DBConnectionTestTest {
    // 자동 주입
    @Autowired DataSource ds;
    
    // JUnit Test 메소드에 @Test 애너테이션 붙이기 -> Test 메소드는 무조건 'public void'여야 함
    @Test
    public void insertUserTest() throws Exception{
        User user=new User("admin","1234","이주영","admin@app.com",new Date(),"instagram",new Date());
        deleteAll();
        int rowCnt=insertUser(user);
        assertTrue(rowCnt==1); // 성공 시 1 반환, 실패 시 0 반환
    }

    @Test
    public void selectUserTest() throws Exception {
        deleteAll();
        User user=new User("admin","1234","이주영","admin@app.com",new Date(),"instagram",new Date());
        int rowCnt=insertUser(user);
        User user2=selectUser("admin");
        assertTrue(user.getId().equals("admin"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        deleteAll();
        int rowCnt=deleteUser("admin");
        assertTrue(rowCnt==0);
        User user=new User("admin","1234","이주영","admin@app.com",new Date(),"instagram",new Date());
        rowCnt=insertUser(user);
        assertTrue(rowCnt==1);
        rowCnt=deleteUser(user.getId());
        assertTrue(rowCnt==1);
        assertTrue(selectUser(user.getId())==null);
    }

    // 매개변수로 받은 사용자 정보로 user_info 테이블을 update하는 메소드
    public int updateUser(User user) throws Exception {
        return 0;
    }

    public int deleteUser(String id) throws Exception {
        Connection c=ds.getConnection();
        String sql="delete from user_info where id = ?";
        PreparedStatement pstmt=c.prepareStatement(sql);
        pstmt.setString(1, id);
//        return rowCnt;
        return pstmt.executeUpdate();
    }

    public User selectUser(String id) throws Exception { // ID에 해당하는 유저 정보를 갖고옴
        Connection c=ds.getConnection();
        String sql="select * from user_info where id = ?";
        PreparedStatement pstmt=c.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()) {
            User user=new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(rs.getDate(5));
            user.setSns(rs.getString(6));
            user.setReg_date(rs.getDate(7));

            return user;
        }
        return null;
    }

    private void deleteAll() throws Exception {
        Connection c=ds.getConnection();
        String sql="delete from user_info";
        PreparedStatement pstmt=c.prepareStatement(sql);
        pstmt.executeUpdate(); // insert, delete, update
    }

    // 사용자 정보를 user_info 테이블에 저장하는 메소드
    public int insertUser(User user) throws Exception {
        Connection c=ds.getConnection(); // 1. 커넥션 얻기
        String sql="insert into user_info values (?,?,?,?,?,?,?)"; // 2. 쿼리문 셋팅
//        String sql="insert into user_info values (?,?,?,?,?,?,now())";
        PreparedStatement pstmt=c.prepareStatement(sql); // 3. 쿼리문을 전송할 참조변수 설정(PreparedStatement)
        pstmt.setString(1, user.getId()); // 4. 물음표에 해당하는 값 지정
        pstmt.setString(2, user.getPwd());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6, user.getSns());
        pstmt.setDate(7, new java.sql.Date(user.getReg_date().getTime()));

        int rowCnt = pstmt.executeUpdate(); // 5. 쿼리 실행, 6. 결과 값을 rowCnt에 저장(0, 1)
        
        return rowCnt;
    }

    @Test
    public void SpringJDBCConnection() throws Exception {
        // 수동 주입
//        ApplicationContext ac=new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//        DataSource ds=ac.getBean(DataSource.class);

        Connection c=ds.getConnection(); // DB Connection

        System.out.println("c = " + c);
        assertTrue(c!=null);
    }
}