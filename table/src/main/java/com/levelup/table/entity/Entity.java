package com.levelup.table.entity;

/**
 * @author Veronika Kulichenko on 02.01.17.
 */
public abstract class Entity {
  private Long id;

  public Entity() {
  }

  public Entity(final Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }
}
