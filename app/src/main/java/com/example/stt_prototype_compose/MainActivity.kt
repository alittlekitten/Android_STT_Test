package com.example.stt_prototype_compose

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.stt_prototype_compose.ui.theme.STT_prototype_composeTheme
import java.util.*


class MainActivity : ComponentActivity() {
    // 결과물이 보여질 곳
    private var outputTxt by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            STT_prototype_composeTheme {
                SttScreen()
            }
        }
    }

    @Composable
    fun SttScreen() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Android STT 프로토타입",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        })
                }) {
                // STT를 위한 컴포저블 소환
                Box(
                    modifier = Modifier.padding(it)
                ) {
                    SpeechToText(outputTxt, ::getSpeechInput)
                }
            }
        }
    }

    // 구글 Speech 서비스 이용하기
    private fun getSpeechInput(context: Context) {
        // 정상작동하지 않는 경우 예외처리
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
        } else {
            // 인텐트 생성
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            // 인식 모델 설정
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
            )

            // 디폴트 언어를 인식하도록 설정
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

            // 프롬프트 메시지 설정
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "말해보세요!")

            // 설정이 완료되었다면 음성을 받아서 후처리
            startForResult.launch(intent)
        }
    }

    // 결과값 저장을 위한 함수 (startActivityForResult에 대한 결과 처리를 위한 함수)
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        activityResult: ActivityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                // 문자열 데이터만 추출
                val result = activityResult.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                // outputTxt에 결과값 저장
                outputTxt = result?.get(0).toString()
            }
    }
}
