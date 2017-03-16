package ar_ubin.benotified.base;

import android.app.ProgressDialog;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import ar_ubin.benotified.R;

public abstract class BaseFragment extends Fragment
{
    @VisibleForTesting
    protected ProgressDialog mProgressDialog;

    protected void showProgressDialog() {
        if( mProgressDialog == null ) {
            mProgressDialog = new ProgressDialog( getActivity() );
            mProgressDialog.setMessage( getString( R.string.loading ) );
            mProgressDialog.setCancelable( false );
            mProgressDialog.setIndeterminate( true );
        }
        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        if( mProgressDialog != null && mProgressDialog.isShowing() ) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    protected boolean validateRequiredText( EditText requiredTextView ) {
        boolean valid = true;
        String text = requiredTextView.getText().toString().trim();
        if( TextUtils.isEmpty( text ) ) {
            requiredTextView.setError( "Required." );
            valid = false;
        } else {
            requiredTextView.setError( null );
        }

        return valid;
    }

    protected boolean validateRequiredNumber( EditText requiredTextView ) {
        boolean valid = validateRequiredText( requiredTextView );
        if( valid == false ) return valid;

        String input = requiredTextView.getText().toString().trim();
        try {
            Integer.parseInt( input );
        } catch( NumberFormatException e ) {
            requiredTextView.setError( "Must be a Number." );
            valid = false;
        }

        return valid;
    }

    protected void showToastMessage( View view, String message ) {
        Snackbar.make( view, message, Snackbar.LENGTH_SHORT ).setAction( "Action", null ).show();
    }
}
