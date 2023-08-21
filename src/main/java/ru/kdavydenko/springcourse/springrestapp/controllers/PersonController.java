package ru.kdavydenko.springcourse.springrestapp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kdavydenko.springcourse.springrestapp.dto.PersonRestDTO;
import ru.kdavydenko.springcourse.springrestapp.models.PersonRest;
import ru.kdavydenko.springcourse.springrestapp.servises.PeopleService;
import ru.kdavydenko.springcourse.springrestapp.util.PersonErrorResponse;
import ru.kdavydenko.springcourse.springrestapp.util.PersonNotCreatedException;
import ru.kdavydenko.springcourse.springrestapp.util.PersonNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonRestDTO> getPeople() {
        return peopleService.findAll().stream().map(this::convertToPersonDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonRestDTO getPerson(@PathVariable("id") int id) {
        return convertToPersonDTO(peopleService.findOne(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createPerson(@RequestBody @Valid PersonRestDTO personRestDTO,
                                                   BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError fieldError: errors){
                errorMsg.append(fieldError.getField())
                        .append(" - ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }
        peopleService.save(convertToPerson(personRestDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }



    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e){
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                "Person with this id wasn`t found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e){
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.BAD_REQUEST);
    }
    private PersonRest convertToPerson(PersonRestDTO personRestDTO) {
        return modelMapper.map(personRestDTO, PersonRest.class);
    }
    private PersonRestDTO convertToPersonDTO(PersonRest personRest) {
        return modelMapper.map(personRest, PersonRestDTO.class);
    }
}
