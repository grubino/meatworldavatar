package com.kramerica.meatworldavatar.meatdomain;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.kramerica.meatworldavatar.Relevance;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table RELEVANCE.
*/
public class RelevanceDao extends AbstractDao<Relevance, Long> {

    public static final String TABLENAME = "RELEVANCE";

    /**
     * Properties of entity Relevance.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property EventId = new Property(1, Long.class, "eventId", false, "EVENT_ID");
        public final static Property HabitId = new Property(2, Long.class, "habitId", false, "HABIT_ID");
        public final static Property AvatarId = new Property(3, Long.class, "avatarId", false, "AVATAR_ID");
        public final static Property HabitRelevance = new Property(4, Float.class, "habitRelevance", false, "HABIT_RELEVANCE");
        public final static Property EventRelevance = new Property(5, Float.class, "eventRelevance", false, "EVENT_RELEVANCE");
    };

    private DaoSession daoSession;

    private Query<Relevance> avatar_RelevanceListQuery;

    public RelevanceDao(DaoConfig config) {
        super(config);
    }
    
    public RelevanceDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'RELEVANCE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'EVENT_ID' INTEGER," + // 1: eventId
                "'HABIT_ID' INTEGER," + // 2: habitId
                "'AVATAR_ID' INTEGER," + // 3: avatarId
                "'HABIT_RELEVANCE' REAL," + // 4: habitRelevance
                "'EVENT_RELEVANCE' REAL);"); // 5: eventRelevance
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'RELEVANCE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Relevance entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long eventId = entity.getEventId();
        if (eventId != null) {
            stmt.bindLong(2, eventId);
        }
 
        Long habitId = entity.getHabitId();
        if (habitId != null) {
            stmt.bindLong(3, habitId);
        }
 
        Long avatarId = entity.getAvatarId();
        if (avatarId != null) {
            stmt.bindLong(4, avatarId);
        }
 
        Float habitRelevance = entity.getHabitRelevance();
        if (habitRelevance != null) {
            stmt.bindDouble(5, habitRelevance);
        }
 
        Float eventRelevance = entity.getEventRelevance();
        if (eventRelevance != null) {
            stmt.bindDouble(6, eventRelevance);
        }
    }

    @Override
    protected void attachEntity(Relevance entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Relevance readEntity(Cursor cursor, int offset) {
        Relevance entity = new Relevance( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // eventId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // habitId
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // avatarId
            cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4), // habitRelevance
            cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5) // eventRelevance
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Relevance entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setEventId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setHabitId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setAvatarId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setHabitRelevance(cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4));
        entity.setEventRelevance(cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Relevance entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Relevance entity) {
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
    
    /** Internal query to resolve the "relevanceList" to-many relationship of Avatar. */
    public List<Relevance> _queryAvatar_RelevanceList(Long avatarId) {
        synchronized (this) {
            if (avatar_RelevanceListQuery == null) {
                QueryBuilder<Relevance> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.AvatarId.eq(null));
                avatar_RelevanceListQuery = queryBuilder.build();
            }
        }
        Query<Relevance> query = avatar_RelevanceListQuery.forCurrentThread();
        query.setParameter(0, avatarId);
        return query.list();
    }

}
