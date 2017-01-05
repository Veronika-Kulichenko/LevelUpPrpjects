package com.finance.bookkeeping.core.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 * @author Veronika Kulichenko on 12.09.16.
 */
@Entity
@Table
public class Record {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column
  @Type(type = "date")
  private Date date = new Date();
  @Column
  private String name;
  @Column
  @Enumerated(value = EnumType.STRING)
  private RecordType type;
  @Column
  private String category;
  @Column
  private double sum;
  @Column
  private double quantity = 1;
  @Column
  private String units;
  @Column
  private String description;
  @Column(name = "user_id")
  private long userId;

  public Record() {
  }

  public Record(String name, RecordType type, String category, double sum, double quantity, String units, String description, User user) {
    this.name = name;
    this.type = type;
    this.category = category;
    this.sum = sum;
    this.quantity = quantity;
    this.units = units;
    this.description = description;
    this.userId = user.getId();
  }

  public Record(String name, RecordType type, String category, double sum, User user) {
    this.name = name;
    this.type = type;
    this.category = category;
    this.sum = sum;
    this.userId = user.getId();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RecordType getType() {
    return type;
  }

  public void setType(RecordType type) {
    this.type = type;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public double getSum() {
    return sum;
  }

  public void setSum(double sum) {
    this.sum = sum;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public String getUnits() {
    return units;
  }

  public void setUnits(String units) {
    this.units = units;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }
}
