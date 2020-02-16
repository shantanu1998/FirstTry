package e.hp.firsttry.ui.login;

import android.widget.EditText;

import e.hp.firsttry.R;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    ;
    private String displayName;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {

        this.displayName = displayName;

    }

    String getDisplayName() {

        return displayName;
    }
}
