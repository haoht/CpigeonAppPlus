package com.cpigeon.app.modular.matchlive.presenter;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.modular.matchlive.model.dao.IChaZuDao;
import com.cpigeon.app.modular.matchlive.model.daoimpl.IChaZuDaoImpl;
import com.cpigeon.app.modular.matchlive.view.activity.viewdao.IRacePigeonsView;
import com.cpigeon.app.modular.matchlive.view.adapter.ChaZuBaoDaoDetailsAdapter;
import com.cpigeon.app.modular.matchlive.view.adapter.RaceReportAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class ChaZuBaoDaoDetailsPre extends BasePresenter<IRacePigeonsView,IChaZuDao> {
    public ChaZuBaoDaoDetailsPre(IRacePigeonsView mView) {
        super(mView);
    }

    @Override
    protected IChaZuDao initDao() {
        return new IChaZuDaoImpl();
    }

    public void loadChaZuBaoDaoDetails()
    {
        if (isAttached())
        {
            mDao.loadChaZuBaoDaoDetails(mView.getMatchType(), mView.getSsid(), mView.getFoot(),
                    mView.getName(), mView.isHascz(), mView.getPageIndex(), mView.getPageSize(),
                    mView.getCzIndex(), mView.getSkey(), new IBaseDao.OnCompleteListener<List>() {
                        @Override
                        public void onSuccess(final List data) {
                            final List d = isDetached() ? null : "xh".equals(mView.getMatchType()) ? ChaZuBaoDaoDetailsAdapter.getXH(data) : ChaZuBaoDaoDetailsAdapter.getGP(data);
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    if (isAttached()) {
                                        if (mView.isRefreshing())
                                            mView.hideRefreshLoading();
                                        else if (mView.isMoreDataLoading())
                                            mView.loadMoreComplete();
                                        mView.showMoreData(d);
                                    }
                                }
                            });
                        }

                        @Override
                        public void onFail(String msg) {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    if (isAttached())
                                    {
                                        //TODO 显示错误界面
                                    }
                                }
                            });
                        }
                    });
        }
    }
}
