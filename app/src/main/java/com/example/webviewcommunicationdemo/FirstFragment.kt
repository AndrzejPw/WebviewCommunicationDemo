package com.example.webviewcommunicationdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import androidx.fragment.app.Fragment
import com.example.webviewcommunicationdemo.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = binding.webView
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(
            WebAppInterface(requireActivity() as MainActivity),
            "Android"
        )
        webView.loadData(html, "text/html", "UTF-8")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun postResult(result: String) {
        binding.webView.evaluateJavascript("displayAndroidResponse('$result')", null)
    }
}

class WebAppInterface(private val mainActivity: MainActivity) {
    /** Show a toast from the web page  */
    @JavascriptInterface
    fun sendToAndroid(message: String) {
        mainActivity.runOnUiThread {
            mainActivity.openActivityForResult(message)
        }
    }
}

val html = """
<html>
<head>
    <style>
     .sent{
        background: blue;
        color:white;
      }
      .received{
        background: green;
        color:white;
      }
    </style>
<script>
function displayAndroidResponse(result){
  displayMessage(result, "received");
}
function displayMessage(message, cssClass) {
  const messages = document.getElementById('chat-messages');
  const li = document.createElement('li');
  li.innerHTML = message;
  li.classList.add(cssClass);
  messages.appendChild(li);
}
</script>
</head>
<body>
<div id="chat-interface">
  <ul id="chat-messages">
  </ul>
  <form id="chat-form">
    <input type="text" id="chat-input" placeholder="Type your message here"></input>
    <input type="submit" value="Send"></input>
  </form>
</div>

<p>
</p>
<script>
const form = document.getElementById('chat-form');
form.addEventListener('submit', (event) => {
  event.preventDefault();
  const input = document.getElementById('chat-input');
  const message = input.value;
  input.value = '';
  displayMessage(message, "sent");
  Android.sendToAndroid(message);
});
</script>
</body>
</html>

""".trimIndent()
