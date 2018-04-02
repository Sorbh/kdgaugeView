# KdGaugeView
This is a circular loading view for android.
  
# Motivation

In default progress view, its hard to change drawable, color and animation. So this project was created to avoid these limitation and create custom easy customizable loading view.

# Getting started

## Installing 
To use this library simply import it by placing the following line under dependencies in your app module's build.gradle file

This library is posted in jCenter

#### Gradle
```
implementation 'in.unicodelabs.sorbh:kdloadignview:1.0.0'
```

#### Maven
```
<dependency>
  <groupId>in.unicodelabs.sorbh</groupId>
  <artifactId>kdloadingview</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

# Usage

After Importing this library you can directly use this view in your view xml

    <in.unicodelabs.view.KdLoadingView
            android:id="@+id/kdLoadingView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:tint="@color/colorPrimary"
            app:animation="@anim/rotate"
            app:anim_duration="2000"/>
            
  For now you can use 3 custom attributes

  * app:tint="@color/colorPrimary" - Color for loading view
  * app:animation="@anim/rotate" - Can set custom animaton drawable
  * app:anim_duration="2000" - Animation time in ms
  
# Screenshots
![alt text](https://github.com/sorbh/KdLoadingView/blob/master/raw/demo.gif) 

# Author
  * **Saurabh K Sharma - [GIT](https://github.com/Sorbh)**
  
      I am very new to open source community. All suggestion and improvement are most welcomed. 
  
 
## Contributing

1. Fork it (<https://github.com/sorbh/KdLoadingView/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request


# License

```
Copyright 2018 Saurabh Kumar Sharma

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
