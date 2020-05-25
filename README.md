# KdGaugeView
KDGaugeView is a simple and customizable gauge control for Android inspired by [LMGaugeView](https://github.com/lminhtm/LMGaugeView)

![open source](https://img.shields.io/badge/Open%20source-free-green.svg?longCache=true&style=flat-square)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-KdGaugeView-green.svg?style=flat )]( https://android-arsenal.com/details/1/6894 )
![build number](https://img.shields.io/badge/Build-passing-green.svg?longCache=true&style=flat-square)
![version number](https://img.shields.io/badge/Download-1.0.0-blue.svg?longCache=true&style=flat-square)
![Size](https://img.shields.io/badge/Size-9KB-blue.svg?longCache=true&style=flat-square)
![Method count](https://img.shields.io/badge/Method%20count-25k-red.svg?longCache=true&style=flat-square)
![License Apache](https://img.shields.io/badge/License-apache%202.0-red.svg?longCache=true&style=flat-square)


# Motivation

I need some clean Guage view for my Android application.

# Getting started

## Installing
To use this library simply import it by placing the following line under dependencies in your app module's build.gradle file

This library is posted in jCenter

#### Gradle
```Gradle
implementation 'in.unicodelabs.sorbh:kdgaugeview:1.0.0'
```

#### Maven
```Gradle
<dependency>
  <groupId>in.unicodelabs.sorbh</groupId>
  <artifactId>kdgaugeview</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

# Usage

After Importing this library you can directly use this view in your view XML

```java
<in.unicodelabs.kdgaugeview.KdGaugeView
                android:id="@+id/speedMeter"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                app:speed_limit="75" />
```
# Customization
  For now you can these custom attributes

  * app:maxSpeed="180" - max speed for gauge(default = 180)
  * app:minSpeed="0" - min speed for gauge(default = 0)
  * app:speed="60" - speed for gauge
  * app:speed_limit="90" - speed limit for gauge(default = 90)

  * app:unitOfMeasurement="km/hr" - Speed unit for measurment for gauge(default = Km/Hr)

  * app:animationTime="2000" - Animation time in ms

  * app:speedTextSize="100dp" - Speed text size in the center of the gauge
  * app:unitOfMeasurementTextSize="30dp" - Unit of measurement text size
  * app:speedLimitTextSize="15dp" - Speed limit text size

  * app:speedDialRingWidth="15dp" - Speed Dial ring width
  * app:speedDialRingInnerPadding="15dp" - Padding between speed dial and division doted ring

  * app:dialActiveColor="@color/dialActiveColor" - dial active ring color (default = #D3D3D3)
  * app:dialInactiveColor="@color/dialInactiveColor" - dial inactive ring color (default = #E0E0E0)
  * app:dialSpeedColor="@color/dialSpeedColor" - dial speed ring color (default = GREEN)
  * app:dialSpeedAlertColor="@color/dialSpeedAlertColor" - dial speed alert ring color (default = RED)
  * app:subDivisionCircleColor="@color/subDivisionCircleColor" - sub-division circle color (default = DKGRAY)
  * app:divisionCircleColor="@color/divisionCircleColor" - division circle color (default = BLUE)
  * app:speedTextColor="@color/speedTextColor" - speed text color (default = BLACK)
  * app:unitOfMeasurementTextColor="@color/unitOfMeasurementTextColor" - unit of measurement text color (default = BLACK)
  * app:speedLimitTextColor="@color/speedLimitTextColor" - speed limit text color (default = BLACK)



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
