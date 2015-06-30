package ro.octa.greendaosample.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table DBUSER_DETAILS.
 */
public class DBUserDetailsDao extends AbstractDao<DBUserDetails, Long> {

    public static final String TABLENAME = "DBUSER_DETAILS";

    /**
     * Properties of entity DBUserDetails.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property NickName = new Property(1, String.class, "nickName", false, "NICK_NAME");
        public final static Property FirstName = new Property(2, String.class, "firstName", false, "FIRST_NAME");
        public final static Property LastName = new Property(3, String.class, "lastName", false, "LAST_NAME");
        public final static Property BirthDate = new Property(4, java.util.Date.class, "birthDate", false, "BIRTH_DATE");
        public final static Property Gender = new Property(5, String.class, "gender", false, "GENDER");
        public final static Property Country = new Property(6, String.class, "country", false, "COUNTRY");
        public final static Property RegistrationDate = new Property(7, java.util.Date.class, "registrationDate", false, "REGISTRATION_DATE");
        public final static Property UserId = new Property(8, long.class, "userId", false, "USER_ID");
    }

    ;

    private DaoSession daoSession;


    public DBUserDetailsDao(DaoConfig config) {
        super(config);
    }

    public DBUserDetailsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'DBUSER_DETAILS' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'NICK_NAME' TEXT NOT NULL ," + // 1: nickName
                "'FIRST_NAME' TEXT NOT NULL ," + // 2: firstName
                "'LAST_NAME' TEXT NOT NULL ," + // 3: lastName
                "'BIRTH_DATE' INTEGER," + // 4: birthDate
                "'GENDER' TEXT," + // 5: gender
                "'COUNTRY' TEXT," + // 6: country
                "'REGISTRATION_DATE' INTEGER NOT NULL ," + // 7: registrationDate
                "'USER_ID' INTEGER NOT NULL );"); // 8: userId
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DBUSER_DETAILS'";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, DBUserDetails entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getNickName());
        stmt.bindString(3, entity.getFirstName());
        stmt.bindString(4, entity.getLastName());

        java.util.Date birthDate = entity.getBirthDate();
        if (birthDate != null) {
            stmt.bindLong(5, birthDate.getTime());
        }

        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(6, gender);
        }

        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(7, country);
        }
        stmt.bindLong(8, entity.getRegistrationDate().getTime());
        stmt.bindLong(9, entity.getUserId());
    }

    @Override
    protected void attachEntity(DBUserDetails entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public DBUserDetails readEntity(Cursor cursor, int offset) {
        DBUserDetails entity = new DBUserDetails( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getString(offset + 1), // nickName
                cursor.getString(offset + 2), // firstName
                cursor.getString(offset + 3), // lastName
                cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // birthDate
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // gender
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // country
                new java.util.Date(cursor.getLong(offset + 7)), // registrationDate
                cursor.getLong(offset + 8) // userId
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, DBUserDetails entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNickName(cursor.getString(offset + 1));
        entity.setFirstName(cursor.getString(offset + 2));
        entity.setLastName(cursor.getString(offset + 3));
        entity.setBirthDate(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setGender(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCountry(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setRegistrationDate(new java.util.Date(cursor.getLong(offset + 7)));
        entity.setUserId(cursor.getLong(offset + 8));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(DBUserDetails entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(DBUserDetails entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getDBUserDao().getAllColumns());
            builder.append(" FROM DBUSER_DETAILS T");
            builder.append(" LEFT JOIN DBUSER T0 ON T.'USER_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected DBUserDetails loadCurrentDeep(Cursor cursor, boolean lock) {
        DBUserDetails entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        DBUser user = loadCurrentOther(daoSession.getDBUserDao(), cursor, offset);
        if (user != null) {
            entity.setUser(user);
        }

        return entity;
    }

    public DBUserDetails loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();

        String[] keyArray = new String[]{key.toString()};
        Cursor cursor = db.rawQuery(sql, keyArray);

        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }

    /**
     * Reads all available rows from the given cursor and returns a list of new ImageTO objects.
     */
    public List<DBUserDetails> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<DBUserDetails> list = new ArrayList<DBUserDetails>(count);

        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }

    protected List<DBUserDetails> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }


    /**
     * A raw-style query where you can pass any WHERE clause and arguments.
     */
    public List<DBUserDetails> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }

}
