&#160; &#160; &#160; &#160;在Android原有ListView控件基础之上打造一个类似于表格形式，全方位滚动（既可以上下滚动又可以左右滚动）的UDLRSlideListView控件。

一. 实现目标

- 为了扩展方便重写ListView，ListView大部分的特性我们还继续保留着，关键时候有大用处。
- ListView的行可以左右滑动，因为手机的屏幕比较小，咱们经常显示表格的时候不能保证一个屏幕全部显示完成。
- 在行可以左右滑动的基础上，咱还可以动态设置固定每一行前面多少列是不会滑动（把每一行分成左右两个部分，左边的一部分不能滑动，右边的一部可以滑动）。
- 可以设置标题，并且UDLRSlideListView上下滑动的时候标题可以一直固定在顶部（可以动态设置是否固定）。
- 下拉刷新，上拉加载功能。

二. 效果展示

![这里写图片描述](http://img.blog.csdn.net/20170815113035031?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd3V5dXhpbmcyNA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
