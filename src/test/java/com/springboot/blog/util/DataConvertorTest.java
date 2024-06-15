package com.springboot.blog.util;


import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostDtoV2;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DataConvertorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataConvertorTest.class);

    @BeforeAll
    static void setUp() {
        LOGGER.debug("Starting DataConvertorTest");
    }


    @Test
    @DisplayName("readValue test")
    void readValue() {
        String testJson = "{\n"
                + "    \"id\": \"1\",\n"
                + "    \"title\": \"postValue\",\n"
                + "    \"description\": \"descriptionValue\",\n"
                + "    \"content\": \"contentValue\",\n"
                + "    \"ghy\": \"test\"\n"
                + "}";

        PostDtoV2 postDtoV2 = DataConvertor.readValue(testJson, PostDtoV2.class);
        Assertions.assertNotNull(postDtoV2);
        Assertions.assertEquals("postValue", postDtoV2.getTitle());
        Assertions.assertEquals("descriptionValue", postDtoV2.getDescription());
        Assertions.assertEquals("contentValue", postDtoV2.getContent());

    }

    @Test
    @DisplayName("deserializeJsonString test")
    void deserializeJsonString() {
        String testJson = "{\n"
                + "    \"id\": \"1\",\n"
                + "    \"title\": \"postValue\",\n"
                + "    \"description\": \"descriptionValue\",\n"
                + "    \"content\": \"contentValue\",\n"
                + "    \"ghy\": \"test\"\n"
                + "}";


        Throwable t = Assertions.assertThrows(RuntimeException.class, () -> DataConvertor.deserializeJsonString(testJson, PostDto.class));
//        t.printStackTrace();
    }


}