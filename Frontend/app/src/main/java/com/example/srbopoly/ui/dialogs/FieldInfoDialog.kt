package com.example.srbopoly.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.srbopoly.data.Player
import com.example.srbopoly.data.fields.BonusField
import com.example.srbopoly.data.fields.Field
import com.example.srbopoly.data.fields.Field.Companion.getFieldImage
import com.example.srbopoly.data.fields.FieldInfo
import com.example.srbopoly.data.fields.FieldType
import com.example.srbopoly.data.fields.JailField
import com.example.srbopoly.data.fields.PropertyField
import com.example.srbopoly.ui.views.FieldView
import com.example.srbopoly.ui.views.PropertyFieldView

@Composable
fun FieldInfoDialog(onDismiss:()->Unit, field: Field)
{
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(getFieldImage(field.FieldType)),
                    contentDescription = field.Name,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    FieldInfo(field)
                }
            }
        }
    }
}




@Preview
@Composable
fun Preview() {
    FieldInfoDialog({},PropertyField("Ime", FieldType.JUZNA_SRBIJA,10, Player(1,"username",100,10,"Red"),60,3).apply { GameFieldID=10 })
}