LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := CLReader
LOCAL_SRC_FILES := CLReader.cpp
LOCAL_LDLIBS:=-llog

include $(BUILD_SHARED_LIBRARY)
