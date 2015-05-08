# Velodrome
A small Android library that attempts to simplify the `onActivityResult` flow.

### Thesis
One of the more horrible pieces of the Android lifecycle occurs inside `onActivityResult`. It inevitably descends into a mess of switch statements and spaghetti.

A more reasonable approach is to encapsulate each piece of callback behavior into its own object. That is precisely what [Velodrome](http://en.wikipedia.org/wiki/Velodrome) does.

### Java Usage

```java
// In your fragment or activity class.
@ResultHandler
private ResultHandler mSomeHandler = new Velo() {

    @Override
    public int requestCode() {
        return 0;
    }
    
    @Override
    public void handleResult(int resultCode, Intent data) {
        // Do something here.
    }
}

@ResultHandler
private ResultHandler mAnotherHandler = new Velo() {

    @Override
    public int requestCode() {
        return 1;
    }
    
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
[Result]
val aLauncher = activityLauncher(SOME_CODE, javaClass<SomeActivity>()) {
    Log.d(TAG, it.getStringExtra("result")
}
```

### Install
The Android team has started using [Jitpack](http://jitpack.io) for all of our open source libraries, so add the following to your app's build.gradle file:

```groovy
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
  compile 'com.github.Levelmoney:Velodrome:0.7' // or whatever the latest version is.
}
```

### Known Issues
* Does not currently operate as a compilation processor. Pull requests welcome.
* Temporarily calls `setAccessible(true)` on fields during onHandle. Would be unnecessary at compile-time.
