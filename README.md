# RubberIndicator

[![Join the chat at https://gitter.im/LyndonChin/AndroidRubberIndicator](https://badges.gitter.im/LyndonChin/AndroidRubberIndicator.svg)](https://gitter.im/LyndonChin/AndroidRubberIndicator?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

<img src="https://cdn.nlark.com/yuque/0/2019/png/124977/1559045910714-8948c8b2-2b86-44a3-a600-a4415db3c01f.png" width=400px />

A rubber indicator for ViewPager

<img src="https://cdn.dribbble.com/users/303234/screenshots/2090803/pageindicator.gif" width="400px" height="300px" />

* Designed by [Valentyn Khenkin](https://dribbble.com/shots/2090803-Rubber-Indicator?list=searches&tag=indicator&offset=7)
* [Here](http://codepen.io/machycek/full/eNvyjb/) is the CSS version

## Usage 

The attributes for `RubberIndicator` are not yet finished.
A toy example is provided in [sample](sample/src/main/java/com/liangfeizc/rubberindicator/MainActivity.java).

## Introduction

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

## Me

* [weibo](http://weibo.com/liangfeizc)
* [twitter](https://twitter.com/JpRyouhi)

## License

    MIT
