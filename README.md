# 🎨 Easy Canvas Coordinates with Jetpack Compose

Ever struggled to find the exact coordinates on a canvas or pinpoint touch positions? I’ve been there too! That’s why I built this library using Jetpack Compose to make debugging and validating screen interactions a breeze.

Save time, avoid the hassle, and focus on what really matters—building great apps. Check it out, give it a try, and let me know what you think. Let’s make Android development smoother together! 🚀

# Usage
### Composable
    PointWiser(
        modifier: Modifier = Modifier, 
        pointState: PointState = PointState(),
        view: (@Composable () -> Unit)? = null 
    )

**Modifier** - Modifier of the composable.\
**pointState** - A state object that holds the specifications for customized plotting. \
**view** - Here you will pass your own composable.

### Point state
##### State for the point state.

    data class PointState(
        val labelCordinates: List<LabelCordinates> = emptyList<LabelCordinates>(),
        val hoverCrossOver: HoverCrossOver = HoverCrossOver(),
        val showIntersection: Boolean = true,
        val mainTextStyle: TextStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
        val subTextStyle: TextStyle = TextStyle(color = Color.LightGray, fontSize = 12.sp),
        val mainDividerStrokeWidth: Float = 5f,
        val subDividerStrokeWidth: Float = 3f,
        val step: Int = 100,
        val dashStep: Int = 20,
        val xAxisMainIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
        val xAxisSubIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
        val yAxisMainIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
        val yAxisSubIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
    ) 
Please refer to the **PointState** class documentation for further details.
### Hover cross over
##### State for the cross hover.

    data class HoverCrossOver(
        val isEnabled: Boolean = true,
        val color: Color = Color.LightGray,
        val dashStep: Int = 20,
        val circleRadius: Float = 8f,
        val crossOverTextColor: Color = color
    )

Please refer to the **HoverCrossOver** class documentation for further details.

# Download
> In your settings.gradle file
```
dependencyResolutionManagement {
    ...
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }    
    }
}
```

> In your app.gradle file
```
dependencies {
    ....
    .....  
    implementation 'com.github.akhil-rkrishnan:pointwise:1.0.0'
    
    //Note: Please check the release tag for the latest version in the repo and replace the version with the latest tag
}
```
> ⚠️ **WARNING:**

> These changes mentioned below are essential for the project to compile successfully; otherwise, it will result in a build error.

> If your gradle is in groovy, Add these changes in your **project** level build.gradle file
```
...
// Groovy
buildscript {
    ext {
        compose_version = '1.1.1' // mention your compose version
        compile_sdk_version = 31 // mention your compile sdk version
        min_sdk_version = 21 // mention your min sdk version
        target_sdk_version = compile_sdk_version // mention your target sdk version
    }
}
...
```
> If your gradle is in Kotlin DSL, Add these changes in your **app** level build.gradle file

```
...
// Kotlin DSL
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}
val versionCompileSdk = 35
val versionMinSdk = 24
val versionTargetSdk = 34
extra["versionCompileSdk"] = versionCompileSdk
extra["versionMinSdk"] = versionMinSdk
extra["versionTargetSdk"] = versionTargetSdk

android {
    namespace = "app.android.composepath"
    compileSdk = versionCompileSdk

    defaultConfig {
        applicationId = "app.android.composepath"
        minSdk = versionMinSdk
...
```