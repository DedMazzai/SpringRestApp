package ru.kdavydenko.springcourse.springrestapp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "person_rest")
public class PersonRest {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Имя должно быть не пустым")
    private String name;

    @Column(name = "age")
    @Min(value = 18, message = "Возраст должен быть больше 18 лет")
    private int age;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotEmpty
    @Column(name = "created_who")
    private String createdWho;


    public PersonRest(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
