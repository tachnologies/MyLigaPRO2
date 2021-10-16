package com.tachnologies.myligapro.common.model.dataAccess;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthenticationAPI {
    private FirebaseAuth mFirebaseAuth;

    private static class SingletonHolder{
        private static final FirebaseAuthenticationAPI INSTANCE = new FirebaseAuthenticationAPI();
    }

    public static FirebaseAuthenticationAPI getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private FirebaseAuthenticationAPI(){
        this.mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getmFirebaseAuth() {
        return this.mFirebaseAuth;
    }

    /**
     * por si agrego el auth con google y asi sacar nombre y foto
     * public Usuario getAuthUser() {
        Usuario user = new Usuario();
        if(mFirebaseAuth != null && mFirebaseAuth.getCurrentUser() != null){
            //user.setUid(mFirebaseAuth.getCurrentUser().getUid());
            //user.setUsername(mFirebaseAuth.getCurrentUser().getDisplayName());
            //user.setEmail(mFirebaseAuth.getCurrentUser().getEmail());
            user.setUid(mFirebaseAuth.getCurrentUser().getPhoneNumber());
            user.setUri(mFirebaseAuth.getCurrentUser().getPhotoUrl());

        }
        return user;
    }*/

    public FirebaseUser getCurrentUser() {
        return mFirebaseAuth.getCurrentUser();
    }
}
