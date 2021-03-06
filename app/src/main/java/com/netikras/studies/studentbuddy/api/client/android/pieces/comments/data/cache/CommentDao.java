package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data.cache;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.GenericDao;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.cahe.PersonDao;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

import java.util.Date;
import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.coalesce;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;
import static java.util.Arrays.asList;

/**
 * Created by netikras on 17.12.8.
 */

public class CommentDao extends GenericDao<CommentDto> {


    PersonDao personCache;

    public CommentDao(CacheManager cacheManager) {
        super(cacheManager, "comment");
        personCache = cacheManager.getDao(PersonDao.class);
    }

    @Override
    public String getId(CommentDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, title text, text_ text, entity_type text, entity_id text, category text, created_on integer, updated_on integer, tags text, author_id text)";
    }

    @Override
    protected void persist(CommentDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("title", entity.getTitle());
        values.put("text_", entity.getText());
        values.put("entity_type", entity.getEntityType());
        values.put("entity_id", entity.getEntityId());
        values.put("category", entity.getCategory());
        values.put("created_on", toTimestamp(entity.getCreatedOn()));
        values.put("updated_on", toTimestamp(entity.getUpdatedOn()));
        values.put("tags", joinCollection(entity.getTags()));

        if (entity.getAuthor() != null) {
            values.put("author_id", entity.getAuthor().getId());
        }
    }

    @Override
    protected CommentDto restore(DBResults results) {

        CommentDto comment = new CommentDto();

        comment.setId(results.getString("id"));
        comment.setTitle(results.getString("title"));
        comment.setText(results.getString("text_"));
        comment.setEntityType(results.getString("entity_type"));
        comment.setEntityId(results.getString("entity_id"));
        comment.setCategory(results.getString("category"));
        comment.setCreatedOn(fromTimestamp(results.getLong("created_on")));
        comment.setUpdatedOn(fromTimestamp(results.getLong("updated_on")));

        List<String> tags = toCollection(results.getString("tags"));
        if (!isNullOrEmpty(tags)) {
            comment.setTags(tags);
        }

        String id = results.getString("author_id");
        if (!isNullOrEmpty(id)) {
            comment.setAuthor(new PersonDto());
            comment.getAuthor().setId(id);
        }

        return comment;
    }


    @Override
    public CommentDto fill(CommentDto entity) {
        if (entity == null) {
            return entity;
        }
        super.fill(entity);

        entity.setAuthor(prefill(entity.getAuthor(), personCache));

        return entity;
    }

    @Override
    public CommentDto putWithImmediates(CommentDto entity) {
        if (entity == null) {
            return entity;
        }
        super.putWithImmediates(entity);

        personCache.put(entity.getAuthor());

        return entity;
    }

    public Date getLastUpdateDate(String entityType, String entityId) {
        CommentDto dto = getLastUpdated(entityType, entityId);
        if (dto != null) {
            return (Date) coalesce(dto.getUpdatedOn(), dto.getCreatedOn());
        }
        return null;
    }


    public CommentDto getLastUpdated(String entityType, String entityId) {
        StringBuilder qryBuilder = new StringBuilder();

        qryBuilder
                .append("select * " +
                        "from " +
                        "     ").append(getTableName()).append(" " +
                "where " +
                "    id in ( " +
                "        select id " +
                "        from ( " +
                "            select " +
                "                id, " +
                "                coalesce (updated_on, created_on) last_touch " +
                "            from " +
                "                 ").append(getTableName()).append(" ");
        if (entityType != null) {
            qryBuilder.append(" where entity_type = '").append(entityType).append("'");
        }
        if (entityId != null) {
            if (entityType == null) {
                qryBuilder.append(" where entity_id = ").append(entityId).append(" ");
            } else {
                qryBuilder.append(" and entity_id = ").append(entityId).append(" ");
            }
        }
        qryBuilder.append("  ) " +
                "        order by last_touch desc " +
                "        limit 1 " +
                "        ) ")
        ;

        return queryFirst(qryBuilder.toString());

    }


    public List<CommentDto> getAllByAuthor(String personId) {
        return getAllWhere("author_id = ?", personId);
    }

    public List<CommentDto> getAllByTag(String tag, long pagesize, long pageno) {
        long fromResult = (pagesize * pageno);
        long upToResult = fromResult + pagesize;
        return queryAll("select * from " + getTableName() + " where tags like '%?%' order by created_on asc limit ?, ?", tag, "" + fromResult, "" + upToResult);
    }

    public List<CommentDto> getAllByEntity(String type, String typeId) {
        if (type == null) {
            if (typeId == null) {
                return asList();
            }
            return getAllWhere("entity_id = ?", typeId);
        }
        if (typeId == null) {
            return getAllWhere("entity_type = ?", type);
        }
        return getAllWhere("entity_type = ? and entity_id = ?", type, typeId);

    }

    public int getCountByEntity(String entityType, String entityId) {
        if (isNullOrEmpty(entityId)) {
            return getCountWhere("entity_type = ?", entityType);
        }
        return getCountWhere("entity_type = ? and entity_id = ?", entityType, entityId);
    }
}
