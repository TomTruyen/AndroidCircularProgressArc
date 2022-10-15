# AndroidCircularProgressArc

![Example GIF](https://github.com/TomTruyen/AndroidCircularProgressArc/blob/main/example.png)

Customizable progress arc for android.

## Import

```gradle
maven { url 'https://jitpack.io' }
```

```gradle
implementation 'com.github.TomTruyen:AndroidCircularProgressArc:1.0.1'
```

## Usage

### XML

```xml
<com.tomtruyen.circularprogressarc.CircularProgressArc
        android:id="@+id/arc"
        android:layout_centerInParent="true"
        android:layout_width="250dp"
        android:layout_height="250dp"
        app:angle_start="180"
        app:angle_sweep="360"
        app:max_progress="100"
        app:animation_duration="1000"
        app:rounded_corners="true"
        app:progress_color="@color/teal_200"
        app:background_color="@color/white"
        app:stroke_width="10dp" />

```

### Kotlin

### Handle click event

```kotlin
findViewById<CircularProgressArc>(R.id.arc)?.let {
    it.setProgress(50)
    // NOTE: This overrides the progress_color
    it.setGradientColors(
        intArrayOf(
                ContextCompat.getColor(this, R.color.teal_200),
                ContextCompat.getColor(this, R.color.teal_700)
        )
    )
}
```

### Styling

Attributes:

```xml
<declare-styleable name="CircularProgressArc">
        <attr name="angle_start" format="float" />
        <attr name="angle_sweep" format="float" />
        <attr name="max_progress" format="integer" />
        <attr name="animation_duration" format="integer" />
        <attr name="rounded_corners" format="boolean" />
        <attr name="progress_color" format="color" />
        <attr name="background_color" format="color" />
        <attr name="stroke_width" format="dimension" />
</declare-styleable>
```

The progress and gradient can only be set from within the Kotlin code.

All attributes als have setters to access from with Kotlin
