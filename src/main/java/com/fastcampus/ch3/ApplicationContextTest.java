//package com.fastcampus.ch3;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.annotation.Scope;
//import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.Map;
//
//@Component
//@Scope("prototype")
//class Door {}
//@Component class Engine {}
//@Component class TurboEngine extends Engine {}
//@Component class SuperEngine extends Engine {}
//
//@Component
//class Car {
//    String color;
//    int oil;
////    @Value("red") String color;
////    @Value("100") int oil;
////    @Autowired
//    Engine engine;
////    @Autowired
//    Door[] doors;
//
//      // 생성자가 2개일 때는 주입할 생성자에 @Autowired 붙이기
////    public Car(){} engine=null, doors=null
//    @Autowired // 원래 붙여야하지만 생략 가능 | 생성자를 이용해 bean 주입 가능
//    public Car(@Value("black") String color, @Value("50") int oil, Engine engine, Door[] doors) {
//        this.color = color;
//        this.oil = oil;
//        this.engine = engine;
//        this.doors = doors;
//    }
//
//    @Override
//    public String toString() {
//        return "Car{" +
//                "color='" + color + '\'' +
//                ", oil=" + oil +
//                ", engine=" + engine +
//                ", doors=" + Arrays.toString(doors) +
//                '}';
//    }
//}
//
//@Component // `<component-scan>`로 @Component가 클래스르 자동 검색해서 빈으로 등록
//@PropertySource("setting.properties")
//class sysInfo {
//    @Value("#{systemProperties['user.timezone']}") String timeZone;
//    @Value("#{systemEnvironment['APPDATA']}") String currDir;
//    @Value("${autosaveDir}") String autosaveDir;
//    @Value("${autosaveInterval}") int autosaveInterval;
//    @Value("${autosave}") boolean autosave;
//
//    @Override
//    public String toString() {
//        return "sysInfo{" +
//                "timeZone='" + timeZone + '\'' +
//                ", currDir='" + currDir + '\'' +
//                ", autosaveDir='" + autosaveDir + '\'' +
//                ", autosaveInterval=" + autosaveInterval +
//                ", autosave=" + autosave +
//                '}';
//    }
//}
//
//public class ApplicationContextTest {
//    public static void main(String[] args) {
//        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
////      Car car = ac.getBean("car", Car.class); // 타입 지정 시 형변환 생략 가능 | 아래 문장과 동일함
//        Car car  = (Car) ac.getBean("car"); // 이름으로 빈 검색
//        System.out.println("car = " + car);
//
//        System.out.println("ac.getBean(sysInfo.class) = " + ac.getBean(sysInfo.class));
//        Map<String, String> map=System.getenv();
//        System.out.println("map = "+map);
//    }
//}