package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.impl.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListHandler;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.presenter.CommentsMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.view.CommentsMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToTimestamp;
import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.timestampToDatetime;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.25.
 */

public class CommentsActivity extends BaseActivity implements CommentsMvpView {

    ViewFields fields;

    @Inject
    CommentsMvpPresenter<CommentsMvpView> presenter;

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_comment);
        setUp();
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu(R.id.btn_comment_main_menu);
    }

    public ViewFields getFields() {
        return fields;
    }

    @Override
    public void showComment(CommentDto commentDto) {
        if (commentDto == null) {
            return;
        }

        getFields().setId(commentDto.getId());
        getFields().setCreateDatetime(commentDto.getCreatedOn());
        getFields().setAuthor(commentDto.getAuthor());
        getFields().setTitle(commentDto.getTitle());
        getFields().setText(commentDto.getText());
        getFields().setTags(commentDto.getTags());
    }

    @OnClick(R.id.btn_comment_author)
    public void showAuthor() {
        PersonDto author = getFields().getAuthor();
        presenter.showAuthor(this, author);
    }

    @OnClick(R.id.btn_comment_tags)
    public void showTags() {
        List<String> tags = getFields().getTags();
        if (tags == null) {
            return;
        }

        showList(this, new ListHandler<String>() {
            @Override
            public List<String> getListData() {
                return tags;
            }

            @Override
            public String getToolbarText() {
                return getString(R.string.lbl_comment_tags);
            }
        });
    }

    class ViewFields extends BaseViewFields {
        @BindView(R.id.txt_edit_comment_id)
        EditText id;
        @BindView(R.id.txt_edit_comment_title)
        EditText title;
        @BindView(R.id.txt_edit_comment_text)
        EditText text;
        @BindView(R.id.txt_edit_comment_create_datetime)
        EditText createDate;
        @BindView(R.id.btn_comment_author)
        Button author;
        @BindView(R.id.btn_comment_tags)
        Button tags;

        public String getId() {
            return getString(id);
        }

        public void setId(String id) {
            setString(this.id, id);
        }

        public String getTitle() {
            return getString(title);
        }

        public void setTitle(String title) {
            setString(this.title, title);
        }

        public String getText() {
            return getString(text);
        }

        public void setText(String text) {
            setString(this.text, text);
        }

        public String getCreateDate() {
            return getString(createDate);
        }

        public void setCreateDate(String createDate) {
            setString(this.createDate, createDate);
        }

        public Date getCreateDatetime() {
            return timestampToDatetime(getCreateDate());
        }

        public void setCreateDatetime(Date createDate) {
            setCreateDate(datetimeToTimestamp(createDate));
        }

        public String getAuthorName() {
            return getString(author);
        }

        public void setAuthorName(String author) {
            setString(this.author, author);
        }

        public void setAuthor(PersonDto personDto) {
            setTag(author, personDto);
            if (personDto != null) {
                setAuthorName(personDto.getFirstName() + " " + personDto.getLastName());
            }
        }

        public PersonDto getAuthor() {
            return (PersonDto) getTag(author);
        }

        public void setTagsCount(String count) {
            setString(tags, count);
        }

        public String getTagsCount() {
            return getString(tags);
        }

        public void setTags(List<String> tags) {
            setTag(this.tags, tags);
            if (tags != null) {
                setTagsCount("" + tags.size());
            }
        }

        public List<String> getTags() {
            return (List<String>) getTag(tags);
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return null;
        }
    }
}
