package com.a203.sixback.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String ctgName;
}
