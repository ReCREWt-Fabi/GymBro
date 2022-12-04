package de.othr.im.gymbro.model;

import javax.persistence.*;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "iduser", referencedColumnName = "id" )
    private User user;
}