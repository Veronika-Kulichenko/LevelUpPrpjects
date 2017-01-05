package com.finance.bookkeeping.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * @author Veronika Kulichenko on 29.12.16.
 */
public abstract class AbstractHibernateDAO<T> extends HibernateDaoSupport implements HibernateDAO<T> {

  protected final Class clazz;

  protected AbstractHibernateDAO(Class clazz) {
    this.clazz = clazz;
  }

  @Override
  public void saveOrUpdate(final T t) {
    getHibernateTemplate().saveOrUpdate(t);
  }

  @Override
  public List<T> getAll() {
    Session session = getSessionFactory().getCurrentSession();
    Criteria criteria = session.createCriteria(clazz);
    return criteria.list();
  }

  @Override
  public void delete(final T t) {
    getHibernateTemplate().delete(t);
  }

  @Override
  public T getOneById(final long id) {
    Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
    Criteria criteria = session.createCriteria(clazz).add(Restrictions.eq("id", id));
    return (T) criteria.uniqueResult();
  }
}
