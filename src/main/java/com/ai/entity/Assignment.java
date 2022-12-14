package com.ai.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@lombok.Builder
@Table(name="assignment")
public class Assignment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @NotBlank(message = "Assignment title is required!")
    private String title;

    @Column(nullable = false)
    @NotNull(message = "Start date is required!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime start;

    @Column(nullable = false)
    @NotNull(message = "End date is required!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime end;

    @Column(nullable = false)
    @Min(value = 1,message = "Full mark is required!")
    private int mark;

    private String file;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Batch batch;

    @OneToMany(mappedBy = "assignment", cascade = {CascadeType.MERGE, CascadeType.PERSIST},orphanRemoval = true)
    private List<AssignmentAnswer> assignmentAnswers;

    @Transient
    private int batchId;

    @Transient
    private MultipartFile files;
    
    public enum Status{
        Assigned, TurnedIn, Late, Missing 
    }
}
