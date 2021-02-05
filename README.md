# GXP-BaseEverything
基础组件库，包含了MVP、MVVM框架的基础Activity和Fragment，支持沉浸式、双击back键退出应用、侧滑结束Activity、Fragment的back键监听等，总结了项目中用到的所有的自定义View和ViewGroup，总结了一些工具类。后续会不断完善和改进。

# Install
1. 在Project的build.gradle中添加代码：
```groovy
allprojects {
    repositories {
        //其他代码
        maven {url 'https://dl.bintray.com/mudcastles/maven'}
    }
}
```
2. 添加依赖
```groovy
dependencies {
    //其他代码
    implementation 'com.peng.gxpbaseeverything:GXPBaseEverything:2.0.3'
}
```

# 使用说明
基库支持MVP和MVVM两种框架，由于MVC框架太过落后且无需封装，因此并未添加MVC的封装。
## 使用MVP框架
如果你想使用MVP框架中的Activity或者Fragment，你需要继承 `com.peng.gxpbaseeverything.view.activity.mvp` 包下的基类。所有基类都默认集成了侧滑退出界面的功能，你可以通过 `setSwipeBackEnable(Boolean)` 来启用或者禁用此功能。

GXPBaseActivity：无标题栏，内容部分占据全屏。<br>
GXPToolbarActivity：带标题栏，内容部分在占据出标题栏外的所有空间。<br>
GXPDrawerActivity：带侧滑菜单无标题栏，内容部分占据全屏，侧滑菜单自定义。<br>
GXPToolbarDrawerActivity：带标题栏带侧滑菜单，内容部分在占据出标题栏外的所有空间，侧滑菜单自定义。

## 使用MVVM框架
如果你想使用MVVM框架中的Activity或者Fragment，你需要继承 `com.peng.gxpbaseeverything.view.activity.mvvm` 包下的基类。所有基类都默认集成了侧滑退出界面的功能，你可以通过 `setSwipeBackEnable(Boolean)` 来启用或者禁用此功能。
泛型类型是你的ViewBinding，你不必自己构造ViewBinding对象，基类中会帮你构造好，你只需要在对应的回调方法中使用即可。

GXPBaseActivity：无标题栏，内容部分占据全屏。<br>
GXPToolbarActivity：带标题栏，内容部分在占据出标题栏外的所有空间。<br>
GXPDrawerActivity：带侧滑菜单无标题栏，内容部分占据全屏，侧滑菜单自定义。<br>
GXPToolbarDrawerActivity：带标题栏带侧滑菜单，内容部分在占据出标题栏外的所有空间，侧滑菜单自定义。

如果你想要跨Activity共享ViewModel，你可以继承 `com.peng.gxpbaseeverything.view.GXPBaseApplication`，其内部构造了一个`appViewModelStore`对象，你可以使用此对象来存放ViewModel，这样你就能在不同的页面使用同一个ViewModel了。