package com.luv2code.component.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {MvcTestingExampleApplication.class})
public class ReflectionTestUtilTest {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private CollegeStudent collegeStudent;
    @Autowired
    private StudentGrades studentGrades;
    @BeforeEach
    void setup(){
        collegeStudent.setFirstname("madhav");
        collegeStudent.setLastname("anupoju");
        collegeStudent.setEmailAddress("madhav@techM.com");
        collegeStudent.setStudentGrades(studentGrades);
        ReflectionTestUtils.setField(collegeStudent,"id",101);
        ReflectionTestUtils.setField(collegeStudent, "studentGrades", new StudentGrades(Arrays.asList(12.6,35.5,40.7, 50.5)));
    }

    @Test
    void getPrivateField(){
        assertEquals(101,ReflectionTestUtils.getField(collegeStudent, "id"));
        assertEquals(101, collegeStudent.getId());
        assertEquals(collegeStudent.getStudentGrades(),ReflectionTestUtils.getField(collegeStudent, "studentGrades"));


    }

    @Test
    void invokePrivateMethod(){
        String getFirstNameAndId = ReflectionTestUtils.invokeMethod(collegeStudent, "getFirstNameAndId");
        assertEquals("madhav 101", getFirstNameAndId);
    }

}
