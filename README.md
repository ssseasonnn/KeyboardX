# KeyboardX

[![](https://jitpack.io/v/ssseasonnn/KeyboardX.svg)](https://jitpack.io/#ssseasonnn/KeyboardX)

<img src="keyboardx.gif" width="50%" height="50%"/>

## Prepare

### 1. Add jitpack to build.gradle
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### 2.  Add dependency

```gradle
dependencies {
	implementation 'com.github.ssseasonnn:KeyboardX:1.0.0'
}
```

## Usage

### Basic Usage

```kotlin
class MainActivity : AppCompatActivity() {

    private val keyboardX by lazy { KeyboardX() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        keyboardX.heightFlow().onEach { height ->
            println("Keyboard height is: $height")
        }.launchIn(lifecycleScope)
    }
}
```

## License

> ```
> Copyright 2021 Season.Zlc
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
> ```
