# Android Book 通讯录


### 功能介绍：

1、支持自定义ViewHolder来实现多种分组

2、支持粘性分组

#### 使用方法：

1、在root目录的build.gradle目录中添加
```
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```


2、在项目的build.gradle中添加依赖
```
    dependencies {
        implementation 'com.github.wshychbydh:book:1.0.0'
    }
```

**注**：如果编译的时候报重复的'META-INF/app_release.kotlin_module'时，在app的build.gradle文件的android下添加
```
    packagingOptions {
        exclude 'META-INF/app_release.kotlin_module'
    }
```
报其他类似的重复错误时，添加方式同上。

4、构建分组数据，所有需要分组的数据模型都需实现IQuickProvider接口，实例：
```
    var DEFAULTS = arrayOf("热", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
      "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
      
    private fun mockBookData() : LinkedHashMap<String, List<IQuickProvider>>{
        val data = LinkedHashMap<String, List<IQuickProvider>>()
        val size = DEFAULTS.size
        val random = Random()
        for (i in 0 until size) {
          val key = DEFAULTS[i] //分组关键字
    
          if (i == 0) { //模拟分组顶部数据
            data[key] = arrayListOf(Hot(key, "热"))
            continue
          }
    
          //模拟分组数据
          val count = random.nextInt(10) + 1
          val content = ArrayList<Address>()
          (0 until count).forEach {
            content.add(Address(key, "第 ${it + 1}条数据，我属于$key分组"))
          }
          data[key] = content  //关联分组和数据
        }
        return data
    } 
      
    以DEFAULTS分组方式为例，定义数据模型：
    
    data class Hot(
        private val key: String,  //该数据所在分组，如"热"
        ... 
    ) : IQuickProvider {
      override fun getKey(): String {
        return key
      }
    }
    
    data class Book(
        private val key: String,  //该数据所在分组，如"A","B"...
        ... 
    ) : IQuickProvider {
      override fun getKey(): String {
        return key
      }
    }
```

5、定义RecyclerView的ViewHolder，ViewHolder的使用方式参考(https://github.com/wshychbydh/recyclerAdapter)
```
  @LayoutId(R.layout.hot_item)
  class HotViewHolder(itemView: View) : DataViewHolder<Hot>(itemView) {
    private var hotItemTv: TextView = itemView.findViewById(R.id.hot_item_tv)

    override fun updateViewByData(data: Hot) {
      super.updateViewByData(data)
      hotItemTv.text = data.content //显示顶部hot数据
    }
  }  

  @LayoutId(R.layout.book_item)
  class BookViewHolder(itemView: View) : DataViewHolder<Book>(itemView) {
    private var bookItemTv: TextView = itemView.findViewById(R.id.book_item_tv)

    override fun updateViewByData(data: Book) {
      super.updateViewByData(data)
      bookItemTv.text = data.content //显示每一栏的数据
    }
  }
```

6、添加QuickView到布局文件中

```
  <com.cool.eye.func.address.QuickView
    android:id="@+id/quickView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

7、构建QuickView的参数并显示数据

```
    //设置数据和显示的ViewHolder
    val dataParams = QuickDataParams.Builder()
        .setData(mockBookData())     //（必填）设置数据
        .registerViewHolder(Hot::class.java, HotViewHolder::class.java)  （选填）
        .registerViewHolder(Book::class.java, BookViewHolder::class.java) （必填）
        .build()
    
    //右侧导航栏的样式    
    val barParams = BarParams.Builder()
        .setMinWidth()              
        .setBackgroundDrawable()
        .setPressedBackgroundDrawable()
        .setIndicatorColor()
        .setIndicatorRadius()
        .setLetterBackgroundColors()
        .setLetterSelectedTextColor()
        .setLetterSpace()
        .setLetterTextColor()
        .setLetterTextSize()
        .setOnLetterSelectedListener()
        .setToastDuration()
        .setToastView()
        .setToastViewDismissAnim()
        .build()    
     
    //粘性头部样式 
    val stickyParams = QuickStickyParams.Builder()
        .setTitleHeight()
        .setDivider()
        .isShowStickyHeader()
        .isShowFirstGroup()
        .setBackgroundColor()
        .setTextColor()
        .setTextPaddingLeft()
        .setTextSize()
        .build()  
    
    //快速选择分组后的toast提示样式
    val toastParams = QuickToastParams.Builder()
        .setToastViewWidth()
        .setToastViewHeight()
        .setToastTextSize()
        .setToastTextColor()
        .setToastBackgroundColor()
        .setToastBackgroundDrawable()
        .build()          

    val quickParams = QuickViewParams.Builder()
        .setDataParams(dataParams)
        .setQuickBarParams(barParams)
        .setQuickStickyParams(stickyParams)
        .setToastParams(toastParams)
        .build()
    
    quickView.setup(quickParams)
```
    
#### 联系方式 wshychbydh@gmail.com

[![](https://jitpack.io/v/wshychbydh/book.svg)](https://jitpack.io/#wshychbydh/book)
