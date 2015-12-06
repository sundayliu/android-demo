#include <jni.h>
#include <stdio.h>
#include <android/log.h>

namespace cl{
namespace reader{

void test(){
    __android_log_print(ANDROID_LOG_ERROR,"reader", "Hello,reader");
}

}
}
