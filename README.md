# Velodrome
A small Android library that attempts to simplify the `onActivityResult` flow.

### Thesis
One of the more horrible pieces of the Android lifecycle occurs inside `onActivityResult`. It inevitably descends into a mess of switch statements and spaghetti.

A more reasonable approach is to encapsulate each piece of callback behavior into its own object. That is precisely what [Velodrome](http://en.wikipedia.org/wiki/Velodrome) does.

### Java Usage

```java
// In your fragment or activity class.
@HandleResult
private ResultHandler mSomeHandler = new BasicResultHandler(0) {
    @Override
    public void handleResult(Intent data) {
        // Do something here.
    }
}

@HandleResult
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
[HandleResult]
val aLauncher = activityLauncher(2, javaClass<SomeActivity>()) {
    Log.d(TAG, it.getStringExtra("result")
}

[HandleResult]
val handler = resultHandler(3) {
    Log.d(TAG, it.getStringExtra("result")
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    Velodrome.handleResult(requestCode, resultCode, data)
}
```

### Install
```gradle
dependencies {
    compile 'com.levelmoney.velodrome:velodrome:1.0@aar'
    // OR
    compile 'com.levelmoney:velodrome:velodrome-kotlin:1.0@aar'
}
```
