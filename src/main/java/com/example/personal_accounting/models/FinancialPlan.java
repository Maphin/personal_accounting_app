//package com.example.personal_accounting.models;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.Data;
//import com.example.personal_accounting.types.Period;
//import com.example.personal_accounting.types.PlanType;
//import java.util.List;
//
//@Entity(name = "financial_plans")
//@Data
//public class FinancialPlan {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private PlanType type;
//
//    @Column(nullable = false)
//    private String title;
//
//    @Column
//    private Double rate;  // Only applicable for Credit card type
//
//    @Enumerated(EnumType.STRING)
//    @Column()
//    private Period period; // applicable for Credit and Fund card types
//
//    @Column()
//    private int duration; // Duration in years or months, depending on the period
//
//    @OneToMany(mappedBy = "financialPlan", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Account> accounts;
//}
