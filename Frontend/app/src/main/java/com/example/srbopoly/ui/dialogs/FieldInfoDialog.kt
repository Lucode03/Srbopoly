package com.example.srbopoly.ui.dialogs

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.srbopoly.data.fields.BonusField
import com.example.srbopoly.data.fields.Field
import com.example.srbopoly.data.fields.Field.Companion.getFieldImage
import com.example.srbopoly.data.fields.FieldAction
import com.example.srbopoly.data.fields.FieldInfo
import com.example.srbopoly.data.fields.FieldType
import com.example.srbopoly.data.fields.PropertyField
@Composable
fun FieldInfoDialog(onDismiss:()->Unit, field: Field,
                    action:Boolean=false,
                    onResult:(Boolean)->Unit={},isMyTurn:Boolean=true)
{
    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .padding(10.dp)
        ) {
            if (!isMyTurn) {
                Text(
                    "Čekate potez drugog igrača...",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
            Column()
            {
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Image(
                        painter = painterResource(getFieldImage(field.FieldType)),
                        contentDescription = field.Name,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        FieldInfo(field)
                    }
                }

                HorizontalDivider(color = Color.LightGray)

                Spacer(modifier = Modifier.height(10.dp))

                FieldAction(
                    field = field,
                    modifier = Modifier.fillMaxWidth(),
                    action = action,
                    isMyTurn=isMyTurn,
                    onResult = { result ->
                        onResult(result)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    FieldInfoDialog({},PropertyField("Imeeeee", FieldType.JUZNA_SRBIJA,10, null,60,3).apply { GameFieldID=10 },
        true,
        isMyTurn = true)
}