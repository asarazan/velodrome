# Velodrome
A small Android library that attempts to simplify the `onActivityResult` flow.

### Thesis
One of the more horrible pieces of the Android lifecycle occurs inside `onActivityResult`. It inevitably descends into a mess of switch statements and spaghetti.

A more reasonable approach is to encapsulate each piece of callback behavior into its own handler method. That is precisely what [Velodrome](http://en.wikipedia.org/wiki/Velodrome) does.

### Java Usage

```java
// In your fragment or activity class.

@OnActivityResult(0)
public void onActivityReturn(Intent data) {
    Log.d("Velo", data.getStringExtra("text"));
}

// Velodrome can also extract simple values from the Intent
// using the @Arg annotation.

@OnActivityResult(1)
public void onActivityReturn(@Arg("text") String text, @Arg("someNum") int num) {
    Log.d("Velo", "Text: " + text + " Num: " + someNum);
}

@OnActivityResult({2, 3})
public void onMultipleCodes(Intent data) {
    Log.d("Velo", "one of two possible things just happened.");
}

@OnActivityResult(value = 4, resultCode = Activity.RESULT_CANCELED)
public void onDialogCancel(Intent data) {
    Log.d("Velo", "Canceled");
}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Velodrome.OnActivityResul(this, requestCode, resultCode, data);
}
```

### Install
```gradle
dependencies {
    compile 'com.levelmoney.velodrome:velodrome:0.9.1-SNAPSHOT@aar'
}
```
