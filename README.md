#### 一个高性能无锯齿的圆角布局组件，包含了常见Layout的圆角实现

- RoundConstraintLayout
- RoundFrameLayout
- RoundLinearLayout
- RoundRecyclerView
- RoundRelativeLayout

有四种可选实现方式
- RoundViewBitmapShaderImpl
  使用BitmapShader创建圆角布局,通过添加透明边框来实现。在复杂布局的情况下可能会显示异常.实测在积目发现卡牌场景下显示异常
  
- RoundViewClipPathImpl
  通过裁剪画板来实现圆角布局,锯齿比较明显,性能较高。某些手机上可能会有系统原因导致的bug
  
- RoundViewOutlineImpl
  使用Outline的方式实现圆角,性能较高.锯齿明显.无法单独设置某个圆角
  
- RoundViewXfermodeImpl（默认选项）
  通过使用Xfermode混合模式来实现圆角布局,通过添加一个透明边框解决锯齿严重的问题，效果最佳

``` xml
<declare-styleable name="RoundView">
        <attr name="radius" format="string" />
        <attr name="radius_dimension" format="dimension" />
        <attr name="bottomLeftRadius" format="dimension" />
        <attr name="bottomRightRadius" format="dimension" />
        <attr name="topRightRadius" format="dimension" />
        <attr name="topLeftRadius" format="dimension" />
        <attr name="transparent_border_width" format="dimension" />
        <attr name="implementation_type" format="enum">
            <enum name="outline" value="0" />
            <enum name="xfermode" value="1" />
            <enum name="clipPath" value="2" />
            <enum name="bitmapShader" value="3" />
        </attr>
    </declare-styleable>
```

#### 添加依赖

``` gradle
repositories {
  maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'com.github.erleizh:RoundLayout:1.0.0'
}
```
