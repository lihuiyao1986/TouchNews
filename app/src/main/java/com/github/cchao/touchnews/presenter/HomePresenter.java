package com.github.cchao.touchnews.presenter;

import android.util.Log;

import com.android.volley.VolleyError;
import com.github.cchao.touchnews.javaBean.Weather;
import com.github.cchao.touchnews.model.i.IHomeModel;
import com.github.cchao.touchnews.model.HomeModel;
import com.github.cchao.touchnews.presenter.i.IHomePresenter;
import com.github.cchao.touchnews.util.NetRequestUtil;
import com.github.cchao.touchnews.view.HomeView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cchao on 2016/3/31.
 * E-mail:   cchao1024@163.com
 * Description:
 */
public class HomePresenter implements IHomePresenter {
        HomeView mHomeView;
        IHomeModel mHomeModel;
        Gson gson=new Gson();

        public HomePresenter ( HomeView homeView ) {
                mHomeView = homeView;
                mHomeModel = new HomeModel ( this);
        }

        @Override
        public void getFragments( ) {
                mHomeView.setFragmentPager ( mHomeModel.getFragments ( ));
        }

        @Override
        public void getNavigation() {
                mHomeModel.loadNavigation ( new NetRequestUtil.RequestListener ( ) {
                        @Override
                        public void onResponse ( JSONObject response ) {
                                Log.e("weather", response.toString() );
                                try {
                                        JSONObject jsonObject=new JSONObject(response.toString());
                                        JSONObject jsonWeather=jsonObject.getJSONArray("HeWeather data service 3.0").getJSONObject(0);
                                        String s=jsonWeather.toString();
                                        Weather weather=gson.fromJson(jsonWeather.toString(), Weather.class);
                                        mHomeView.setNavigation(weather);
                                } catch (JSONException e) {
                                        e.printStackTrace();
                                }
                        }

                        @Override
                        public void onError ( VolleyError error ) {

                        }
                } );
        }
}
