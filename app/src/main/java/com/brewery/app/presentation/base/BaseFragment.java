package com.brewery.app.presentation.base;

import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.brewery.app.R;
import com.brewery.app.data.datasource.remote.util.RemoteConstants;
import com.brewery.app.exception.ServiceException;

import java.net.UnknownHostException;

/**
 * This classe provide common behaviour to all fragments
 */
public abstract class BaseFragment extends Fragment implements GenericView {

    @Override
    public boolean handleServiceErrors(Throwable error) {
        View view = getView();
        if (view == null) {
            return false;
        }

        Snackbar errorMessage = Snackbar.make(getView(), R.string.error_generic, Snackbar.LENGTH_LONG);
        if (error instanceof UnknownHostException) {
            errorMessage.setText(R.string.error_no_internet_connection);
        } else if (error instanceof ServiceException) {
            final ServiceException serviceException = (ServiceException) error;
            // add switch statement when we have more error codes available
            if (serviceException.getErrorCode() == RemoteConstants.Application.Error.BUSINESS_ERROR) {
                errorMessage.setText(serviceException.getMessage());
            } else {
                errorMessage.setText(getResources().getString(R.string.error_generic));
            }
        }

        // default will show the most generic message
        errorMessage.show();
        return true;
    }
}
