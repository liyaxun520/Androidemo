cmake_minimum_required(VERSION 3.4.1)

# 编译出一个动态库 native-lib，源文件只有 src/main/cpp/native-lib.cpp
add_library( # Sets the name of the library.
             lison
             # Sets the library as a shared library.
             SHARED
             # Provides a relative path to your source file(s).
             src/main/jni/lison.cpp )

# 找到预编译库 log_lib 并link到我们的动态库 native-lib中
find_library( # Sets the name of the path variable.
              log-lib
              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )
target_link_libraries( # Specifies the target library.
                       lison
                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )