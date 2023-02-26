package com.fastcampus.ch3.diCopy2;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class Car{}
class SportsCar extends Car{}
class Truck extends Car {}
class Engine {}

class AppContext {
    Map map; // 객체 저장소

    AppContext() {
        map=new HashMap();

        try {
            Properties p=new Properties();
            p.load(new FileReader("config.txt"));

            // Properties에 저장된 내용을 Map에 저장
            map=new HashMap(p);
            
            // 반복문으로 클래스 이름 얻기 -> 객체 생성 후 다시 map에 저장
            for(Object key:map.keySet()){
            Class clazz=Class.forName((String)map.get(key)); // key가 'car'인 Entry를 얻어옴
            map.put(key,clazz.newInstance());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Object getBean(String key) {
        return map.get(key); // key를 주면 key에 해당하는 객체를 반환
    }
}

public class Main2 {
    public static void main(String[] args) throws Exception{
        AppContext ac=new AppContext();
        Car car=(Car)ac.getBean("car");
        Engine engine=(Engine)ac.getBean("engine");
        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
    }
}
