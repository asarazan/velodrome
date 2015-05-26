# Velodrome
A small Android library that attempts to simplify the `onActivityResult` flow.

### Thesis
One of the more horrible pieces of the Android lifecycle occurs inside `onActivityResult`. It inevitably descends into a mess of switch statements and spaghetti.

A more reasonable approach is to encapsulate each piece of callback behavior into its own handler method. That is precisely what [Velodrome](http://en.wikipedia.org/wiki/Velodrome) does.

### Java Usage

```java
// In your fragment or activity class.

@HandleResult(0)
public void onActivityReturn(Intent data) {
    Log.d("Velo", data.getStringExtra("text"));
}

@HandleResults({2, 3})
public void onMultipleCodes(Intent data) {
    Log.d("Velo", "one of two possible things just happened.");
}

@HandleResult(value = 1, resultCode = Activity.RESULT_CANCELED)
public void onDialogCancel(Intent data) {
    Log.d("Velo", "Canceled");
}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Velodrome.handleResult(this, requestCode, resultCode, data);
}
```

### Install
```gradle
dependencies {
    compile 'com.levelmoney.velodrome:velodrome:0.9-SNAPSHOT@aar'
}
```
