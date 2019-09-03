package com.example.springrest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Indexed
@Entity
@Table(name="user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column
  @Fields({
          @Field,
          @Field(name = "sortName", analyze = Analyze.NO, store = Store.NO, index = Index.NO)
  })
  @SortableField(forField = "sortName")
  private String name;
  @Column
  @Field
  private String surname;
  @Column
  private String email;
  @Column
  private String password;
  @Column(name = "user_type")
  @Enumerated(value = EnumType.STRING)
  private UserType userType;

}
