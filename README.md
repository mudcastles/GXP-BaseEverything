# GXP-BaseEverything [![](https://www.jitpack.io/v/com.gitee.mudcastles/gxp-base-everything.svg)](https://www.jitpack.io/#com.gitee.mudcastles/gxp-base-everything)
基础组件库，包含了MVP、MVVM框架的基础Activity和Fragment，支持沉浸式、双击back键退出应用、侧滑结束Activity、Fragment的back键监听等，总结了项目中用到的所有的自定义View和ViewGroup，总结了一些工具类。后续会不断完善和改进。

# Install
1. 在Project的build.gradle中添加代码：
```groovy
allprojects {
    repositories {
        maven { url 'https://www.jitpack.io' }
    }
}
```
2. 添加依赖
```groovy
dependencies {
    //其他代码
    implementation 'com.gitee.mudcastles:gxp-base-everything:Tag'
}
```
或
```groovy
dependencies {
    //其他代码
    implementation 'com.github.mudcastles:GXP-BaseEverything:Tag'
}
```
请使用最新版本！！！

# 用到且已经依赖的库
1. EventBus：org.greenrobot:eventbus:3.1.1
2. 沉浸式：com.gyf.immersionbar:immersionbar:3.0.0
             com.gyf.immersionbar:immersionbar-components:3.0.0
             com.gyf.immersionbar:immersionbar-ktx:3.0.0
3. 权限：com.github.getActivity:XXPermissions:11.6
4. RxJava3：io.reactivex.rxjava3:rxjava:3.0.6

# 可能用到且需要自己添加依赖的库
1. 进度条：com.daimajia.numberprogressbar:library:1.4@aar
2. 动画：com.nineoldandroids:library:2.4.0

# 使用说明
基库支持MVP和MVVM两种框架，由于MVC框架太过落后且无需封装，因此并未添加MVC的封装。
## 1. 使用MVP框架
如果你想使用MVP框架中的Activity或者Fragment，你需要继承 `com.peng.gxpbaseeverything.view.activity.mvp` 包下的基类。所有基类都默认集成了侧滑退出界面的功能，你可以通过 `setSwipeBackEnable(Boolean)` 来启用或者禁用此功能。

GXPBaseActivity：无标题栏，内容部分占据全屏。<br>
GXPToolbarActivity：带标题栏，内容部分在占据出标题栏外的所有空间。<br>
GXPDrawerActivity：带侧滑菜单无标题栏，内容部分占据全屏，侧滑菜单自定义。<br>
GXPToolbarDrawerActivity：带标题栏带侧滑菜单，内容部分在占据出标题栏外的所有空间，侧滑菜单自定义。

## 2. 使用MVVM框架
如果你想使用MVVM框架中的Activity或者Fragment，你需要继承 `com.peng.gxpbaseeverything.view.activity.mvvm` 包下的基类。所有基类都默认集成了侧滑退出界面的功能，你可以通过 `setSwipeBackEnable(Boolean)` 来启用或者禁用此功能。
泛型类型是你的ViewBinding，你不必自己构造ViewBinding对象，基类中会帮你构造好，你只需要在对应的回调方法中使用即可。

GXPBaseActivity：无标题栏，内容部分占据全屏。<br>
GXPToolbarActivity：带标题栏，内容部分在占据出标题栏外的所有空间。<br>
GXPDrawerActivity：带侧滑菜单无标题栏，内容部分占据全屏，侧滑菜单自定义。<br>
GXPToolbarDrawerActivity：带标题栏带侧滑菜单，内容部分在占据出标题栏外的所有空间，侧滑菜单自定义。

如果你想要跨Activity共享ViewModel，你可以继承 `com.peng.gxpbaseeverything.view.GXPBaseApplication`，其内部构造了一个`appViewModelStore`对象，你可以使用此对象来存放ViewModel，这样你就能在不同的页面使用同一个ViewModel了。

## 3. 库中部分功能介绍
1. CrashHandler：异常捕获器，用于在发生未捕获的异常时把异常崩溃日志写入到本地。<br>注意： <font color="red">如运行环境未使用分区存储，该功能需要文件写入权限，请手动申请该权限，日志存储位置为“根目录->Crash->log”。在启用分区存储的设备上无需申请权限，日志存储位置为“Documents->Crash->log”。</font><br><br>
	使用时需要在Application的onCreate方法中添加以下代码：
	
	```Java
	CrashHandler.getInstance().init(getApplicationContext());
	```