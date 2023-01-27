package main.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@JsonIgnoreProperties("done")
public class Task {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(name = "creation_date")
 private LocalDateTime creationDate;

 @Column(name = "is_done")
 @JsonProperty("isDone")
 private Boolean isDone = null;

 private String title;

 private String description;

}
