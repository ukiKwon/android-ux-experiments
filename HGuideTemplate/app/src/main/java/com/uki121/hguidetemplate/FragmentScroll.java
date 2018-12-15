package com.uki121.hguidetemplate;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentScroll extends Fragment {
    private ScrollView mscrollview;
    private TextView textViewTos1,textViewTos2,textViewTos3;
    private CheckBox checkTos2;
    private Button btnConfirm;
    private Context mcontext;

    public FragmentScroll() {};
    public static FragmentScroll newInstance() {
        FragmentScroll fragment = new FragmentScroll();
        return fragment;
    }
    @Override
    public void onCreate(Bundle SavedInstancState) {
        super.onCreate(SavedInstancState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_scroll, container, false);
        //Initializing variables
        textViewTos1 = (TextView) view.findViewById(R.id.content_tos1);
        textViewTos2 = (TextView) view.findViewById(R.id.content_tos2);
        textViewTos3 = (TextView) view.findViewById(R.id.content_tos3);

        //HGI
        final HGIndicator hgIndicator = new HGIndicator(view);
        //
        List <String> tosList = Arrays.asList(getResources().getStringArray(R.array.tos));
        textViewTos1.setText(tosList.get(0));
        textViewTos2.setText(tosList.get(1));
        textViewTos3.setText(tosList.get(2));
        //checkbox
        final CheckBox checkTos1 = (CheckBox) view.findViewById(R.id.check_tos1);
        final CheckBox checkTos2 = (CheckBox) view.findViewById(R.id.check_tos2);
        final CheckBox checkTos3 = (CheckBox) view.findViewById(R.id.check_tos3);

        checkTos1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        mcontext,
                        "Clicked : " + v.getId(),
                        Toast.LENGTH_SHORT
                ).show();
            }
         });
        btnConfirm = (Button) view.findViewById(R.id.btn_scroll_cofirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //todo : refacetor is needed
                String msg = " Please, check all the agreements ";
                int checkboxFlag = 0;
                checkboxFlag = checkTos1.isChecked() ? checkboxFlag + 1 : checkboxFlag;
                checkboxFlag = checkTos2.isChecked() ? checkboxFlag + 2 : checkboxFlag;
                checkboxFlag = checkTos3.isChecked() ? checkboxFlag + 4 : checkboxFlag;
                //check whether checkboxes are checked all
                if (checkTos1.isChecked() && checkTos2.isChecked() && checkTos3.isChecked()) {
                    msg = "Success! ";
                } else {
                    //find the point where to hight and move
                    //operation_highlight
                    //part1. Set Trigger(checkbox) - Action(HIGHLIGHT)
                    //CheckBox[] target_box = {checkTos1, checkTos2, checkTos3};
                    /* 새로운 코딩방식*/
                    List<Integer> srcview_id = Arrays.asList(R.id.check_tos1, R.id.check_tos2, R.id.check_tos3);
                    List<Integer> dstview_id = Arrays.asList(R.id.content_tos1, R.id.content_tos2, R.id.content_tos3);
                    hgIndicator.Trigger("confirm_checkbox", srcview_id, "All_check")
                                .Action(dstview_id, "HIGHLIGHT")
                                .Commit();
                    //part2. Set Trigger(scrollview) - Action(FOCUS)
                    /*
                    TextView[] target = {textViewTos1, textViewTos2, textViewTos3};
                    //todo : it is needed to define prority.
                    //Move tos1
                    if (checkboxFlag ==2 || checkboxFlag == 4 || checkboxFlag == 6) {
                        scrollToView(target[0], mscrollview, 0);
                    } //Move tos2
                    else if (checkboxFlag == 1 || checkboxFlag == 5) {
                        scrollToView(target[1], mscrollview, 0);
                    } //Move tos3
                    else if (checkboxFlag == 3) {
                        scrollToView(target[2], mscrollview, 0);
                    }
                }
                Toast.makeText(
                        mcontext, "(" + checkboxFlag + ")" + msg,
                        Toast.LENGTH_SHORT
                ).show();
            */
                }

            }
        });
        mscrollview = (ScrollView) view.findViewById(R.id.container_scroll);
        mcontext = getActivity();

        return view;
    }
    private void manageBlinkEffect(TextView _target) {
        ObjectAnimator anim = ObjectAnimator.ofInt(_target, "backgroundColor", Color.WHITE, Color.BLUE,
                Color.WHITE);
        anim.setDuration(3000);
        anim.setEvaluator(new ArgbEvaluator());
        //anim.setRepeatMode(ValueAnimator.REVERSE);
        //anim.setRepeatCount(Animation.INFINITE);
        anim.start();
    }
    public static void scrollToView(View view, final ScrollView scrollView, int count) {
        if (view != null && view != scrollView) {
            count += view.getTop();
            scrollToView((View) view.getParent(), scrollView, count);
        } else if (scrollView != null) {
            final int finalCount = count;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    scrollView.smoothScrollTo(0, finalCount);
                }
            }, 200);
        }
    }

}
