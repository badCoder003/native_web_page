package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WebViewScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun WebViewScreen(modifier: Modifier = Modifier) {
    var url by remember { mutableStateOf("https://live.healthassure.in/WebApp/#/flutterWeb?userSession=+tF/ydmW6x5X2veQEJk3WIlHPQLqKQ1Vk1shlA2MBT0NfD5fzqdsfIZfBi03WzPBYRQitL7fn+4u1kpNcvOI5ZaRUKbw9HKyAe8Ve7NRiqQ=&hideProfile=false") }
    var webView: WebView? = null
    var showButton by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(true) }

    Column(modifier = modifier.fillMaxSize()) {
        // WebView component
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            super.onReceivedError(view, request, error)
                            Log.e("WebView", "Error: ${error?.description}")
                            isLoading = false // Stop loading indicator on error
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            Log.d("WebView", "Page loaded: $url")
                            isLoading = false // Stop loading indicator when page loads
                        }
                    }
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        loadsImagesAutomatically = true
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    }
                }.also { webView = it }
            },
            modifier = modifier.weight(1f).fillMaxSize()
        )

        // Load initial URL
        LaunchedEffect(url) {
            webView?.loadUrl(url)
            isLoading = true // Show loading indicator while loading
        }
    }

    // Centered Login Button
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (showButton) {
            Button(
                onClick = {
                    url = "https://live.healthassure.in/WebApp/#/flutterWeb?userSession=+tF/ydmW6x5X2veQEJk3WIlHPQLqKQ1Vk1shlA2MBT0NfD5fzqdsfIZfBi03WzPBYRQitL7fn+4u1kpNcvOI5ZaRUKbw9HKyAe8Ve7NRiqQ=&hideProfile=false"
                    webView?.loadUrl(url)
                    showButton = false // Hide button after click
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Login")
            }
        }
    }

    // Loading indicator
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("OQPQP")
    }
}
