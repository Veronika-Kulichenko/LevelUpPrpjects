package com.levelup.table.entity;

/**
 * @author Veronika Kulichenko on 24.07.16.
 */
public class Citizen extends Entity {

    private String firstName;
    private String lastName;
    private int age;
    private Long streetId;

  public Citizen() {
  }

  public Citizen(final Long id) {
    super(id);
  }

  public Citizen(Long id, String firstName, String lastName, int age, Long streetId) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.streetId = streetId;
    }

    public Citizen(String firstName, String lastName, int age, Long streetId) {
      super(null);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.streetId = streetId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "id=" + getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", streetId=" + streetId +
                '}';
    }
}


