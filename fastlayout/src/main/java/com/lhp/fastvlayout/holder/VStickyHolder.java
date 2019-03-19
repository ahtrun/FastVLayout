package com.lhp.fastvlayout.holder;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.lhp.fastvlayout.R;
import com.lhp.fastvlayout.bean.LimitTime;
import com.lhp.fastvlayout.core.VBaseHolder;
import com.lhp.fastvlayout.model.StickyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By lhp
 * on 2019/3/19
 */
public class VStickyHolder extends VBaseHolder<List> {
    private TabLayout tabLayout;
//    private List limitTimeBeans;
    public VStickyHolder(View itemView) {
        super(itemView);
        tabLayout=itemView.findViewById(R.id.tb_limittime);
    }

    @Override
    public void setData(int ps, List mData) {
        super.setData(ps, mData);
        creatTimeTab(model.getData().get(ps));
    }

    public void creatTimeTab(final List limitTimeBeans){
        if(limitTimeBeans!=null){
            StickyModel stickyModel= (StickyModel) model;
//        this.limitTimeBeans=limitTimeBeans;
            tabLayout.removeAllTabs();
            for (int position=0;position<limitTimeBeans.size();position++){
                tabLayout.addTab(tabLayout.newTab());
                //依次获取标签
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                //为每个标签设置布局
                tab.setCustomView(model.getLayoutId());

                if (stickyModel.getItemDataBind()!=null){//tab初始化
                    stickyModel.getItemDataBind().bindView(tab.getCustomView(),limitTimeBeans.get(position));
                }
                final int p=position;
                tab.getCustomView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener!=null){//tab被点击
                            mListener.onItemClick(tabLayout,p,limitTimeBeans.get(p));
                        }
                    }
                });
            }
        }
    }

}
