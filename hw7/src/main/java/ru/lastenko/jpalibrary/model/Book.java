package ru.lastenko.jpalibrary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
@FieldNameConstants
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Genre genre;

}