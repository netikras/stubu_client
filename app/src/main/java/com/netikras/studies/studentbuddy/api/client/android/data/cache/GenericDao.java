package com.netikras.studies.studentbuddy.api.client.android.data.cache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.netikras.tools.common.io.IoUtils.closeQuietly;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public abstract class GenericDao<E> extends SQLiteOpenHelper {

    private static int version = 2;
    private static String dbName = "stubu.db";
    protected String tableName;

    private CacheManager cacheManager = null;

//    public GenericDao(Context context, String tableName) {
//        super(context, dbName, null, version);
//        this.tableName = tableName;
//    }

    public GenericDao(CacheManager cacheManager, String tableName) {
        super(cacheManager.getAppContext(), dbName, null, version);
        this.tableName = tableName;
        this.cacheManager = cacheManager;
        cacheManager.registerDao(this);
    }

    public CacheManager getManager() {
        return cacheManager;
    }

    public <D extends GenericDao> D getDao(Class<D> type) {
        return cacheManager.getDao(type);
    }


    public abstract String getId(E entity);

    protected abstract String getCreateQuery();

    protected abstract void persist(E entity, ContentValues values);

    protected abstract E restore(DBResults results);

    protected List<E> restoreAll(DBResults results) {
        List<E> resultsList = new ArrayList<>();

        while (results.hasData()) {
            resultsList.add(restore(results));
            results.next();
        }

        return resultsList;
    }

    public void destroy() {
        getWritableDatabase().delete(getTableName(), null, null);
    }


    protected String getTableName() {
        return tableName;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getCreateQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

    public E get(String id) {
        if (!isNullOrEmpty(id)) {
            Cursor cursor = null;
            try {
                cursor = getReadableDatabase()
                        .query(getTableName(), null, "id = ?", new String[]{id}, null, null, null);
                if (cursor.moveToFirst()) {
                    return restore(new DBResults(cursor));
                }
            } finally {
                closeQuietly(cursor);
            }
        }
        return null;
    }

    public List<E> getAllByIds(Collection<E> itemsWithId) {
        if (!isNullOrEmpty(itemsWithId)) {
            List<E> results = new ArrayList<>(itemsWithId.size());
            for (E e : itemsWithId) {
                E result = get(getId(e));
                if (result != null) {
                    results.add(result);
                }
            }
            return results;
        }
        return null;
    }

    public List<E> getAll(Collection<String> ids) {
        if (!isNullOrEmpty(ids)) {
            List<E> results = new ArrayList<>(ids.size());
            for (String id : ids) {
                E result = get(id);
                if (result != null) {
                    results.add(result);
                }
            }
            return results;
        }
        return null;
    }

    public E queryFirst(String qry, String... args) {
        if (!isNullOrEmpty(qry)) {
            Cursor cursor = null;
            try {
                cursor = getReadableDatabase().rawQuery(qry, args);
                if (cursor.moveToFirst()) {
                    return restore(new DBResults(cursor));
                }
            } finally {
                closeQuietly(cursor);
            }
        }
        return null;
    }

    public E getFirstWhere(String conditions, String... args) {
        if (!isNullOrEmpty(conditions)) {
            Cursor cursor = null;
            try {
                cursor = getReadableDatabase()
                        .query(getTableName(), null, conditions, args, null, null, null);
                if (cursor.moveToFirst()) {
                    return restore(new DBResults(cursor));
                }
            } finally {
                closeQuietly(cursor);
            }
        }
        return null;
    }

    public List<E> getAll() {
        return getAllWhere(null);
    }

    public List<E> getAllWhere(String conditions, String... args) {
        List<E> results = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = getReadableDatabase()
                    .query(getTableName(), null, conditions, args, null, null, null);

            if (cursor.moveToFirst()) {
                DBResults dbResults = new DBResults(cursor);
                results.addAll(restoreAll(dbResults));
            }
        } finally {
            closeQuietly(cursor);
        }

        restoreAll(new DBResults(cursor));
        return results;
    }

    public List<E> queryAll(String qry, String... args) {
        List<E> results = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().rawQuery(qry, args);

            if (cursor.moveToFirst()) {
                DBResults dbResults = new DBResults(cursor);
                results.addAll(restoreAll(dbResults));
            }

            restoreAll(new DBResults(cursor));
        } finally {
            closeQuietly(cursor);
        }
        return results;
    }

    public int getCountWhere(String whereClause, String... args) {
        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().rawQuery("select count(*) from " + getTableName() + " where " + whereClause, args);

            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        } finally {
            closeQuietly(cursor);
        }
        return 0;
    }

    public int deleteById(String id) {
        if (!isNullOrEmpty(id)) {
            return getWritableDatabase().delete(getTableName(), "id = ?", new String[]{id});
        }
        return 0;
    }

    public int delete(E entity) {
        if (entity != null) {
            return deleteById(getId(entity));
        }
        return 0;
    }

    public long create(E entity) {
        if (entity != null && !isNullOrEmpty(getId(entity))) {
            ContentValues values = new ContentValues();
            persist(entity, values);

            SQLiteDatabase db = getWritableDatabase();
            return db.insert(getTableName(), null, values);
        }
        return -2;
    }

    public long createAll(Collection<E> entities) {
        long total = 0;
        long status = 0;

        if (!isNullOrEmpty(entities)) {
            for (E entity : entities) {
                status = create(entity);
                if (status >= 0) {
                    total += status;
                }
            }
        }
        return total;
    }

    public int update(E entity) {
        if (entity != null && !isNullOrEmpty(getId(entity))) {
            ContentValues values = new ContentValues();
            persist(entity, values);

            SQLiteDatabase db = getWritableDatabase();
            return db.update(getTableName(), values, "id = ?", new String[]{getId(entity)});
        }

        return 0;
    }


    public E fill(E entity) {
        return entity;
    }

    protected <T> T prefill(T entity, GenericDao<T> cache) {
        if (entity != null && cache != null) {
            if (!isNullOrEmpty(cache.getId(entity))) {
                return cache.get(cache.getId(entity));
            }
        }

        return entity;
    }

    public <C extends Collection<E>> C fillAll(C entities) {
        if (!isNullOrEmpty(entities)) {
            for (E entity : entities) {
                fill(entity);
            }
        }
        return entities;
    }

    public E put(E entity) {
        if (entity != null) {
            E existing = get(getId(entity));
            if (existing == null) {
                create(entity);
            } else {
                update(entity);
            }
        }
        return entity;
    }

    public E putWithImmediates(E entity) {
        return put(entity);
    }

    public Collection<E> putAllWithImmediates(Collection<E> entities) {
        if (!isNullOrEmpty(entities)) {
            for (E entity : entities) {
                putWithImmediates(entity);
            }
        }
        return entities;
    }

    public Collection<E> putAll(Collection<E> entities) {
        if (!isNullOrEmpty(entities)) {
            for (E entity : entities) {
                put(entity);
            }
        }
        return entities;
    }

    public E[] putAll(E... entities) {
        if (!isNullOrEmpty(entities)) {
            for (E entity : entities) {
                put(entity);
            }
        }

        return entities;
    }

    public void close() {
        // yeah, I know what you're thinking. I hate myself a little for this too.
        getWritableDatabase().close();
        getReadableDatabase().close();
    }

    protected long toTimestamp(Date date) {
        if (date != null) {
            return date.getTime();
        }

        return -1;
    }

    protected Date fromTimestamp(long timestamp) {
        if (timestamp >= 0) {
            return new Date(timestamp);
        }
        return null;
    }

    protected List<String> toCollection(String string) {
        if (isNullOrEmpty(string)) {
            return new ArrayList<>();
        }

        return Arrays.asList(string.split(","));
    }

    protected String joinCollection(Collection<String> collection) {
        if (isNullOrEmpty(collection)) {
            return "";
        }
        return collection.stream().collect(Collectors.joining(","));
    }

    public static class DBResults {
        private Cursor cursor;
        private Map<String, Integer> columns;


        DBResults() {
            init();
        }

        DBResults(Cursor c) {
            this();
            setCursor(c);
        }

        private void init() {
            columns = new HashMap<>();
        }

        private void setCursor(Cursor c) {
            columns.clear();
            cursor = c;
            String[] colNames = c.getColumnNames();
            if (!isNullOrEmpty(colNames)) {
                for (int i = 0; i < colNames.length; i++) {
                    columns.put(colNames[i], i);
                }
            }
        }

        public boolean hasNext() {
            return cursor.getCount() > cursor.getPosition() + 1;
        }

        public DBResults next() {
            if (cursor.moveToNext()) {
                return this;
            }
            return null;
        }

        private int getColumnId(String colName) {
            return columns.get(colName);
        }

        public boolean hasData() {
            return cursor.getCount() > 0
                    && cursor.getCount() > cursor.getPosition();
        }


        public int getCount() {
            return cursor.getCount();
        }

        public int getInt(String columnName) {
            if (hasData()) {
                return cursor.getInt(getColumnId(columnName));
            }
            return 0;
        }

        public long getLong(String columnName) {
            if (hasData()) {
                return cursor.getLong(getColumnId(columnName));
            }
            return 0;
        }

        public String getString(String columnName) {
            if (hasData()) {
                return cursor.getString(getColumnId(columnName));
            }
            return null;
        }

        public byte[] getBlob(String columnName) {
            if (hasData()) {
                return cursor.getBlob(getColumnId(columnName));
            }
            return null;
        }

    }

}
