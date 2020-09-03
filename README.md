# WorkFiveWeek

## 应用安全

### MD5

MD5即Message-Digest Algorithm 5（信息-摘要算法5），用于确保信息传输完整一致，是计算机广泛使用的杂凑算法之一（又译摘要算法、哈希算法）。MD5算法将数据（如汉字）运算为另一固定长度值，是杂凑算法的基础原理，MD5的前身有MD2、MD3和MD4。

MD5特点

- 压缩性：任意长度的数据，算出的MD5值长度都是固定的
- 容易计算：从原数据计算出MD5值很容易。
- 抗修改性：对原数据进行任何改动，哪怕只修改1个字节，所得到的MD5值都有 很大区别。
- 强抗碰撞：已知原数据和其MD5值，想找到一个具有相同MD5值的数据（即伪造 数据）是非常困难的。

应用场景：

当用户登录的时候，系统把用户输入的密码计算成MD5值，然后再去和保存在文件系统中的MD5值进行比较，进而确定输入的密码是否正确。通过这样的步骤，系统在并不知道用户密码的明码的情况下就可以确定用户登录系统的合法性。这不但可以避免用户的密码被具有系统管理员权限的用户知道，而且还在一定程度上增加了密码被破解的难度。
MD5加密算法是不可逆的，因此不可能在客户端与服务器交互时，使用MD5对传输的Json串进行加密，因为用md5加密过后，服务器端或者客户端将无法解析出正确的数据并执行相应的逻辑。

### AES

应用场景：

我们在不同的网站里使用不同账号密码，很难记住，想做个app 统一管理，但是账号密码保存在手机里，一旦丢失了容易造成安全隐患，所以需要一种加密算法，将账号密码信息加密起来保管，这时候如果使用对称加密算法，将数据进行加密，秘钥我们自己记在心里，只需要记住一个密码。需要的时候可以还原信息。

android 里需要把一些敏感数据保存到SharedPrefrence 里的时候，也可以使用对称加密，这样可以在需要的时候还原。
请求网络接口的时候，我们需要上传一些敏感数据，同样也可以使用对称加密，服务端使用同样的算法就可以解密。或者服务端需要给客户端传递数据，同样也可以先加密，然后客户端使用同样算法解密。

### RSA

RSA是一种常用的非对称加密算法，所谓非对称加密是指使用一对密钥（公钥和私钥）进行加密和解密，公钥人人都可以获得，用于加密数据，私钥保存在服务器中，用于解密数据。加密解密过程如下：

应用场景：

使用RSA进行加密解密，其优点是非常不容易破解，缺点是和对称加密（如AES）相比，加密速度较慢。由于加解密花费时间较长，速度慢，只适合对少量数据进行加密（运用场景例如：登陆、修改密码等）；秘钥长度越长，安全性能越好，但是解析时长也会随之下降;

### 非对称加密算法+对称加密算法的组合使用场景

对称和非对称加密为什么要组合应用，主要因为：
1、对称加密加解密用同一个密钥，速度快，但安全欠缺。
2、非对称加密使用不同的密钥进行加解密，速度慢（被加解密内容越大越明显），但是更安全。

客户端要与服务器端通信的时候，客户端先使用 RSA 算法生成自己的公钥和私钥，然后把公钥以明文的方式发送给服务器，服务器拿到公钥，并使用从客户端接收到的公钥对 AES 的秘钥进行加密然后返回给客户端。客户端接收到加密后的 AES 秘钥后就可以直接使用自己的私钥解密 AES 的秘钥，从而得到 AES 秘钥明文。之后两者的通信就可以使用 AES来实现敏感信息的加密了。这个操作就即解决了 AES 秘钥的传输问题，又解决了 RSA 算法效率不高的问题。

### v1及v2适配

v1签名是对jar进行签名，V2签名是对整个apk签名：官方介绍就是：v2签名是在整个APK文件的二进制内容上计算和验证的，v1是在归档文件中解压缩文件内容。

二者签名所产生的结果： 
v1：在v1中只对未压缩的文件内容进行了验证，所以在APK签名之后可以进行很多修改——文件可以移动，甚至可以重新压缩。即可以对签名后的文件在进行处理 
v2：v2签名验证了归档中的所有字节，而不是单独的ZIP条目，如果在构建过程中有任何定制任务，包括篡改或处理APK文件，确保禁用它们，否则可能会使v2签名失效，从而使APKs与Android 7.0和以上版本不兼容。

