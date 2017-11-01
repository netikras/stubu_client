package com.netikras.studies.studentbuddy.api.client.android.ui.person;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.util.Settings;
import com.netikras.studies.studentbuddy.api.user.generated.PersonApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PersonService extends IntentService {

    private static final String X_USER_ID = Settings.getExtraName("user-id");

    public enum Action {
        GET_BY_ID
    }


    private PersonApiConsumer personApiConsumer;
    private App app;

    public PersonService() {
        super("PersonService");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        app = App.getInstance(this);

        if (personApiConsumer == null) {
            personApiConsumer = new PersonApiConsumer();
        }

        app.attachConsumer(personApiConsumer);
    }

    public static void startGetById(Context context, String userId) {
        Intent intent = new Intent(context, PersonService.class);
        intent.setAction(Action.GET_BY_ID.name());
        intent.putExtra(X_USER_ID, userId);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Action action = Action.valueOf(intent.getAction());

            String useId;

            switch (action) {
                case GET_BY_ID:
                    useId = intent.getStringExtra(X_USER_ID);
                    break;

            }

        }
    }

    private void handleGetByd(String id) {
        PersonDto personDto = personApiConsumer.retrievePersonDto(id);


    }

}
