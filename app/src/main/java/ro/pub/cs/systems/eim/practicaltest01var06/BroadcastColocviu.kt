package ro.pub.cs.systems.eim.practicaltest01var06

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BroadcastColocviu  : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // TODO: exercise 7 - get the action and the extra information from the intent and set the text on the messageTextView
        // TODO: exercise 9 - restart the activity through an intent if the messageTextView is not available

        val action = intent.action
        var data: String? = null

        data = intent.getStringExtra(Constants.DATA).toString()


        Log.i(Constants.TAG_DATA, data)

    }
}