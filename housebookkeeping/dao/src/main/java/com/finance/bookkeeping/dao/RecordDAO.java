package com.finance.bookkeeping.dao;

import com.finance.bookkeeping.core.entity.Record;
import java.util.Date;
import java.util.List;

/**
 * @author Veronika Kulichenko on 29.12.16.
 */
public interface RecordDAO {

  List<Record> getRecordsByDate(Date date);
}
