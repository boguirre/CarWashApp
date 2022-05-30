package com.example.carwashapp.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.carwashapp.HomeActivity;
import com.example.carwashapp.LoginActivity;
import com.example.carwashapp.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class UserManagment {
    Context context;
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public static final String PREF_NAME="User_Login";
    public static final String LOGIN="is_user_login";
    public static final String NAME="nombrecli";
    public static final String APE="apellidocli";
    public static final String ID="codcli";


    public UserManagment(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean isUserLogin(){
        return sharedPreferences.getBoolean(LOGIN, false);

    }

    public void UserSessonManage(String name, String ape, int codcli){
        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(APE, ape);
        editor.putInt(ID, codcli);
        editor.apply();
    }

    public void checkLogin(){
        if (!this.isUserLogin()){
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ((HomeActivity) context).finish();
        }
    }

    public HashMap<String, String> userDetails(){
        HashMap<String,String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(APE, sharedPreferences.getString(APE, null));
        user.put(ID, sharedPreferences.getInt(ID, 0)+"");
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((HomeActivity) context).finish();
    }
}
