# Android Book 通讯录


### 功能介绍：

1、支持自定义ViewHolder来实现多种分组

2、支持粘性分组

#### 使用方法：

1、在root目录的build.gradle目录中添加

```groovy
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }
```

2、在项目的build.gradle中添加依赖

```groovy
    dependencies {
        implementation 'com.github.wshychbydh:book:TAG'
    }
```

**注**：如果编译的时候报重复的'META-INF/app_release.kotlin_module'时，在app的build.gradle文件的android下添加

```groovy
    packagingOptions {
        exclude 'META-INF/app_release.kotlin_module'
    }
```
报其他类似的重复错误时，添加方式同上。

4、构建分组数据，所有需要分组的数据模型都需实现[IQuickProvider](./app/src/main/java/com/eye/cool/book/support/IQuickProvider.kt)接口。实例：

```kotlin
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
      
    //以DEFAULTS分组方式为例，定义数据模型：
    
    data class Hot(
        private val key: String,  //该数据所在分组，如"热"
        val content: String   //用于显示的内容（自定义）
    ) : IQuickProvider {
      override fun getKey(): String {
        return key
      }
      
      override fun getSearchKey(): String {
        return content   //用于模糊匹配搜素的内容（自定义）
      }
    }
    
    data class Book(
        private val key: String,  //该数据所在分组，如"A","B"...
        val content: String   //用于显示的内容（自定义）
    ) : IQuickProvider {
      override fun getKey(): String {
        return key
      }
      
      override fun getSearchKey(): String {
        return content   //用于模糊匹配搜素的内容（自定义）
      }
    }
```

5、定义RecyclerView的ViewHolder，继承[DataViewHolder](./app/src/main/java/com/eye/cool/book/adapter/DataViewHolder.kt)

```kotlin
  @LayoutId(R.layout.hot_item) //或 @LayoutName("hot_item")
  class HotViewHolder(itemView: View) : DataViewHolder<Hot>(itemView) {
    private var hotItemTv: TextView = itemView.findViewById(R.id.hot_item_tv)

    override fun updateViewByData(data: Hot) {
      super.updateViewByData(data)
      hotItemTv.text = data.content //显示顶部hot数据
    }
  }  

  @LayoutId(R.layout.book_item) //或 @LayoutName("book_item")
  class BookViewHolder(itemView: View) : DataViewHolder<Book>(itemView) {
    private var bookItemTv: TextView = itemView.findViewById(R.id.book_item_tv)

    override fun updateViewByData(data: Book) {
      super.updateViewByData(data)
      bookItemTv.text = data.content //显示每一栏的数据
    }
  }
```

6、添加[QuickView](./app/src/main/java/com/eye/cool/book/view/QuickView.kt)到布局文件中

```xml
  <com.eye.cool.book.view.QuickView
    android:id="@+id/quickView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

7、构建QuickView的参数并显示数据

```kotlin
    //设置数据和显示的ViewHolder
    //kotlin调用：val dataParams = QuickDataParams.build{...} (推荐)
    val dataParams = QuickDataParams.Builder()
        .data(mockBookData())     //（必填）设置数据
        .viewHolders()            //直接指定ViewHolders
        .registerViewHolder(Hot::class.java, HotViewHolder::class.java)   //逐个添加
        .registerViewHolder(Book::class.java, BookViewHolder::class.java)  
        .build()
    
    //右侧导航栏的样式 (可选)   
    //kotlin调用：val barParams = QuickBarParams.build{...} (推荐)
    val barParams = QuickBarParams.Builder()
        .minWidth()              
        .backgroundDrawable()
        .pressedBackgroundDrawable()
        .indicatorColor()
        .indicatorRadius()
        .letterBackgroundColors()
        .letterSelectedTextColor()
        .letterSpace()
        .textColor()
        .textSize()
        .onLetterSelectedListener()
        .toastDuration()
        .toastView()
        .toastViewDismissAnim()
        .build()    
     
    //粘性头部样式 (可选)
    //kotlin调用：val stickyParams = QuickStickyParams.build{...} (推荐)
    val stickyParams = QuickStickyParams.Builder()
        .titleHeight()       //分组标题的高度
        .divider()           //RecyclerView分割线
        .showStickyHeader()  //是否显示粘性分组，默认true
        .showFirstGroup()    //是否选择第一个分组，默认false
        .backgroundColor()
        .textColor()
        .textPaddingLeft()
        .textSize()
        .build()  
    
    //快速选择分组后的toast提示样式 (可选)
    //kotlin调用：val toastParams = QuickToastParams.build{...} (推荐)
    val toastParams = QuickToastParams.Builder()
        .viewWidth()
        .viewHeight()
        .textSize()
        .textColor()
        .backgroundColor()
        .backgroundDrawable()
        .build()    
      
    //kotlin调用：val quickParams = QuickToastParams.build(dataParams){...} (推荐)
    val quickParams = QuickViewParams.Builder(dataParams)
        .quickBarParams(barParams)
        .quickStickyParams(stickyParams)
        .toastParams(toastParams)
        .build()
    
    quickView.setup(quickParams)
```

8、搜索，默认是在调用线程中执行 
```kotlin
    quickView.search(key)  //返回搜索结果
```
    

#####   
 
**Demo地址：(https://github.com/wshychbydh/SampleDemo)**    
    
##

###### **欢迎fork，更希望你能贡献commit.** (*￣︶￣)    

###### 联系方式 wshychbydh@gmail.com

[![](https://jitpack.io/v/wshychbydh/book.svg)](https://jitpack.io/#wshychbydh/book)
