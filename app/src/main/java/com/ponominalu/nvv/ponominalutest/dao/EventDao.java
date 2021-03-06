package com.ponominalu.nvv.ponominalutest.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.ponominalu.nvv.ponominalutest.dao.Event;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EVENT.
*/
public class EventDao extends AbstractDao<Event, Long> {

    public static final String TABLENAME = "EVENT";

    /**
     * Properties of entity Event.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Sub_id = new Property(2, Long.class, "sub_id", false, "SUB_ID");
        public final static Property Date = new Property(3, String.class, "date", false, "DATE");
        public final static Property End_date = new Property(4, String.class, "end_date", false, "END_DATE");
        public final static Property Image_name = new Property(5, String.class, "image_name", false, "IMAGE_NAME");
        public final static Property Image = new Property(6, byte[].class, "image", false, "IMAGE");
        public final static Property Min_price = new Property(7, Long.class, "min_price", false, "MIN_PRICE");
        public final static Property Max_price = new Property(8, Long.class, "max_price", false, "MAX_PRICE");
        public final static Property Count = new Property(9, Long.class, "count", false, "COUNT");
        public final static Property Description = new Property(10, String.class, "description", false, "DESCRIPTION");
        public final static Property Category_id = new Property(11, Long.class, "category_id", false, "CATEGORY_ID");
    };


    public EventDao(DaoConfig config) {
        super(config);
    }
    
    public EventDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EVENT' (" + //
                "'ID' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'TITLE' TEXT," + // 1: title
                "'SUB_ID' INTEGER," + // 2: sub_id
                "'DATE' TEXT," + // 3: date
                "'END_DATE' TEXT," + // 4: end_date
                "'IMAGE_NAME' TEXT," + // 5: image_name
                "'IMAGE' BLOB," + // 6: image
                "'MIN_PRICE' INTEGER," + // 7: min_price
                "'MAX_PRICE' INTEGER," + // 8: max_price
                "'COUNT' INTEGER," + // 9: count
                "'DESCRIPTION' TEXT," + // 10: description
                "'CATEGORY_ID' INTEGER);"); // 11: category_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EVENT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Event entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        Long sub_id = entity.getSubevents().get(0).getSub_id();
        if (sub_id != null) {
            stmt.bindLong(3, sub_id);
        }
 
        String date = entity.getSubevents().get(0).getDate();
        if (date != null) {
            stmt.bindString(4, date);
        }
 
        String end_date = entity.getSubevents().get(0).getEnd_date();
        if (end_date != null) {
            stmt.bindString(5, end_date);
        }
 
        String image_name = entity.getSubevents().get(0).getImage_name();
        if (image_name != null) {
            stmt.bindString(6, image_name);
        }
 
        byte[] image = entity.getSubevents().get(0).getImage();
        if (image != null) {
            stmt.bindBlob(7, image);
        }
 
        Long min_price = entity.getSubevents().get(0).getMin_price();
        if (min_price != null) {
            stmt.bindLong(8, min_price);
        }
 
        Long max_price = entity.getSubevents().get(0).getMax_price();
        if (max_price != null) {
            stmt.bindLong(9, max_price);
        }
 
        Long count = entity.getSubevents().get(0).getCount();
        if (count != null) {
            stmt.bindLong(10, count);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(11, description);
        }
 
        Long category_id = entity.getCategory_id();
        if (category_id != null) {
            stmt.bindLong(12, category_id);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Event readEntity(Cursor cursor, int offset) {
        Event entity = new Event( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // sub_id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // date
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // end_date
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // image_name
            cursor.isNull(offset + 6) ? null : cursor.getBlob(offset + 6), // image
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7), // min_price
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8), // max_price
            cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9), // count
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // description
            cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11) // category_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Event entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.getSubevents().get(0).setSub_id(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.getSubevents().get(0).setDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.getSubevents().get(0).setEnd_date(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.getSubevents().get(0).setImage_name(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.getSubevents().get(0).setImage(cursor.isNull(offset + 6) ? null : cursor.getBlob(offset + 6));
        entity.getSubevents().get(0).setMin_price(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
        entity.getSubevents().get(0).setMax_price(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
        entity.getSubevents().get(0).setCount(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
        entity.setDescription(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCategory_id(cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Event entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Event entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
