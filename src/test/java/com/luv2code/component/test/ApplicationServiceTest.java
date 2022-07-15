package com.luv2code.component.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {MvcTestingExampleApplication.class})
class ApplicationServiceTest {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CollegeStudent collegeStudent;
    @Autowired
    private StudentGrades studentGrades;
    @Mock
    private ApplicationDao applicationDaoMock;

    @InjectMocks //inject mock dependencies: note it will inject dependencies annotated with @Mock or @Spy
    private ApplicationService applicationServiceUnderTest;

    @BeforeEach
    public void setup() {
        collegeStudent.setFirstname("madhav");
        collegeStudent.setLastname("anupoju");
        collegeStudent.setEmailAddress("madhav@techM.com");
        collegeStudent.setStudentGrades(studentGrades);
    }

    @Test
    void assertEqualsTestAddGrades(){

        when(applicationDaoMock.addGradeResultsForSingleClass(studentGrades.getMathGradeResults()))
                .thenReturn(100.0);

        double result = applicationServiceUnderTest.addGradeResultsForSingleClass(collegeStudent.getStudentGrades()
                .getMathGradeResults());
        assertEquals(100.0, result);

        verify(applicationDaoMock, times(1))
                .addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @Test
    void throwRuntimeException(){
        CollegeStudent student = applicationContext.getBean(CollegeStudent.class);
        doThrow(new RuntimeException("runtime")).when(applicationDaoMock).checkNull(student);
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            applicationServiceUnderTest.checkNull(student);
        });
        assertEquals(runtimeException.getMessage(),"runtime");
        verify(applicationDaoMock,times(1)).checkNull(student);
    }

    @Test
    void testFindGPA(){
        when(applicationDaoMock.findGradePointAverage(studentGrades.getMathGradeResults())).thenReturn(8.2);

        double gradePointAverage = applicationServiceUnderTest.findGradePointAverage(collegeStudent.getStudentGrades().getMathGradeResults());
        assertEquals(8.2,gradePointAverage);
    }

    @Test
    void testAssertNotNull(){
        when(applicationDaoMock.checkNull(studentGrades.getMathGradeResults())).thenReturn(true);
        assertNotNull(applicationServiceUnderTest.checkNull(collegeStudent.getStudentGrades().getMathGradeResults()));
    }

}