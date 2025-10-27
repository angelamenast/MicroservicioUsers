package com.unicauca.usersmanagement.validation;
import com.unicauca.usersmanagement.entity.Person;
import com.unicauca.usersmanagement.service.PersonService;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class LoginValidation {

    @Autowired
    PersonService personService;

    @Transactional
    public boolean validateLogin(String email, String password) {

        Person person = personService.findByUser_Email(email);
        if (person == null) {
            return false;
        }
        String userPass = person.getUser().getPassword();

        return BCrypt.checkpw(password, userPass);
    }

    public String encryptPassword(String password) {
         return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
