# Velodrome
A small Android library that attempts to simplify the `onActivityResult` flow.

### Thesis
One of the more horrible pieces of the Android lifecycle occurs inside `onActivityResult`. It inevitably descends into a mess of switch statements and spaghetti.

A more reasonable approach is to encapsulate each piece of callback behavior into its own object. That is precisely what [Velodrome](http://en.wikipedia.org/wiki/Velodrome) does.

### Java Usage

```java
// In your fragment or activity class.
@ResultHandler
private ResultHandler mSomeHandler = new BasicResultHandler(0) {
    @Override
    public void handleResult(Intent data) {
        // Do something here.
    }
}

@ResultHandler
private ResultHandler mAnotherHandler = new BasicResultHandler(1) {
    @Override
    public void handleResult(Intent data) {
        // Do something else here.
    }
}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Velodrome.handleResult(this, requestCode, resultCode, data);
}
```

### Kotlin Usage
```kotlin
// Inside a fragment or activity class.
[ResultHandler]
val aLauncher = activityLauncher(3, javaClass<SomeActivity>()) {
    Log.d(TAG, it.getStringExtra("result")
}

[ResultHandler]
val handler = resultHandler(SOME_CODE) {
    Log.d(TAG, it.getStringExtra("result")
}
```

### Install
```gradle
dependencies {
    compile 'com.levelmoney.velodrome:velodrome:1.0'
    // OR
    compile 'com.levelmoney:velodrome:velodrome-kotlin:1.0'
}
```
