package ru.kdavydenko.springcourse.springrestapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kdavydenko.springcourse.springrestapp.models.PersonRest;

public interface PersonRestRepository extends JpaRepository<PersonRest, Integer> {
}
