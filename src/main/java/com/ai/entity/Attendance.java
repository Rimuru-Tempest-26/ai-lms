package com.ai.entity;

import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="attendance")
public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "Attendance Status is required!")
    private String status;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Attendance Date is required!")
    private LocalDate date;

    @ManyToOne
    private User user;

    @ManyToOne
    private Batch batch;
}
