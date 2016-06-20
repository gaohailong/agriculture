package com.sxau.agriculture.presenter.fragment_presenter;

import android.os.Handler;

import com.sxau.agriculture.presenter.fragment_presenter_interface.IMessagePresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.view.fragment_interface.IMessageFragment;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public class MessagePresenter implements IMessagePresenter {
    private IMessageFragment iMessageFragment;
    private Handler handler;

    public MessagePresenter(IMessageFragment iMessageFragment,Handler handler) {
        this.iMessageFragment = iMessageFragment;
        this.handler=handler;
    }
//-----------------接口方法-----------------------------
    @Override
    public Object findItemByPosition(int position) {
        return null;
    }

    @Override
    public void pullRefersh() {
        handler.sendEmptyMessage(ConstantUtil.PULL_REFRESH);

    }

    @Override
    public void pushRefersh() {
        handler.sendEmptyMessage(ConstantUtil.UP_LOAD);

    }
//------------------接口方法结束-------------------------
}