一个APK可以同时由v1和v2签名同时签署，所以它仍然可以向后兼容以前的Android版本。

根据实际开发的经验总结：

 一定可行的方案： 只使用 v1 方案，
不一定可行的方案：同时使用 v1 和 v2 方案，
对 7.0 以下一定不行的方案：只使用 v2 方案。

**解决方案一**
v1和v2的签名使用
只勾选v1签名并不会影响什么，但是在7.0上不会使用更安全的验证方式
只勾选V2签名7.0以下会直接安装完显示未安装，7.0以上则使用了V2的方式验证
同时勾选V1和V2则所有机型都没问题

**解决方案二**
在app的build.gradle的android标签下加入如下

```java
signingConfigs {
    debug {
        v1SigningEnabled true
        v2SigningEnabled true
    }
    release {
        v1SigningEnabled true
        v2SigningEnabled true
    }
}
```

### 多渠道打包

#### 多渠道打包的用途

- 用途一：统计不同渠道的数据，方便在推广运营过程中进行有效的渠道比较。来评估渠道的质量。
- 用途二：对于APP内部有分享邀请好友送奖励的活动，需要进行渠道标识，免除用户手动输入邀请码的流程。
   简化用户注册步骤，减少因注册步骤太繁琐而导致的用户流失。
- 用途三：部分游戏要进行分渠道的推广，需要进行分包统计。
- 分包的方式：
  - 客户端打多个渠道包：以下方式都使用
  - 服务器根据需要动态生成渠道包：使用较多的为美团和360的方式

#### 多渠道打包的6种方式

- 1、原始多渠道打包
  - 直接在清单文件中定义meta标签设置渠道值，读取对应值当做渠道id；
  - 或者直接将渠道写入java代码中
  - 优缺点：操作简单，但是当渠道数量过多时消耗时间过长，需要多次修改id值。

- 2、通过将渠道信息写入assets中进行渠道标识

- 3、友盟多渠道打包
  - 原理：清单文件添加渠道标签读取对应值。
  - 打包后修改渠道值的两种方法
  - 第一种方法：
     通过ApkTool进行解包，然后修改AndroidManifest中修改渠道标示，最后再通过ApkTool进行打包、签名。
  - 第二种方法：
     使用AXML解析器axmleditor.jar，拥有很弱的编辑功能，工程中用来编辑二进制格式的 AndroidManifest.xml 文件.

- 4、美团多渠道打包
  - 1、直接将apk文件解压缩，然后在META-INF中间中添加以渠道名命名的空文件。
  - 2、代码中读取该文件名作为渠道名。
  - 该种方式不需要重新对apk进行签名，操作简单，也可将添加渠道文件的步骤做成Python脚本。实现自动化添加
  - 优缺点：需要将apk解压缩，如果APK包较大则解压缩所需时间较长。
  - 使用遇到的问题：使用爱加密加密过后，再按此种方式进行渠道包的添加，会导致在7.0及以上手机上无法安装
    - 解决办法：先分渠道打多个包，然后提交爱加密进行加密。

- 5、360多渠道打包
  - 利用的是Zip文件“可以添加comment（摘要）”的数据结构特点，在文件的末尾写入任意数据，而不用重新解压zip文件
  - apk文件就是zip文件格式；
  - 不需要对apk文件解压缩和重新签名即可完成多渠道自动打包，高效速度快，无兼容性问题

- 6、Gradle和Maven多渠道打包
  - Maven是一个软件项目管理和自动构建工具；
  - 配合使用android-maven-plugin插件，以及maven-resources-plugin插件可以很方便的生成渠道包

### 代码混淆

Android代码混淆是让Android项目避免轻易被逆向分析，防止代码安全泄露的手段之一。它将工程中的Android代码用简单抽象的字母或单词代替原有的代码名称。使代码丧失可读性从而使逆向工程师难以阅读，增加逆向成本。当逆向成本大于逆向收益的时候，逆向代码也就失去意义。 

除此之外，由于代码混淆用简单抽象的单词代替原有长而通俗易懂的代码，因而减少APK的体积。而且，使用代码混淆后，利用Gradle为Android提供的插件，能将项目中未使用的资源安全移除，大大减少APK体积。

在app的build.gradle里，gradle为我们默认配置了如下代码:

