package com.springboot.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class SpringbootBlogRestApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testTrialStream() {
//        List<String> list = Arrays.asList("string1", "String 2", "string 3");
        List<String> list = new ArrayList<>();
        List<String> response = list.stream().filter(e -> e.startsWith("st")).collect(Collectors.toList());
        System.out.println("Response="+response);
    }

}
