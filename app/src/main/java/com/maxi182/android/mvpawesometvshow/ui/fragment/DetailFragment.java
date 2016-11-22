package com.maxi182.android.mvpawesometvshow.ui.fragment;

import com.maxi182.android.mvpawesometvshow.ui.fragment.base.BaseNavigationFragment;

/**
 * Created by mac on 21/11/2016.
 */

public class DetailFragment extends BaseNavigationFragment<DetailFragment.DetailFragmentCallbacks>  {

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }



    @Override
    public DetailFragmentCallbacks getDummyCallbacks() {
        return null;
    }


    public interface DetailFragmentCallbacks {
        void onGoToVerificationCode(String ccode, String phoneNbr);
    }
}