```java
buildTypes {
    release {
        minifyEnabled false
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }
}
```

minifyEnabled就是我们开启代码混淆的开关，系统默认为false，既不开启代码混淆。proguardFiles是指定项目中所使用的混淆规则配置文件。其中getDefaultProguardFile(‘proguard-android.txt’)是指定系统默认的混淆规则，而proguard-rules.pro则代表当前项目的混淆规则。 
  proguard-android.txt位于Android SDK目录下/tools/proguard目录下，proguard-rules.pro位于app module下。一般而言，通过修改proguard-rules.pro来改变当前项目的混淆规则。 

一般而言，项目的debug版本无需开启代码混淆，因为代码混淆会额外增加apk的构建时间。但是，用于测试的版本也要与生产版本一样开启代码混淆，以免在生产版本出现测试版本未曾出现的bug。

#### 混淆规则

1.如果使用了Gson之类的工具要使JavaBean类即实体类不被混淆。

2.如果使用了自定义控件那么要保证它们不参与混淆。

3.如果使用了枚举要保证枚举不被混淆。

4.对第三方库中的类不进行混淆

5.混淆时保护引用的第三方jar包

如：-libraryjars libs/baidumapapi_v3_2_0.jar  #保护引用的第三方jar包不被混淆

注意：在使用Eclipse+ADT时需要加入-libraryjars libs/...，如果你是使用Android Studio开发的项目则不需要加入libs包中的jar包，这是因为，通过Android Studio进行混淆代码时，默认已经将 lib目录中的 jar 都已经添加到打包脚本中，所以不需要再次手动添加，否则会出现“ java.io.IOException: The same input jar is specified twice” 错误。

6.混淆时保护第三方jar包中的类不被混淆

如：-keep class com.baidu.** { *; }  #让ProGuard不要警告找不到com.baidu.**这个包里面的类的相关引用

-dontwarn com.baidu.**  #保持com.baidu.**这个包里面的所有类和所有方法不被混淆。

自定义规则

不混淆某个类

```cpp
-keep public class com.biaobiao.example.Test { *; }
```

不混淆某个包所有的类

```cpp
-keep class com.biaobiao.test.** { *; }
}
```

不混淆某个类的子类

```java
-keep public class * extends com.biaobiao.example.Test { *; }
```

不混淆所有类名中包含了“model”的类及其成员

```java
-keep public class * extends com.biaobiao.example.Test { *; }
```

不混淆某个接口的实现

```dart
-keep class * implements com.biaobiao.example.TestInterface { *; }
```

不混淆某个类的构造方法

```kotlin
-keepclassmembers class com.biaobiao.example.Test { 
    public <init>() 
}
```

不混淆某个类的特定的方法

```cpp
-keepclassmembers class com.biaobiao.example.Test { 
    public void test(java.lang.String); 
}
}
```

不混淆某个类的内部类

```cpp
-keep class com.biaobiao.example.Test$* {
        *;
 }
```

#### 混淆语法

##### 使用技巧

如果我们想保持项目中 cn.edu.scujcc.workfiveweek.util. 包下的类都不被混淆，我们可以这样写：

```stylus
-keep class  cn.edu.scujcc.workfiveweek.util.**
```

而 -keep class  xj.progurddemo.been.* 只能保证 People，Test 类不被混淆。Debug 包下的 Debug 类还是会被混淆。

** 和  * 之间的区别就是， ** 包含目录下的所有子目录，而  * 只包含 目录下的直接目录。

假如我们有以下类 Test，

```java
public class Test {

    String name;
    public void  test(){
        
    }
    public abstract String test（String name)
}
```

我们不想 test 类中的 test 方法不被混淆，而 name 字段可以被混淆，我们可以这样配置



```javascript
 -keepclassmembers public class xj.progurddemo.been.Test {
   *** test*(***);
```

*** 表示匹配任意参数，这样 Test 类中的 test 方法都不会被混淆，不管该方法含有什么类型的参数和该方法的返回类型。

##### **keep 关键字**

| 关键字                      | 描述                                                         |
| --------------------------- | ------------------------------------------------------------ |
| keep                        | 保留类和类中的成员，防止被混淆或者移除                       |
| keepnames                   | 保留类和类中的成员，防止被混淆，但是当成员没有被引用时会被移除 |
| keepclassmembers            | 只保留类中的成员，防止他们被混淆或者移除                     |
| keepclassmembersnames       | 只保留类中的成员，防止他们被混淆或者移除，但是当类中的成员没有被引用时还是会被移除 |
| keepclasseswithmembers      | 保留类和类中的成员，前提是指明的类中必须含有该成员           |
| keepclasseswithmembersnames | 不混淆带有指定条件的类和类成员，但未被引用的类和类成员会被移除 |

