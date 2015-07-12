# RubberIndicator

A rubber indicator for ViewPager

<img src="https://d13yacurqjgara.cloudfront.net/users/303234/screenshots/2090803/pageindicator.gif" width="400px" height="300px" />

* Designed by [Valentyn Khenkin](https://dribbble.com/shots/2090803-Rubber-Indicator?list=searches&tag=indicator&offset=7)
* [Here](http://codepen.io/machycek/full/eNvyjb/) is the CSS version

## Usage

### Introduction

APIs offered by **RubberIndicator**.

|APIs | Usage|
|---|---|
|setCount(int count)|Set the count of indicators|
|setCount(int count, int focusPos)|Set the count and specify the focusing indicator|
|setFocusPosition(int pos)|Set focusing indicator|
|getFocusPosition()|Get focusing indicator|
|moveToLeft()|Move the focusing indicator to left|
|moveToRight()|Move the focusing indicator to right|

In addition to the APIs listed in the table, **RubberIndicator** also provides a callback listener - **OnMoveListener** for the user should be notified when the moving animator finished.

```java
public interface OnMoveListener {
	void onMovedToLeft();
	void onMovedToRight();
}
```

### How to use

checkout to [sample](sample/src/main/java/com/liangfeizc/rubberindicator/MainActivity.java) to see how to use `RubberIndicator` with a `GestureDetector`.

### Attributes

> comming soon

### package

> coming soon

## Me

* [weibo](http://weibo.com/liangfeizc)
* [twitter](https://twitter.com/JpRyouhi)

## License

    The MIT License (MIT)
    
    Copyright (c) 2015 liangfei
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.

