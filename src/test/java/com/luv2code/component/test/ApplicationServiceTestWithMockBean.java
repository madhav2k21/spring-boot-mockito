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
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {MvcTestingExampleApplication.class})
class ApplicationServiceTestWithMockBean {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CollegeStudent collegeStudent;
    @Autowired
    private StudentGrades studentGrades;
    //use springboot @MockBean when you need to inject mocks And inject regular beans from app cntext
    @MockBean
    private ApplicationDao applicationDaoMock;

    @Autowired //inject mock dependencies: note it will inject dependencies annotated with @MockBean
    private ApplicationService applicationService;

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

        double result = applicationService.addGradeResultsForSingleClass(collegeStudent.getStudentGrades()
                .getMathGradeResults());
        assertEquals(100.0, result);

        verify(applicationDaoMock, times(1))
                .addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

}