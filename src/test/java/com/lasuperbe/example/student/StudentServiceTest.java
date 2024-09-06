package com.lasuperbe.example.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class StudentServiceTest {

    // which service we want to test
    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentRepository repository;
    @Mock
    private StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_save_a_student(){
        // Given
        StudentDto dto = new StudentDto(
                "John", "Doe", "john@mail.com", 1
        );

        Student student = new Student(
                "John", "Doe", "john@mail.com", 20
        );
        Student savedStudent = new Student(
                "John", "Doe", "john@mail.com", 20
        );
        savedStudent.setId(1);

        // Mock the calls
        Mockito.when(studentMapper.toStudent(dto)).thenReturn(student);
        Mockito.when(repository.save(student)).thenReturn(savedStudent);
        Mockito.when(studentMapper.toStudentResponseDto(savedStudent)).thenReturn(new StudentResponseDto("John", "Doe", "john@mail.com"));

        // When
        StudentResponseDto responseDto = studentService.saveStudent(dto);

        // Then
        assertEquals(dto.firstname(), responseDto.firstname());
        assertEquals(dto.lastname(), responseDto.lastname());
        assertEquals(dto.email(), responseDto.email());

        Mockito.verify(studentMapper, times(1)).toStudent(dto);
        Mockito.verify(repository, times(1)).save(student);
        Mockito.verify(studentMapper, times(1)).toStudentResponseDto(savedStudent);
    }

    @Test
    public void should_findAll_student(){
        // Given
        List<Student> students = new ArrayList<>();
        students.add(new Student("John", "Doe", "john@mail.com", 20));

        // Mock the calls
        Mockito.when(repository.findAll()).thenReturn(students);
        Mockito.when(studentMapper.toStudentResponseDto(Mockito.any(Student.class)))
                .thenReturn(new StudentResponseDto("John", "Doe", "john@mail.com"));


        // When
        List<StudentResponseDto> responseDto = studentService.findAllStudent();

        // Then
        assertEquals(students.size(), responseDto.size());
        Mockito.verify(repository, times(1)).findAll();
    }

    @Test
    public void should_findById_student(){
        // Given
        Student student = new Student("John", "Doe", "john@mail.com", 20);
        Integer id = 1;

        // Mock the calls
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(student));
        Mockito.when(studentMapper.toStudentResponseDto(Mockito.any(Student.class)))
                .thenReturn(new StudentResponseDto("John", "Doe", "john@mail.com"));


        // When
        StudentResponseDto responseDto = studentService.findStudentById(id);

        // Then
        assertEquals(student.getEmail(), responseDto.email());
        Mockito.verify(repository, times(1)).findById(id);
    }

    @Test
    public void should_findByName_student(){
        // Given
        List<Student> students = new ArrayList<>();
        students.add(new Student("John", "Doe", "john@mail.com", 20));
        String name = "John";

        // Mock the calls
        Mockito.when(repository.findAllByFirstnameContaining(name)).thenReturn(students);
        Mockito.when(studentMapper.toStudentResponseDto(Mockito.any(Student.class)))
                .thenReturn(new StudentResponseDto("John", "Doe", "john@mail.com"));


        // When
        List<StudentResponseDto> responseDto = studentService.findStudentByName(name);

        // Then
        assertEquals(students.size(), responseDto.size());
        Mockito.verify(repository, times(1)).findAllByFirstnameContaining(name);
    }
}