package com.rajutech.project.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
@Table(name = "client_registration_mstr")
@GenericGenerator(name = "random_id", strategy = "com.rajutech.project.util.RandomIdGenerator")
public class ClientRegEntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator = "random_id")
   @Column(name = "CRM_ID")
   private Long clientId;

   @Column(name = "CRM_BUS_TYPE")
   private String businessType;

   @Column(name = "CRM_CODE")
   private String clientCode;

   @Column(name = "CRM_STATUS")
   private Integer status;

   @Column(name = "CRM_COUNTRY")
   private String country;

   @Column(name = "CRM_WEB_URL")
   private String webSiteURL;

   @Column(name = "CRM_LICENCE_RENWAL")
   private LocalDateTime licence;

   @Column(name = "CRM_CONTACT_PERSON")
   private String contactPerson;

   @Column(name = "CRM_ADDRESS")
   private String clientAddress;

   @ManyToOne
   @JoinColumn(name = "CRM_CREATED_BY", updatable = false)
   private UserMstrEntity createdBy;

   @Column(name = "CRM_CREATED_ON", updatable = false)
   private LocalDateTime createdOn;

   @Column(name = "CRM_EMAIL")
   private String email;

   @Column(name = "CRM_FAX")
   private String fax;

   @Column(name = "CRM_MOBILE_NUM")
   private String mobile;

   @Column(name = "CRM_NAME")
   private String clientName;

   @Column(name = "CRM_PHONE_NUM")
   private String phone;

   @Lob
   @Column(name = "CRM_REMARKS")
   private String remarks;

   @ManyToOne
   @JoinColumn(name = "CRM_UPDATED_BY")
   private UserMstrEntity updatedBy;

   @Column(name = "CRM_UPDATED_ON")
   private LocalDateTime updatedOn;
   
//   @ManyToOne
//   @JoinColumn(name = "PDFL_ID_FK")
//   private ProjDocFileEntity projDocFileEntity;
}
