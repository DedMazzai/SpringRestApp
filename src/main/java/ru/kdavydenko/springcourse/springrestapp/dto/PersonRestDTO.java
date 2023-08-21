package ru.kdavydenko.springcourse.springrestapp.dto;

import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Getter
@Setter

public class PersonRestDTO {

    private int id;

    @NotEmpty(message = "Имя должно быть не пустым")
    private String name;

    @Min(value = 18, message = "Возраст должен быть больше 18 лет")
    private int age;

    private LocalDateTime createdAt;
}
