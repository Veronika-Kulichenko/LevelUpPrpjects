package com.finance.bookkeeping.core.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Veronika Kulichenko on 12.09.16.
 */
@Entity
@Table(name = "Users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Column
  private String login;
  @Column
  private String email;
  @Column
  private String password;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private List<Balance> balanceList = new ArrayList<>();

  public User() {
  }

  public User(String login, String email, String password) {
    this.login = login;
    this.email = email;
    this.password = password;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Balance> getBalanceList() {
    return balanceList;
  }

  public void setBalanceList(List<Balance> balanceList) {
    this.balanceList = balanceList;
  }

  public void addBalance(Balance balance) {
    balanceList.add(balance);
  }
}
