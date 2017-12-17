package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.impl.view;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListHandler;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListRow;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.presenter.CommentsMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.view.CommentsMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.PersonInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToTimestamp;
import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.timestampToDatetime;
import static com.netikras.tools.common.security.IntegrityUtils.coalesce;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.25.
 */

public class CommentsActivity extends BaseActivity implements CommentsMvpView {

    ViewFields fields;

    @Inject
    CommentsMvpPresenter<CommentsMvpView> presenter;

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList();
    }

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
        addMenu();
        executeTask();
    }

    public ViewFields getFields() {
        return fields;
    }

    @Override
    public void showComment(CommentDto commentDto) {
        showComment(commentDto, false);
    }

    public void showComment(CommentDto commentDto, boolean editable) {
        getFields().reset();
        if (commentDto == null) {
            return;
        }

        getFields().setId(commentDto.getId());
        getFields().setCreateDatetime(commentDto.getCreatedOn());
        getFields().setAuthor(commentDto.getAuthor());
        getFields().setTitle(commentDto.getTitle());
        getFields().setText(commentDto.getText());
        getFields().setTags(commentDto.getTags());
        getFields().setEntityId(commentDto.getEntityId());
        getFields().setEntityType(commentDto.getEntityType());

        if (editable) {
            getFields().enableEdit(true);
        } else {
            prepare(commentDto);
        }
    }

    @Override
    public void showComments(String entityType, String entityId) {

        if (isNullOrEmpty(entityType) || isNullOrEmpty(entityId)) {
            onError("Unable to fetch comments");
            return;
        }

        showLoading();
        presenter.getComments(new ErrorsAwareSubscriber<List<CommentDto>>() {
            @Override
            public void onCacheHit(List<CommentDto> response) {
                super.onCacheHit(response);
                if (!isNullOrEmpty(response)) {
                    setFetchRequired(false);
                    executeOnSuccess(response);
                }
            }

            @Override
            public void onSuccess(List<CommentDto> response) {
                showList(CommentsActivity.this, new ListHandler<CommentDto>() {
                    @Override
                    public ListRow getNewRow(View convertView) {
                        return new CommentsListRow(convertView);
                    }

                    @Override
                    public List<CommentDto> getListData() {
                        return response;
                    }

                    @Override
                    public void onRowClick(CommentDto item) {
                        startView(CommentsActivity.class, new ViewTask<CommentsActivity>() {
                            @Override
                            public void execute() {
                                getActivity().showComment(item);
                            }
                        });
                    }

                    @Override
                    public String getToolbarText() {
                        return getString(R.string.title_comments);
                    }

                    class CommentsListRow extends ListRow<CommentDto> {
                        TextView text;

                        public CommentsListRow(View rowView) {
                            super(null);
                            text = getDefaultListTextView(rowView);
                            rowView.setTag(this);
                        }

                        @Override
                        public void assign(CommentDto item) {
                            if (item == null) {
                                text.setText("<???>");
                                return;
                            }

                            text.setText(item.getTitle());
                        }
                    }
                });
            }
        }, entityType, entityId);

    }

    public CommentDto collect() {
        CommentDto dto = new CommentDto();
        dto.setId(getFields().getId());
        dto.setAuthor(getFields().getAuthor());
        dto.setEntityId(getFields().getEntityId());
        dto.setEntityType(getFields().getEntityType());
        dto.setText(getFields().getText());
        dto.setTitle(getFields().getTitle());
        dto.setTags(getFields().getTags());
        return dto;
    }

    @Override
    protected boolean isPartial() {
        CommentDto dto = collect();
        if (dto == null) {
            return true;
        }
        Object item = coalesce(dto.getAuthor(), dto.getTags());
        return item == null;
    }

    private ServiceRequest.Result<CommentDto> fetch(CommentDto dto) {
        ServiceRequest.Result<CommentDto> result = new ServiceRequest.Result<>();
        showLoading();
        presenter.getById(new ErrorsAwareSubscriber<CommentDto>() {
            @Override
            public void onCacheHit(CommentDto response) {
                if (response != null && !isNullOrEmpty(response.getText())) {
                    setFetchRequired(false);
                    executeOnSuccess(response);
                }
            }

            @Override
            public void onSuccess(CommentDto response) {
                result.setValue(response);
            }
        }, dto.getId());

        return result;
    }

    private void prepare(CommentDto entity) {
        if (entity == null || isNullOrEmpty(entity.getId())) {
            return;
        }
        if (isPartial()) {
            ServiceRequest.Result<CommentDto> result = fetch(entity);
            CommentDto dto = result.get(5, TimeUnit.SECONDS);
            if (!result.isTimedOut()) {
                showComment(dto);
            }
        }
    }

    @OnClick(R.id.btn_comment_author)
    public void showAuthor() {
        PersonDto author = getFields().getAuthor();
        startView(PersonInfoActivity.class, new ViewTask<PersonInfoActivity>() {
            @Override
            public void execute() {
                getActivity().showPerson(author);
            }
        });
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


    @Override
    protected void menuOnClickCreate() {
        showLoading();
        presenter.createComment(new ErrorsAwareSubscriber<CommentDto>() {
            @Override
            public void executeOnSuccess(CommentDto response) {
                if (response != null) {
                    runOnUiThread(() -> showComment(response));
                }
            }
        }, collect());
    }

    @Override
    protected void menuOnClickSave() {
        showLoading();
        CommentDto dto = collect();
        presenter.update(new ErrorsAwareSubscriber<CommentDto>() {
            @Override
            public void onSuccess(CommentDto response) {
                if (response != null) {
                    runOnUiThread(() -> showComment(response));
                }
            }
        }, dto);
    }

    @Override
    protected void menuOnClickDelete() {
        showLoading();
        presenter.deleteComment(new ErrorsAwareSubscriber<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                if (Boolean.TRUE.equals(response)) {
                    runOnUiThread(() -> finish());
                }
            }
        }, getFields().getId());
    }

    @Override
    protected void menuOnClickRefresh() {
        showLoading();
        presenter.getById(new ErrorsAwareSubscriber<CommentDto>() {
            @Override
            public void onSuccess(CommentDto response) {
                if (response != null) {
                    showComment(response);
                }
            }
        }, getFields().getId());
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

        @BindView(R.id.txt_lbl_comment_id)
        TextView lblId;

        String entityId = "";
        String entityType = "";

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
            return Arrays.asList(id, title, text, createDate, author, tags);
        }

        public String getEntityId() {
            return entityId;
        }

        public String getEntityType() {
            return entityType;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public void setEntityType(String entityType) {
            this.entityType = entityType;
        }

        @Override
        protected Map<TextView, Integer> getEditableFields() {
            Map<TextView, Integer> types = super.getEditableFields();

            types.put(title, InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            types.put(text, InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            return types;
        }

        @Override
        public void enableEdit(boolean enable) {
            super.enableEdit(enable);

            if (enable) {
                setVisible(lblId, true);
                setVisible(id, true);
            } else {
                setVisible(lblId, false);
                setVisible(id, null);
            }
        }
    }
}
