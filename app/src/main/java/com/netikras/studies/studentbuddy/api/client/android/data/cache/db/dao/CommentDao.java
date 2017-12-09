package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class CommentDao extends GenericDao<CommentDto> {


    public CommentDao(CacheManager cacheManager) {
        super(cacheManager, "comment");
    }

    @Override
    protected String getId(CommentDto entity) {
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
}
