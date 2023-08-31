package com.bubblebubble.hr.payment.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_SEVERANCE")
@Getter
@NoArgsConstructor
public class Severance {
    @EmbeddedId
    SeveracePK id;
}
