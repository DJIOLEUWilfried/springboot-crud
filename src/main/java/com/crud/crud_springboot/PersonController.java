package com.crud.crud_springboot;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/persons")
public class PersonController {

    final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersonController() {
        return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Person> createPersonController(@RequestBody Person person) {

        return new ResponseEntity<>(personRepository.save(person), HttpStatus.CREATED) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonByIdController(@PathVariable Long id) {

        Optional<Person> per = personRepository.findById(id);

        return per.map(
                person -> new ResponseEntity<>(person, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePersonController(@PathVariable Long id, @RequestBody Person updatePerson) {

        Optional<Person> per = personRepository.findById(id);

        if (per.isPresent())
        {
            Person perIsPresent = per.get();

            perIsPresent.setCity(updatePerson.getCity());
            perIsPresent.setName(updatePerson.getName());
            perIsPresent.setPhoneNumber(updatePerson.getPhoneNumber());

            return new ResponseEntity<>(personRepository.save(perIsPresent), HttpStatus.OK);
        }

        return new ResponseEntity<>( HttpStatus.NOT_FOUND );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Person> deletePersonController(@PathVariable Long id) {

        Optional<Person> per = personRepository.findById(id);

        if (per.isPresent()){

            personRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.OK) ;
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }

}
