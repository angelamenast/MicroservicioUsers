package com.unicauca.usersmanagement.controller;

import com.unicauca.usersmanagement.entity.*;
import com.unicauca.usersmanagement.infra.dto.*;
import com.unicauca.usersmanagement.service.*;
import com.unicauca.usersmanagement.validation.LoginValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usersmanagement")
public class UsersManagementController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CoordinatorService coordinatorService;

    @Autowired
    private HeadOfDepartmentService headOfDepartmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;

    @Autowired
    private LoginValidation  loginValidation;

    //Save to Users

    @PostMapping("/professor")
    public ResponseEntity<?> saveProfessor(@RequestBody ProfessorRequest professor) {
        try {
            Professor professorSaved = professorService.saveProfessor(professor);
            return ResponseEntity.status(HttpStatus.CREATED).body(professorSaved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error saving professor: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/coordinator")
    public ResponseEntity<?> saveCoordinator(@RequestBody CoordinatorRequest coordinator) {
        try {
            Coordinator coordinatorSaved = coordinatorService.saveCoordinator(coordinator);
            return ResponseEntity.status(HttpStatus.CREATED).body(coordinatorSaved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error saving coordinator: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/head")
    public ResponseEntity<?> saveHead(@RequestBody HeadOfDepartmentRequest head) {
        try {
            HeadOfDepartment headSaved = headOfDepartmentService.saveHead(head);
            return ResponseEntity.status(HttpStatus.CREATED).body(headSaved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error savin head of department: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/student")
    public ResponseEntity<?> saveStudent(@RequestBody StudentRequest student) {
        try {
            Student studentSaved = studentService.saveStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(studentSaved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error saving student: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.findAllUsers();
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Theren't users registered");
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error getting users.\"}");
        }

    }

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        try {
            List<Student> students = studentService.findAllStudents();
            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Theren't students registered");
            }
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error getting students.\"}");
        }

    }

    @GetMapping("/professors")
    public ResponseEntity<?> getAllProfessors() {
        try {
            List<Professor> professors = professorService.findAllProfessors();
            if (professors.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Theren't professors registered");
            }
            return ResponseEntity.ok(professors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error getting professors.\"}");
        }
    }

    @GetMapping("/heads")
    public ResponseEntity<?> getAllHeads() {
        try {
            List<HeadOfDepartment> heads = headOfDepartmentService.findAllHeads();
            if (heads.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Theren't heads of department registered");
            }
            return ResponseEntity.ok(heads);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error getting heads of department.\"}");
        }
    }

    @GetMapping("/coordinators")
    public ResponseEntity<?> getAllCoordinators() {
        try {
            List<Coordinator> coordinators = coordinatorService.findAllCoordinators();
            if (coordinators.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Theren't coordinators registered");
            }
            return ResponseEntity.ok(coordinators);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error getting coordinators.\"}");
        }
    }

    // Get Person

    @GetMapping("/personByEmail/{email}")
    public ResponseEntity<?> getPersonByEmail(@PathVariable("email") String email) {
        try {
            Person person = personService.findByUser_Email(email);
            if (person == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("There isn't a person registered with that email");
            }
            return ResponseEntity.ok(person);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/StudentByEmail/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
        Student student = studentService.getStudentByEmail(email);
        if (student == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(student);
    }





    // Validation
    @PostMapping("/login")
    public ResponseEntity<?> validateLogin(@RequestBody User user) {
        try {
            Boolean validation = loginValidation.validateLogin(user.getEmail(), user.getPassword());
            if (!validation) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid email or password");
            }
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}