##### **通配符**

| 关键字     | 描述                                                         |
| ---------- | ------------------------------------------------------------ |
| < filed >  | 匹配类中的所有字段                                           |
| < method > | 匹配类中的所有方法                                           |
| < init >   | 匹配类中的所有构造函数                                       |
| *          | 匹配任意长度字符，但不含包名分隔符(.)。比如说我们的完整类名是com.example.test.MyActivity，使用com.*，或者com.exmaple.*都是无法匹配的，因为*无法匹配包名中的分隔符，正确的匹配方式是com.exmaple.*.*，或者com.exmaple.test.*，这些都是可以的。但如果你不写任何其它内容，只有一个*，那就表示匹配所有的东西。 |
| **         | 匹配任意长度字符，并且包含包名分隔符(.)。比如proguard-android.txt中使用的-dontwarn android.support.**就可以匹配android.support包下的所有内容，包括任意长度的子包。 |
| ***        | 匹配任意参数类型。比如void set*(***)就能匹配任意传入的参数类型，*** get*()就能匹配任意返回值的类型。 |
| …          | 匹配任意长度的任意类型参数。比如void test(…)就能匹配任意void test(String a)或者是void test(int a, String b)这些方法。 |

  到这里对混淆配置文件的语法已经基本了解，系统的proguard-android.txt已经为我们完成大部分基础的混淆配置工作。我们只需要写好自己项目的proguard-rules.pro文件，对照proguard-android.txt照葫芦画瓢就能自定义自己项目的proguard-rules.pro。

代码混淆后会产生如下几个位于
< module-name >/build/outputs/mapping/release/目录下的文件。其中

| 文件名      | 说明                                             |
| ----------- | ------------------------------------------------ |
| dump.txt    | 说明 APK 中所有类文件的内部结构。                |
| mapping.txt | 提供原始与混淆过的类、方法和字段名称之间的转换。 |
| seeds.txt   | 列出未进行混淆的类和成员。                       |
| usage.txt   | 列出从 APK 移除的代码。                          |

  对于开启混淆的项目，我们应该从类的创建就要思考这个类是否混淆，大到一个模块，小到一个类的字段，以免到项目庞大以后再来思考混淆就更加难办了。例如，当项目使用了反射机制，对于反射的类及其成员要根据实际情况避免混淆。又例如Gson所要解析成的实体类的字段也要避免混淆。

#### android逆向



## 进程通信

ipc

IPC是 Inter-Process Communication的缩写，意为进程间通信或跨进程通信，是指两个进程之间进行数据交换的过程。

线程是CPU调度的最小单元，同时线程是一种有限的系统资源。进程一般指一个执行单元，在PC和移动设备上指一个程序或者一个应用。一个进程可以包含多个线程，因此进程和线程是包含与被包含的关系。最简单的情况下，一个进程中只可以有一个线程，即主线程，在Android中也叫UI线程。

IPC不是Android中所独有的，任何一个操作系统都需要相应的IPC机制，比如Windows上可以通过剪贴板等来进行进程间通信。Android是一种基于Linux内核的移动操作系统，它的进程间通信方式并不能完全继承自Linux，它有自己的进程间通信方式。

#### Android 进程间各种通信方案

由于应用程序之间不能共享内存。在不同应用程序之间交互数据（跨进程通讯），在android SDK中提供了4种用于跨进程通讯的方式。这4种方式正好对应于android系统中4种应用程序组件：Activity、Content Provider、Broadcast和Service。其中Activity可以跨进程调用其他应用程序的Activity；Content Provider可以跨进程访问其他应用程序中的数据（以Cursor对象形式返回），当然，也可以对其他应用程序的数据进行增、删、改操 作；Broadcast可以向android系统中所有应用程序发送广播，而需要跨进程通讯的应用程序可以监听这些广播；Service和Content Provider类似，也可以访问其他应用程序中的数据，但不同的是，Content Provider返回的是Cursor对象，而Service返回的是Java对象，这种可以跨进程通讯的服务叫AIDL服务。

#### AIDL