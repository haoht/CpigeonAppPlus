package com.cpigeon.app.modular.footsearch.presenter;

import android.os.Handler;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.IView;
import com.cpigeon.app.modular.footsearch.view.fragment.IFootSearchView;
import com.cpigeon.app.modular.footsearch.model.dao.ICpigeonServicesInfo;
import com.cpigeon.app.modular.footsearch.model.daoimpl.CpigeonServicesInfoImpl;
import com.cpigeon.app.modular.usercenter.model.bean.CpigeonUserServiceInfo;

import java.util.Map;

/**
 * Created by Administrator on 2017/4/8.
 */

public class FootSearchPre extends BasePresenter<IFootSearchView, ICpigeonServicesInfo> {


    public FootSearchPre(IFootSearchView mView) {
        super(mView);
    }

    public void loadUserServiceInfo() {
        if (isAttached()) {
            mDao.getFootSearchService(mView.getQueryService(), new IBaseDao.OnCompleteListener<CpigeonUserServiceInfo>() {
                @Override
                public void onSuccess(final CpigeonUserServiceInfo data) {
                    post(new CheckAttachRunnable() {
                        @Override
                        protected void runAttached() {
                            mView.getFootSearchService(data);
                        }
                    });
                }

                @Override
                public void onFail(String msg) {
                    post(new CheckAttachRunnable() {
                        @Override
                        protected void runAttached() {
                            mView.getFootSearchService(null);
                        }
                    });
                }
            });
        }

    }

    public org.xutils.common.Callback.Cancelable queryFoot() {
        if (isDetached()) {
            return null;
        }
        mView.showTips("搜索中...", IView.TipType.LoadingShow);
        return mDao.queryFoot(mView.getQueryKey(), new IBaseDao.OnCompleteListener<Map<String, Object>>() {
            @Override
            public void onSuccess(final Map<String, Object> data) {
                post(new CheckAttachRunnable() {
                    @Override
                    protected void runAttached() {
                        mView.showTips(null, IView.TipType.LoadingHide);
                        mView.queryFoot(data);
                    }
                });
            }

            @Override
            public void onFail(String msg) {
                post(new CheckAttachRunnable() {
                    @Override
                    protected void runAttached() {
                        mView.showTips(null, IView.TipType.LoadingHide);
                        mView.showTips("暂未找到记录", IView.TipType.ToastShort);
                    }
                });
            }
        });

    }


    @Override
    protected ICpigeonServicesInfo initDao() {
        return new CpigeonServicesInfoImpl();
    }
}
