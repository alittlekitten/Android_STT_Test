package com.example.stt_prototype_compose

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// STT 기능구현
@Composable
fun SpeechToText(outputTxt: String, getSpeechInput: (context: Context) -> Unit) {
    // 메시지 저장소
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // on the below line we are creating a simple
        // text for displaying the heading.
        Text(
            text = "SpeechRecognizer 테스트",
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 기본 STT를 동작하게 하기 위한 버튼
        Button(
            // 버튼 커스텀
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp, pressedElevation = 0.dp, disabledElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            // 클릭하면 getSpeechInput 팝업 띄우기
            onClick = { getSpeechInput(context) },
        ) {
            // 아이콘 그리기
            Icon(
                // Icons의 이미지를 사용
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "Mic",
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(5.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        // 저장된 텍스트 띄우기
        Text(
            text = outputTxt,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            color = Color.Green
        )
    }
}
