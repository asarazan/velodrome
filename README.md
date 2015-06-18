# Velodrome
A small Android library that attempts to simplify the `onActivityResult` flow.

### Thesis
One of the more horrible pieces of the Android lifecycle occurs inside `onActivityResult`. It inevitably descends into a mess of switch statements and spaghetti.

A more reasonable approach is to encapsulate each piece of callback behavior into its own handler method. That is precisely what [Velodrome](http://en.wikipedia.org/wiki/Velodrome) does.

### Usage

```java
public static final int REQUEST_DELETE = 0;
public static final int REQUEST_TEXT_ENTRY = 1;

@OnActivityResult(REQUEST_DELETE)
public void onConfirmDelete() {
	// Burn the world.
}

@OnActivityResult(REQUEST_TEXT_ENTRY)
public void onTextResult(@Arg("text") String text) {
	// 'text' is automatically extracted from the data Intent.
}

@OnActivityResult(value=REQUEST_TEXT_ENTRY, resultCode=Activity.RESULT_CANCELED)
public void onTextAborted(Intent data) {
	// Velodrome only fires on RESULT_OK by default. 
	// You can override that behavior in the annotation args. 
	// 'data' is the original data Intent passed to onActivityResult.
}

@OnActivityResult({100, 101})
public void onOther() {
    // You can even declare a single handler for multiple request codes, if that's your thing.
}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    Velodrome.OnActivityResult(this, requestCode, resultCode, data);
}
```

### Performance
Velodrome uses reflection to resolve handler methods. This means it isn't blazing fast.
That being said, we don't believe that the performance hit is substantial enough to warrant
the pain of dealing with annotation processing.

However, we wouldn't be sad if some intrepid soul were to submit a [pull request](https://github.com/Levelmoney/velodrome/pulls)!

### Install
```gradle
dependencies {
    compile 'com.levelmoney.velodrome:velodrome:0.9.2@aar'
}
```
