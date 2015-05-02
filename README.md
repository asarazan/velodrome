# Velodrome
An experimental Android library that attempts to simplify the `onActivityResult` flow.

### Thesis
One of the more horrible pieces of the Android lifecycle occurs inside `onActivityResult`. It inevitably descends into a mess of switch statements and spaghetti.

A more reasonable approach is to encapsulate each piece of callback behavior into its own object. That is [Velodrome](http://en.wikipedia.org/wiki/Velodrome).

### Usage

```java
// In your fragment or activity class.
@ResultHandler(1234)
private Velo mSomeHandler = new Velo() {
    @Override
    public boolean handleResult(int resultCode, Intent data) {
        // Do something here.
        return true;
    }
}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Velodrome.handleResult(this, requestCode, resultCode, data);
}
```

### Install
We have started using [Jitpack](http://jitpack.io) for all of our open source libraries, so add the following to your app's build.gradle file:

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
