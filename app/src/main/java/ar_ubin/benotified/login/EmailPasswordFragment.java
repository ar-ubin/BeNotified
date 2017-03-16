package ar_ubin.benotified.login;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ar_ubin.benotified.R;
import ar_ubin.benotified.base.BaseFragment;
import ar_ubin.benotified.data.models.User;

import static com.google.common.base.Preconditions.checkNotNull;

public class EmailPasswordFragment extends BaseFragment implements LoginContract.View
{
    private AuthListener mAuthListener;
    public LoginContract.Presenter mPresenter;

    private EditText vEmail;
    private EditText vPassword;
    private Button vEmailSignIn;

    public interface AuthListener
    {
        void onLogedIn( User user );
    }

    public static EmailPasswordFragment newInstance() {
        return new EmailPasswordFragment();
    }

    @Override
    public void setPresenter( LoginContract.Presenter presenter ) {
        mPresenter = checkNotNull( presenter );
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if( context instanceof AuthListener ) {
            mAuthListener = (AuthListener) context;
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        final View fragmentView = inflater.inflate( R.layout.fragment_email_password, container, false );

        vEmail = (EditText) fragmentView.findViewById( R.id.field_email );
        vPassword = (EditText) fragmentView.findViewById( R.id.field_password );
        vEmailSignIn = (Button) fragmentView.findViewById( R.id.email_sign_in_button );

        initializeClickListener();

        return fragmentView;
    }

    private void initializeClickListener() {
        vEmailSignIn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v ) {
                signIn( vEmail.getText().toString(), vPassword.getText().toString() );
            }
        } );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        mAuthListener = null;
        super.onDetach();
    }

    private void signIn( String email, String password ) {
        if( !validateForm() ) {
            return;
        }

        mPresenter.login( getActivity(), email, password );
    }

    private boolean validateForm() {
        return validateRequiredText( vEmail ) && validateRequiredText( vPassword );
    }

    @Override
    public void isAuthorized( User user ) {
        mAuthListener.onLogedIn( user );
    }

    @Override
    public void showLoginComplete( User user ) {
        isAuthorized( user );
    }

    @Override
    public void showLoadingProgress() {
        showProgressDialog();
    }

    @Override
    public void hideLoadingProgress() {
        hideProgressDialog();
    }

    @Override
    public void showLoadingLoginError( String message ) {
        Snackbar.make( getView(), "Authentification failed: " + message, Snackbar.LENGTH_SHORT ).show();
    }
}
