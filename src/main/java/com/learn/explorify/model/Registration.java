package com.learn.explorify.model;

import jakarta.persistence.*;
import lombok.Data;

//@Entity
//@Table(name="registrations")
//public record Registration(
//        @Id @GeneratedValue(strategy = GenerationType.AUTO) String id,
//        @Column String eventId,
//        @Column String attendee
//) {
//}

@Entity
@Table(name = "registration_records")
@Data
public class Registration{
        @Id @Column(name="id") private int id;
        @Column(name="event_id") private String eventId;
        @Column(name="attendee") private String attendee;
}