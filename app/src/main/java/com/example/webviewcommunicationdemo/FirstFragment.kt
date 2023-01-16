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
        binding.webView.evaluateJavascript("displayResponse('$result')", null)
    }
}

class WebAppInterface(private val mainActivity: MainActivity) {
    /** Show a toast from the web page  */
    @JavascriptInterface
    fun showToast(toast: String?) {
        mainActivity.runOnUiThread {
            mainActivity.openActivityForResult()
        }
    }
}

val html = """
<html>
<head>
<script>
function myFunction() {
    Android.showToast('Hello, Android!');
}
var count = 0;
function displayResponse(result) {
      count += 1;
      var newHeader = document.createTextNode("Result no: " + count);
      var newText = document.createTextNode(result);
      var newLine = document.createElement("br");
      document.body.appendChild(newHeader);
      document.body.appendChild(newText);
      document.body.appendChild(newLine);
}
</script>
</head>
<body>
<p>
</p>

<button onclick="myFunction()">Click me</button>

</body>
<script>
displayResponse("test");
document.body.appendChild(document.createTextNode('blue'));
</script>
</html>

""".trimIndent()