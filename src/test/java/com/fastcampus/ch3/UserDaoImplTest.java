package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit4ClassRunner -> ApplicationContext 자동 실행
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"}) // xml 파일 위치 설정
public class UserDaoImplTest {
    @Autowired
    UserDao userDao;

    @Test
    public void deleteUser() {
    }

    @Test
    public void selectUser() {
    }

    @Test
    public void insertUser() {
    }

    @Test
    public void updateUser() throws Exception{
        Calendar cal=Calendar.getInstance();
        cal.clear();
        cal.set(2023,2,28);
        userDao.deleteAll();
        User user=new User("admin","1234","이주영","admin@app.com",new Date(cal.getTimeInMillis()), "instagram", new Date());
        int rowCnt=userDao.insertUser(user);
        assertTrue(rowCnt==1);

        user.setPwd("4321");
        rowCnt=userDao.updateUser(user);
        assertTrue(rowCnt==1);

        User user2=userDao.selectUser(user.getId());
        System.out.println("user = " + user);
        System.out.println("user2 = " + user2);
        assertTrue(user.equals(user2));
    }

//    @Test
//    public void deleteAll() {
//    }
}