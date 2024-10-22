package tokyo.townriver.samplesdkpj

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tokyo.townriver.samplesdkpj.R

//import com.example.composeapp.ui.theme.ComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(this)
        }
    }
}

@Composable
fun MyApp(context: Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("profile") {
            ProfileScreen(context)
        }
        composable("notifications") {
            NotificationsScreen()
        }
        composable("products") {
            ProductsScreen()
        }
    }
}

@Composable

fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // カード風にしたボタンを3つ並べる
        HomeButton(
            text = "お知らせ",
            onClick = { navController.navigate("notifications") }
        )
        HomeButton(
            text = "プロフィール",
            onClick = { navController.navigate("profile") }
        )
        HomeButton(
            text = "製品",
            onClick = { navController.navigate("products") }
        )
    }
}


// ボタンデザインをカスタマイズ
@Composable
fun HomeButton(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = text, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun ProfileScreen(context: Context) {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    var mid by remember { mutableStateOf("") }
    var appId by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var saveSuccess by remember { mutableStateOf(false) } // 保存成功フラグ

    // SharedPreferencesからデータを読み込む
    LaunchedEffect(Unit) {
        mid = sharedPref.getString("mid", "") ?: ""
        appId = sharedPref.getString("appId", "") ?: ""
        token = sharedPref.getString("token", "") ?: ""
        url = sharedPref.getString("url", "") ?: ""
    }

    // 全体のレイアウトとスタイル
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "プロフィール設定",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // テキストフィールド
        OutlinedTextField(
            value = mid,
            onValueChange = { mid = it },
            label = { Text("MID") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = appId,
            onValueChange = { appId = it },
            label = { Text("App ID") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = token,
            onValueChange = { token = it },
            label = { Text("Token") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("URL") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(32.dp))

        // 保存ボタン
        Button(
            onClick = {
                with(sharedPref.edit()) {
                    putString("mid", mid)
                    putString("appId", appId)
                    putString("token", token)
                    putString("url", url)
                    apply()
                }
                saveSuccess = true // 保存成功時にフラグを更新
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("保存", style = MaterialTheme.typography.bodyLarge)
        }

        // 保存成功フラグがtrueのときにメッセージを表示
        if (saveSuccess) {
            Text(
                text = "設定の保存が完了しました。アプリを再起動してみてください。",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 32.dp)
            )
        }
    }
}



@Composable
fun NotificationsScreen() {
    val dummyData = listOf("お知らせ1", "お知らせ2", "お知らせ3", "お知らせ4", "お知らせ5")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dummyData) { notification ->
            NotificationItem(notification)
        }
    }
}

@Composable
fun NotificationItem(notification: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = notification,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 28.sp),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "日付: 2024-10-22", // 仮の日付を表示
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_background), // 適当なアイコンに変更
                contentDescription = "詳細を見る",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}



@Composable
fun ProductsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.toto), // ダミー画像
            contentDescription = "製品画像",
            modifier = Modifier.size(200.dp)
        )
    }
}

