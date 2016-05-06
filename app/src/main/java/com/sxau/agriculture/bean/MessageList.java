package com.sxau.agriculture.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息页面的bean
 * @author 高海龙
 */
public class MessageList {
        @SerializedName("messageInfo")
        @Expose
        private List<MessageInfo> messageInfo = new ArrayList<MessageInfo>();

        /**
         *
         * @return
         * The messageInfo
         */
        public List<MessageInfo> getMessageInfo() {
            return messageInfo;
        }

        /**
         *
         * @param messageInfo
         * The messageInfo
         */
        public void setMessageInfo(List<MessageInfo> messageInfo) {
            this.messageInfo = messageInfo;
        }

    }
