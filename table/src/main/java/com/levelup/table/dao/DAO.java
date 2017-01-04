package com.levelup.table.dao;

import java.util.ArrayList;

public interface DAO<T> {

    void create(T t);

    ArrayList<T> read();

    void update(T t);

    void delete(T t);

    T getOneById(long id);
}
