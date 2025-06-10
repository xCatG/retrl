package com.cattailsw.retrl.core.ui.theme // Updated package

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
// import com.cattailsw.retrl.core.ui.R // Remove or comment out if R is not used

// If you were to use custom fonts from res/font:
// val CourierPrime = FontFamily(
//    Font(R.font.courier_prime_regular, FontWeight.Normal),
//    Font(R.font.courier_prime_bold, FontWeight.Bold),
//    Font(R.font.courier_prime_italic, FontWeight.Normal, FontStyle.Italic),
//    Font(R.font.courier_prime_bold_italic, FontWeight.Bold, FontStyle.Italic)
// )
//
// val SpecialElite = FontFamily(
//    Font(R.font.special_elite_regular, FontWeight.Normal)
// )

// Using built-in Monospace for now as a placeholder for typewriter-like font
val TypewriterFontFamily = FontFamily.Monospace // Placeholder

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = TypewriterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = TypewriterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = TypewriterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = TypewriterFontFamily,
        fontWeight = FontWeight.Bold, // Typewriters often had a 'bold' like effect
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = TypewriterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = TypewriterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = TypewriterFontFamily, // Or a slightly cleaner sans-serif for titles
        fontWeight = FontWeight.Normal, // Or bold
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = TypewriterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = TypewriterFontFamily,
        fontWeight = FontWeight.Bold, // Often smaller titles were still quite impactful
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = TypewriterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp, // Standard reading size
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp // Typewriters had distinct spacing
    ),
    bodyMedium = TextStyle(
        fontFamily = TypewriterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = TypewriterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif, // Labels might be cleaner
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
