package com.example.wangc.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView mRl;
    private MyAdapter myAdapter;
    private List datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        mRl = findViewById(R.id.rl);
        datas = new ArrayList();
        for (int i = 0; i < 25; i++) {
            datas.add(""+i);
        }
        myAdapter = new MyAdapter(this,datas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRl.setLayoutManager(linearLayoutManager);
        mRl.setAdapter(myAdapter);

        //item动画
        MyItemAnimator defaultItemAnimator = new MyItemAnimator();
        defaultItemAnimator.setAddDuration(1000);
        mRl.setItemAnimator(defaultItemAnimator);

        //item分割线
        mRl.addItemDecoration(new MyItemDecoration());
        myAdapter.notifyDataSetChanged();

        //item增加动画
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.add(1,"add");
                myAdapter.notifyItemInserted(1);
            }
        });

        //item删除动画
        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.remove(1);
                myAdapter.notifyItemRemoved(1);
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(mRl);
    }

    public ItemTouchHelper.Callback mCallBack1  = new ItemTouchHelper.Callback() {
        /**
         * 指定可以拖拽（drag）和滑动（swipe）的方向
         * @param recyclerView
         * @param viewHolder
         * @return
         */
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return 0;
        }

        /**
         * 拖拽回调
         * @param recyclerView
         * @param viewHolder
         * @param target
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        /**
         * 滑动回调
         * @param viewHolder
         * @param direction
         */
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    ItemTouchHelper.SimpleCallback mCallback =
            new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                    ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT,
                    ItemTouchHelper.START | ItemTouchHelper.END) {

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    int fromPosition = viewHolder.getAdapterPosition();//当前ViewHolder的position
                    int toPosition = target.getAdapterPosition();//目标ViewHolder的position

                    //item的位置重新交换
                    if (fromPosition < toPosition) {
                        for (int i = fromPosition; i < toPosition; i++) {
                            Collections.swap(datas, i, i + 1);
                        }
                    } else {
                        for (int i = fromPosition; i > toPosition; i--) {
                            Collections.swap(datas, i, i - 1);
                        }
                    }
                    myAdapter.notifyItemMoved(fromPosition, toPosition);
                    return true;
                }


                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    datas.remove(position);//侧滑删除数据
                    myAdapter.notifyItemRemoved(position);
                }


                @Override
                public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        //滑动时改变Item的透明度
                        final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                        viewHolder.itemView.setAlpha(alpha);
                        viewHolder.itemView.setTranslationX(dX);
                    }
                }
            };


    public class MyItemDecoration extends RecyclerView.ItemDecoration{
        private int mDiverHeight;
        private Paint mPaint;

        public MyItemDecoration(){
            mPaint = new Paint();
            mPaint.setColor(Color.GREEN);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);

//            int childCount = parent.getChildCount();
//
//            for (int i = 0; i < childCount; i++) {
//                View view = parent.getChildAt(i);
//
//                if (parent.getChildAdapterPosition(view) !=0){
//                    float top = view.getTop()-mDiverHeight;
//                    float left = parent.getPaddingLeft();
//                    float right = parent.getWidth()-parent.getPaddingRight();
//                    float bottom = view.getTop();
//
//                    c.drawRect(left,top,right,bottom,mPaint);
//                }
//            }

        }


        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);

            int childCount = parent.getChildCount();

            for (int i = 0; i < childCount; i++) {
                View view = parent.getChildAt(i);

                if (parent.getChildAdapterPosition(view) !=0){
                    float top = view.getTop()-mDiverHeight;
                    float left = parent.getPaddingLeft();
                    float right = parent.getWidth()-parent.getPaddingRight();
                    float bottom = view.getTop();

                    c.drawRect(left,top,right,bottom,mPaint);
                }
            }
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildLayoutPosition(view) != 0){
                outRect.top = 10;
                mDiverHeight = 40;
            }
        }
    }

}
