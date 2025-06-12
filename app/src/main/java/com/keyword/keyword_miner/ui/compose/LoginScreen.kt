package com.keyword.keyword_miner.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.keyword.keyword_miner.R
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle

val neoBold = FontFamily(
    Font(R.font.nanumsquareneo_deb)
)
val neoRegular = FontFamily(
    Font(R.font.nanumsquareneo_brg)
)

@Composable
fun LoginScreen() {
    var blogId by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(180.dp))
        LoginTitle()
        Spacer(modifier = Modifier.height(60.dp))
        LoginBody(
            blogId = blogId, onBlogIdChange = {
                blogId = it
            })
        Spacer(modifier = Modifier.height(30.dp))
        LoginBottom(onLoginClick = {

        }, onNonLoginClick = {

        })
    }
}

@Composable
fun LoginBottom(
    onLoginClick: () -> Unit, onNonLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                contentColor = colorResource(R.color.white),
                containerColor = colorResource(R.color.appbar)
            ),
            shape = RoundedCornerShape(15)
        ) {
            Text(text = stringResource(R.string.login_register))
        }
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                contentColor = colorResource(R.color.white),
                containerColor = colorResource(R.color.appbar)
            ),
            shape = RoundedCornerShape(15)
        ) {
            Text(text = stringResource(R.string.login_non_regsiter))
        }
    }
}

@Composable
fun LoginBody(blogId: String, onBlogIdChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(40.dp)) {
        OutlinedTextField(
            value = blogId,
            onValueChange = onBlogIdChange,
            label = { Text(text = stringResource(R.string.login_id_hint)) },
            textStyle = TextStyle(
                fontFamily = neoRegular
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.login_id_helper),
            fontFamily = neoRegular,
            fontSize = 12.sp,
        )
    }
}

@Composable
fun LoginTitle() {
    Text(
        modifier = Modifier.padding(start = 30.dp),
        text = stringResource(id = R.string.login_title),
        fontSize = 50.sp,
        color = colorResource(R.color.black),
        fontFamily = neoBold
    )
    Text(
        modifier = Modifier.padding(top = 15.dp, start = 30.dp),
        text = stringResource(id = R.string.login_sub_title),
        fontFamily = neoRegular
    )
}
