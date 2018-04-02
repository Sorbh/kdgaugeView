# KdGaugeView
KDGaugeView is a simple and customizable gauge control for Android inspired by [LMGaugeView](https://github.com/lminhtm/LMGaugeView)
  
# Motivation

I need some clean Guage view for my Android application.

# Getting started

## Installing 
To use this library simply import it by placing the following line under dependencies in your app module's build.gradle file

This library is posted in jCenter

#### Gradle
```
implementation 'in.unicodelabs.sorbh:kdgaugeview:1.0.0'
```

#### Maven
```
<dependency>
  <groupId>in.unicodelabs.sorbh</groupId>
  <artifactId>kdgaugeview</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

# Usage

After Importing this library you can directly use this view in your view xml

      <in.unicodelabs.kdgaugeview.KdGaugeView
                android:id="@+id/speedMeter"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                app:speed_limit="75" />

# Customization
  For now you can use 3 custom attributes

  * app:tint="@color/colorPrimary" - Color for loading view
  * app:animation="@anim/rotate" - Can set custom animaton drawable
  * app:anim_duration="2000" - Animation time in ms
  
# Screenshots
![alt text](https://github.com/sorbh/kdgaugeView/blob/master/raw/1.jpeg)
![alt text](https://github.com/sorbh/kdgaugeView/blob/master/raw/demo.gif)

# Author
  * **Saurabh K Sharma - [GIT](https://github.com/Sorbh)**
  
      I am very new to open source community. All suggestion and improvement are most welcomed. 
  
 
## Contributing

1. Fork it (<https://github.com/sorbh/kdgaugeView/fork>)
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
