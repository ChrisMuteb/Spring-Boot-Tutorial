package com.lasuperbe.example.student;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class StudentMapperTest {

    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    public void shouldMapStudentDtoToStudent(){
        StudentDto dto = new StudentDto(
                "John",
                "Doe",
                "john@mail.com",
                1);

        Student student = mapper.toStudent(dto);

        Assertions.assertEquals(dto.firstname(), student.getFirstname());
        assertEquals(dto.lastname(), student.getLastname());
        assertEquals(dto.email(), student.getEmail());
        assertNotNull(student.getSchool());
        assertEquals(dto.schoolId(), student.getSchool().getId());
    }

    @Test
    public void should_throw_studentDto_to_student_when_studentDto_is_null(){
        var msg = assertThrows(NullPointerException.class, () -> mapper.toStudent(null)) ;
        assertEquals("The student Dto is null", msg.getMessage());
    }

    @Test
    public void shouldMapStudentToStudentDto(){
        // given
        Student student = new Student(
                "John",
                "Doe",
                "john@mail.com",
                1);

        // when
        StudentResponseDto dto = mapper.toStudentResponseDto(student);

        // then
        assertEquals(dto.firstname(), student.getFirstname());
        assertEquals(dto.lastname(), student.getLastname());
        assertEquals(dto.email(), student.getEmail());
    }
}