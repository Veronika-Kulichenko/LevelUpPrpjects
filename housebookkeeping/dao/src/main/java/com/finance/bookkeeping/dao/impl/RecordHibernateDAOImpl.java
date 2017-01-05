package com.finance.bookkeeping.dao.impl;

import com.finance.bookkeeping.core.entity.Record;
import com.finance.bookkeeping.dao.AbstractHibernateDAO;
import com.finance.bookkeeping.dao.RecordDAO;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @author Veronika Kulichenko on 29.12.16.
 */
@Transactional
@Repository
public class RecordHibernateDAOImpl extends AbstractHibernateDAO<Record> implements RecordDAO {

  @Autowired
  private HibernateTemplate hibernateTemplate;

  public RecordHibernateDAOImpl() {
    super(Record.class);
  }

  @PostConstruct
  public void init() {
    setHibernateTemplate(hibernateTemplate);
  }

  @Override
  public List<Record> getRecordsByDate(final Date date) {
    Session session = getSessionFactory().getCurrentSession();
    Criteria criteria = session.createCriteria(clazz).add(Restrictions.eq("date", date));
    return criteria.list();
  }
}
