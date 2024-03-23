package com.henry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args) {
        List<String> nameList = new ArrayList<>();
        nameList.add("test111");
        nameList.add("test111111");
        nameList.add("test33333333333");

        //nameList.stream().forEach(System.out::println);

        List<String> longNameList = nameList.stream()
                .filter(name -> name.length() > 8)
                .collect(Collectors.toList());

        //longNameList.stream().forEach(name -> System.out.println("길어." + name));
        //System.out.println(longNameList.stream().count());

        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", "aaa1");
        userMap.put("id", "aaa12");
        userMap.put("addr", "aaa13");
        List<String> userList = userMap.values().stream()
                                .filter(user -> user.contains("2"))
                                .collect(Collectors.toList());
        //userList.stream().forEach(System.out::println);

        userMap.keySet().stream().forEach(System.out::println);

    }

}
