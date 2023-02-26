package com.fastcampus.ch3;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

@Component("engine") class Engine{} // <bean id="engine" class="com.fastcampus.ch3.Engine"/>
@Component class SuperEngine extends Engine{}
@Component class TurboEngine extends Engine{}
@Component class Door{}
@Component
class Car{
    @Value("red") String color;
    @Value("100") int oil;

    @Resource(name="superEngine") // byName
//    @Autowired @Qualifier("superEngine") // byType -> byName
    Engine engine; // byType - 타입으로 먼저 검색하고 여러개면 이름으로 찾음 - engine, superEngine, turboEngine

    @Autowired Door[] doors;

    public Car() {}
    public Car(String color, int oil, Engine engine, Door[] doors) { // constructor-arg
        this.color = color;
        this.oil = oil;
        this.engine = engine;
        this.doors = doors;
    }

    public void setColor(String color) { // property
        this.color = color;
    }

    public void setOil(int oil) {
        this.oil = oil;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setDoors(Door[] doors) {
        this.doors = doors;
    }

    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                ", oil=" + oil +
                ", engine=" + engine +
                ", doors=" + Arrays.toString(doors) +
                '}';
    }
}

public class SpringDITest {
    public static void main(String[] args) {
    ApplicationContext ac=new GenericXmlApplicationContext("config.xml");
//    Car car=(Car)ac.getBean("car"); // byName 아래와 같은 문장
    Car car=(Car)ac.getBean("car", Car.class); // byName
//    Car car2=(Car)ac.getBean(Car.class); // byType
////    Engine engine=(Engine)ac.getBean("engine"); // byName
//    Engine engine=(Engine)ac.getBean("Engine"); // byName
////    Engine engine=(Engine)ac.getBean(Engine.class); // byType - 같은 타입이 3개라서 에러 발생
//    Door door=(Door)ac.getBean("door");

//    car.setColor("red");
//    car.setOil(100);
//    car.setEngine(engine);
//    car.setDoors(new Door[]{ac.getBean("door",Door.class),ac.getBean("door",Door.class)});

    System.out.println("car = " + car);
//    System.out.println("car = " + engine);
    }
}
