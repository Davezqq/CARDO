![](https://github.com/Davezqq/CARDO/raw/master/image/DSC_2213.jpg)
CARDO
=
该文件用于阐述CARDO软件的功能，组成部分，部署方法以及注意事项。CARDO是一款简单的Android端的时间规划软件，开发语言为JAVA。
***
| AUthor | Davezqq |
| -------|---------|
| Email  |136834413@qq.com|
***
名称由来
----
该软件名为CARDO，由CARD和DO拼凑而成，寓意为卡片式的执行计划，软件设计理念为“每日一卡，健康规划”

Functions
---
Welcome Page<br>
![](https://github.com/Davezqq/CARDO/raw/master/image/image9.png)

CardList Page<br>
![](https://github.com/Davezqq/CARDO/raw/master/image/image4.png)

Click the plus sign on the upper-right corner to create a new card with the date already set<br>
![](https://github.com/Davezqq/CARDO/raw/master/image/image3.png)

Longclick on the card to delete the card<br>
![](https://github.com/Davezqq/CARDO/raw/master/image/image5.png)

Click the card to show the task list of the card<br>
![](https://github.com/Davezqq/CARDO/raw/master/image/image6.png)

Click the plus sign on the upper-right corner to create a new task<br>
![](https://github.com/Davezqq/CARDO/raw/master/image/image7.png)

Longclick on the card to delete the card<br>
![](https://github.com/Davezqq/CARDO/raw/master/image/image8.png)

Requirements
---
JDK1.8<br>
Android SDK Version19-26(26 is the best)<br>
Development Platform: Android Studio

Installation
----
run the app/realease/CARDO.apk file on the Android devices

Quickstart
----
* Download the CARDO folder 
* Open the project in Android Studio

Instructions
----
* ./app/realease : contains the .apk files
* ./app/src/main/java : contains the source code
* ./app/src/main/res/drawable*: contains the pictures
* ./app/src/main/res/layout: contains the xml files of the interface
* ./app/src/main/res/values: contains the values of the strings and so on....
* ./app/src/main/res/menu: contains the xml files of the menus
* ./app/src/main/res/anim: contains the xml files of the animations

Relationship between fragments and activities
---
CardListFragment -> CardListActivity<br>
TasksetFragment -> TasksetActivity<br>
TasksListFragment -> TasksInDayActivity

Relationship between Pages and code
---
![](https://github.com/Davezqq/CARDO/raw/master/image/image1.png)

Relationship between Data Structures
---
![](https://github.com/Davezqq/CARDO/raw/master/image/image2.png)

Todo List
---
* 任务倒计时，在task上显示当前时间距离任务开始还有多长时间
