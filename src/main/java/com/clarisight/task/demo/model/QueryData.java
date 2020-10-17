package com.clarisight.task.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "query_data")
public class QueryData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date",nullable = false)
    Date date;

    @Column(name = "query",nullable = false)
    String query;

    @Column(name = "count",nullable = false)
    BigDecimal count;

    @Column(name = "hash_key",unique = true,nullable = false)
    String hashKey;
}
