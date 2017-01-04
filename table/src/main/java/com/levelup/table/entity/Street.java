package com.levelup.table.entity;

/**
 * @author Veronika Kulichenko on 24.07.16.
 */
public class Street extends Entity {

  public Street() {
  }

  private String streetName;

  public Street(final Long id) {
    super(id);
  }

  public Street(String streetName) {
      super(null);
      this.streetName = streetName;
    }

    public Street(Long id, String streetName) {
        super(id);
        this.streetName = streetName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

  @Override
  public String toString() {
    return "Street{" + "id=" + getId() + ", streetName='" + streetName + '\'' + '}';
  }
}
