package ru.kdavydenko.springcourse.springrestapp.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kdavydenko.springcourse.springrestapp.models.PersonRest;
import ru.kdavydenko.springcourse.springrestapp.repositories.PersonRestRepository;
import ru.kdavydenko.springcourse.springrestapp.util.PersonNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeopleService {

    private final PersonRestRepository personRestRepository;

    @Autowired
    public PeopleService(PersonRestRepository personRestRepository) {
        this.personRestRepository = personRestRepository;
    }
    public List<PersonRest> findAll(){return personRestRepository.findAll();}

    public PersonRest findOne(int id){
        Optional<PersonRest> foundPerson = personRestRepository.findById(id);
        return foundPerson.orElseThrow(PersonNotFoundException::new);
    }

    public void save(PersonRest personRest){
        enrichPerson(personRest);
        personRestRepository.save(personRest);
    }

    private void enrichPerson(PersonRest personRest) {
        personRest.setCreatedAt(LocalDateTime.now());
        personRest.setUpdatedAt(LocalDateTime.now());
        personRest.setCreatedWho("USER");
    }
}
