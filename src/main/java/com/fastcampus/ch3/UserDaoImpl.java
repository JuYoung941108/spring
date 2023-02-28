package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;


//@Component: @Controller, @Repository, @Service, @ControllerAdvice
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired DataSource ds;
    final int FAIL=0;

    @Override
    public int deleteUser(String id) {
        int rowCnt=FAIL; // insert, delete, update;

        Connection c=null;
        PreparedStatement pstmt=null;

        String sql="delete from user_info where id=?";

        try {
        c=ds.getConnection();
        pstmt=c.prepareStatement(sql);
        pstmt.setString(1,id);
        return pstmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }finally {
            close(pstmt, c);
        }
    }

    @Override
    public User selectUser(String id) {
        User user=null;

        // Connection -> PreparedStatement -> ResultSet
        Connection c=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        String sql="select * from user_info where id = ?";

        try {

            c=ds.getConnection();
            pstmt=c.prepareStatement(sql);
            pstmt.setString(1, id);

            rs=pstmt.executeQuery();

            if(rs.next()) {
                user=new User();
                user.setId(rs.getString(1));
                user.setPwd(rs.getString(2));
                user.setName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setBirth(new Date(rs.getDate(5).getTime()));
                user.setSns(rs.getString(6));
                user.setReg_date(new Date(rs.getTimestamp(7).getTime()));
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            close(rs, pstmt, c); // close()의 호출 순서는 생성된 순서의 역순이여야 함
        }
        return user;
    }

    @Override
    public int insertUser(User user) {
        int rowCnt=FAIL;

        Connection c=null;
        PreparedStatement pstmt=null;

        String sql="insert into user_info values(?,?,?,?,?,?,now())";

        try {
            c=ds.getConnection();
            pstmt=c.prepareStatement(sql);
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPwd());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
            pstmt.setString(6, user.getSns());

            return pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            return FAIL;
        }finally {
            close(pstmt,c);
        }
    }

    @Override
    public int updateUser(User user) {
        int rowCnt=FAIL;

        String sql="update user_info set pwd=?, name=?, email=?, birth=?, sns=?, reg_date=? where id=?";

        try
                // try-with-resources since jdk7
                (
                        Connection c=ds.getConnection();
                        PreparedStatement pstmt=c.prepareStatement(sql);
                )
        {
                pstmt.setString(1, user.getPwd());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setDate(4, new java.sql.Date(user.getBirth().getTime())); // java.sql.Date
                pstmt.setString(5, user.getSns());
                pstmt.setTimestamp(6, new java.sql.Timestamp(user.getReg_date().getTime()));
                pstmt.setString(7, user.getId());

                rowCnt=pstmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
        return rowCnt;
    }

    @Override
    public void deleteAll() throws Exception {
        Connection c=ds.getConnection();

        String sql="delete from user_info";

        PreparedStatement pstmt=c.prepareStatement(sql);
        pstmt.executeUpdate();
    }

    private void close(AutoCloseable... acs) { // 가변인자
        for(AutoCloseable ac:acs)
        try{
            if(ac!=null)
                ac.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
