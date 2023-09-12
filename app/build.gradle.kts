plugins {
    id("com.android.application")
}

android {
    namespace = "com.appteam4.postella"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.appteam4.postella"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true;
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Navigation 라이브러리: 화면 전환 및 탐색 관리, UI요소와 상호작용을 위함
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")
    //Retrofit: 네트워크 통신을 수행하기 위함
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Retrofit과 함께 사용되는 Gson 컨버터 라이브러리: JSON데이터 <-> java객체
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //Glide: 안드로이드에서 이미지 로딩 및 표시를 위함
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // AndroidX: activity라이브러리의 일부, 라이브러리 패키지 구조 개선을 위해 사용
    implementation ("androidx.activity:activity:1.7.2")

    // 둥근 이미지를 보여주는 라이브러리
    implementation("de.hdodenhof:circleimageview:3.1.0")

    modules {
        module("org.jetbrains.kotlin:kotlin-stdlib-jdk7") {
            replacedBy("org.jetbrains.kotlin:kotlin-stdlib", "kotlin-stdlib-jdk7 is now part of kotlin-stdlib")
        }
        module("org.jetbrains.kotlin:kotlin-stdlib-jdk8") {
            replacedBy("org.jetbrains.kotlin:kotlin-stdlib", "kotlin-stdlib-jdk8 is now part of kotlin-stdlib")
        }
    }
}