package com.varietapp.NotaLs.ui.theme
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.varietapp.NotaLs.R

// Set of Material typography styles to start with
val Typography = Typography(
  body2= TextStyle(
       fontFamily = FontFamily(
           Font(R.font.source_sans_pro_regular)
       ),
      fontSize = 19.sp,
      color = Color.White
   ),
    subtitle1 = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.ropa_sans_italic, FontWeight.Normal)
        ),
        fontSize = 18.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.source_sans_pro_bold)
        ),
        fontSize = 22.sp,
        color = Color.White
    )


    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)