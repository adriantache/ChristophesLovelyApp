package com.adriantache.christopheslovelyapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.edit
import com.adriantache.christopheslovelyapp.ui.theme.ChristophesLovelyAppTheme
import com.adriantache.christopheslovelyapp.view.IdleView
import com.adriantache.christopheslovelyapp.view.LinkInput
import com.adriantache.christopheslovelyapp.view.LoadingView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val SHARED_PREFS_FILE = "prefs"
private const val LINK_KEY = "prefs"

class MainActivity : ComponentActivity() {
    private val preferences by lazy { getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE) }

    private val isProcessingLink = MutableStateFlow(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChristophesLovelyAppTheme {
                var linkPrefix: String? by remember { mutableStateOf(null) }

                LaunchedEffect(Unit) {
                    linkPrefix = preferences.getString(LINK_KEY, "")

                    // This solves the bug where, if the app is dead when sending a link to it, it doesn't react to the intent.
                    if (intent?.action == Intent.ACTION_SEND) {
                        onNewIntent(intent)
                    }
                }

                Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {}

                Dialog(
                    onDismissRequest = { finish() },
                    properties = DialogProperties(usePlatformDefaultWidth = false),
                ) {
                    when {
                        isProcessingLink.collectAsState().value -> LoadingView()

                        linkPrefix?.isEmpty() == true -> LinkInput {
                            preferences.edit { putString(LINK_KEY, it) }
                            linkPrefix = it
                        }

                        linkPrefix != null -> IdleView(linkPrefix) { linkPrefix = "" }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent == null || intent.action != Intent.ACTION_SEND) return

        isProcessingLink.value = true

        val data = intent.extras?.getString(Intent.EXTRA_TEXT)

        val url = data.toString()
        val newIntent = Intent(Intent.ACTION_VIEW)
        val prefix = preferences.getString(LINK_KEY, null) ?: return

        newIntent.setData(Uri.parse(prefix + url))

        CoroutineScope(Dispatchers.Default).launch {
            delay(700) // Allow the loading view some time to be proudly visible.
            startActivity(newIntent)
            finish()
        }
    }
}
