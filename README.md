# WebviewCommunicationDemo

It shows how webview can invoke native functionality and get results back.

Webview is embeded in [FirstFragment](app/src/main/java/com/example/webviewcommunicationdemo/FirstFragment.kt). It exposes native method sendToAndroid using _addJavascriptInterface_. 
This method launches SecondActivity for result. The result is sent back to Webview using _evaluateJavascript_ and webview displays it.
