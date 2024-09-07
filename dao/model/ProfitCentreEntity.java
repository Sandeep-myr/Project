package com.rajutech.project.dao.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "finance_profit_centre")
@GenericGenerator(name = "random_id", strategy = "com.rajutech.project.util.RandomIdGenerator")
public class ProfitCentreEntity {
 
    @Id
    @GeneratedValue(generator = "random_id")
    @Column(name = "FPC_ID")
    private Long profitCentreId;
 
    @Column(name = "FPC_CODE")
    private String pcCode;
 
    @Column(name = "FPC_NAME")
    private String pcName;
 
    @ManyToOne
    @JoinColumn(name = "FPC_CRM_ID", updatable = false)
    private ClientRegEntity clientRegEntity;
    
	@ManyToOne
	@JoinColumn(name = "FPC_CMP_ID")
    private CompanyMstrEntity company;
 
    @Column(name = "FPC_IS_ITEM")
    private boolean item;
 
    @Column(name = "FPC_IS_ITEM_PARENT")
    private boolean itemParent = false;
 
    @ManyToOne
    @JoinColumn(name = "FPC_PARENT_ID")
    private ProfitCentreEntity profitCentreEntity;
 
    @OneToMany(mappedBy = "profitCentreEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProfitCentreEntity> childEntities = new ArrayList<ProfitCentreEntity>();
 
    @Column(name = "FPC_STATUS")
    private Integer status;
    
    @Column(name = "FPC_VENDORPAYCYCLE")
    private String vendorPayCycle;
   
    @Column(name = "FPC_CYCLEPERIODSTARTFROM")
    private String cyclePeriodStartFrom;
    
    @Column(name = "FPC_CYCLEDUEDATE")
    private String cycleDueDate;
 
    @ManyToOne
    @JoinColumn(name = "FPC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;
 
    @Column(name = "FPC_CREATED_ON", updatable = false)
    private LocalDateTime createdOn;
 
    @ManyToOne
    @JoinColumn(name = "FPC_UPDATED_BY")
    private UserMstrEntity updatedBy;
 
    @Column(name = "FPC_UPDATED_ON")
    private LocalDateTime updatedOn;
    
    @Column(name = "FPC_PC_ID")
    private Long periodicalCycleId;
}
