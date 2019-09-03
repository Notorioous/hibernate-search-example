package com.example.springrest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Indexed
@Entity
@Table(name = "post")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column
  @Field
  private String title;
  @Column(name = "short_text")
  @Field
  private String shortText;
  @Column
  @Field
  private String text;
  @Column(name = "created_date")
  @Field
  private String createdDate;
  @ManyToOne
  private Category category;
  @Column(name = "pic_url")
  private String picUrl;
  @ManyToOne
  @IndexedEmbedded
  private User user;

}
