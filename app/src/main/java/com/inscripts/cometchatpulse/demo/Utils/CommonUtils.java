package com.inscripts.cometchatpulse.demo.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import com.cometchat.pro.models.Group;
import com.cometchat.pro.models.User;
import com.inscripts.cometchatpulse.demo.Activity.IncomingCallActivity;
import com.inscripts.cometchatpulse.demo.R;
import com.inscripts.cometchatpulse.demo.Activity.CreateGroupActivity;
import com.inscripts.cometchatpulse.demo.Activity.GroupChatActivity;
import com.inscripts.cometchatpulse.demo.Contracts.StringContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommonUtils {

    static ConnectivityManager cm = null;
    private static final String TAG = "CommonUtils";

    public static boolean isConnected(Context context) {

        if (cm == null) {
            cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        NetworkInfo var0 = cm.getActiveNetworkInfo();
        return null != var0 && var0.isConnectedOrConnecting();
    }

    public static String getRandomElement() {
        List<String> list=new ArrayList<>();
        list.add("superhero1");
        list.add("superhero2");
        list.add("superhero3");
        list.add("superhero4");
        list.add("superhero5");
        list.add("testuser128");
        list.add("testuser12");
        list.add("testuser13");
        list.add("testuser11");
        list.add("testuser15");
        list.add("testuser25");
        list.add("testuser26");
        list.add("testuser28");
        list.add("testuser27");
        list.add("testuser30");
        list.add("testuser31");
        list.add("testuser33");

        Random rand = new Random();
        String  s=list.get(rand.nextInt(list.size()));
        Log.d(TAG, "getRandomElement: "+s);
        return s;
    }

    public static void startCallIntent(Context context, User user, String type,
                                       boolean isOutgoing, @NonNull String sessionId) {
        Intent videoCallIntent = new Intent(context, IncomingCallActivity.class);
        videoCallIntent.putExtra(StringContract.IntentStrings.NAME, user.getName());
        videoCallIntent.putExtra(StringContract.IntentStrings.ID,user.getUid());
        videoCallIntent.putExtra(StringContract.IntentStrings.SESSION_ID,sessionId);
        videoCallIntent.putExtra(StringContract.IntentStrings.AVATAR, user.getAvatar());
        videoCallIntent.setAction(type);

        if (isOutgoing) {
            videoCallIntent.setType(StringContract.IntentStrings.OUTGOING);
        }
        else {
            videoCallIntent.setType(StringContract.IntentStrings.INCOMING);
        }
        context.startActivity(videoCallIntent);
    }

    public static void startCallIntent(Context context, Group group, String type,
                                       boolean isOutgoing, @NonNull String sessionId) {
        Intent videoCallIntent = new Intent(context, IncomingCallActivity.class);
        videoCallIntent.putExtra(StringContract.IntentStrings.NAME, group.getName());
        videoCallIntent.putExtra(StringContract.IntentStrings.ID,group.getGuid());
        videoCallIntent.putExtra(StringContract.IntentStrings.SESSION_ID,sessionId);
        videoCallIntent.putExtra(StringContract.IntentStrings.AVATAR, group.getIcon());
        videoCallIntent.setAction(type);

        if (isOutgoing) {
            videoCallIntent.setType(StringContract.IntentStrings.OUTGOING);
        }
        else {
            videoCallIntent.setType(StringContract.IntentStrings.INCOMING);
        }
        context.startActivity(videoCallIntent);
    }


    @SafeVarargs
    public static void startActivityIntent(com.cometchat.pro.models.Group group, Context context, boolean flag, @Nullable Pair<View,String> ...pairs) {
        Intent intent = new Intent(context, GroupChatActivity.class);
        //intent.putExtra(StaticMembers.INTENT_GROUP_ID, chatroomId);
        Logger.error("", "GroupId : " + String.valueOf(group.getGuid()));
        intent.putExtra(StringContract.IntentStrings.INTENT_GROUP_ID, group.getGuid());
        intent.putExtra(StringContract.IntentStrings.INTENT_GROUP_NAME, group.getName());
        intent.putExtra(StringContract.IntentStrings.INTENT_GROUP_ISOWNER, group.getOwner());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        if (flag) {
            ((CreateGroupActivity)context).finish();
        }
         if (pairs!=null) {
             ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, pairs);
             context.startActivity(intent, optionsCompat.toBundle());
         }
         else {
             ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.fade_scale_out);
             context.startActivity(intent);
         }
    }

    public static float dpToPx(Context context, float valueInDp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = valueInDp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static SpannableString setTitle(String title, Context context)
    {
        SpannableString ss = new SpannableString(title);
        ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.secondaryColor)),
                0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ss;
    }

    public static AudioManager getAudioManager(Context context) {
        return (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    }
}
