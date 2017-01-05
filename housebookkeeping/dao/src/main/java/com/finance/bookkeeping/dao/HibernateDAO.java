package com.finance.bookkeeping.dao;

import java.util.List;

/**
 * @author Veronika Kulichenko on 29.12.16.
 */
public interface HibernateDAO<T> {

  void saveOrUpdate(T t);

  T getOneById(long id);

  List<T> getAll();

  void delete(T t);


}
