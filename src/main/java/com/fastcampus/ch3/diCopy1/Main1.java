package com.fastcampus.ch3.diCopy1;

import java.io.FileReader;
import java.util.Properties;

class Car{}
class SportsCar extends Car{}
class Truck extends Car {}
class Engine {}

public class Main1 {
    public static void main(String[] args) throws Exception{
        Car car=(Car)getObject("car");
        Engine engine=(Engine)getObject("engine");
        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
    }

    static Object getObject(String key) throws Exception{
        Properties p=new Properties();
        p.load(new FileReader("config.txt"));

        Class clazz=Class.forName(p.getProperty(key)); // key를 이용해 클래스에 대한 정보를 얻어 옴

        return clazz.newInstance();
    }

        static Car getCar() throws Exception{
        Properties p=new Properties();
        p.load(new FileReader("config.txt"));

        Class clazz=Class.forName(p.getProperty("car")); // key가 'car'인 Entry를 얻어옴

        return (Car)clazz.newInstance(); // newInstance가 반환하는 게 Object라 형변환 필요
    }
}
