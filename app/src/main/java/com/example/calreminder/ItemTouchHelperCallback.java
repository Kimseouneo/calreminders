package com.example.calreminder;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback  {
    private ItemTouchHelperListener listener;
    private boolean swipeBack = false;
    private float buttonWidth;
    private Integer currentPosition = null;
    private Integer previousPostion = null;
    private float currentdX = 0f;
    private View deleteButton = null;

    public ItemTouchHelperCallback(ItemTouchHelperListener listener, float width){
        this.listener = listener;
        buttonWidth = width;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder){
        int drag_flags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipe_flags = ItemTouchHelper.START|ItemTouchHelper.END;
        return makeMovementFlags(drag_flags,swipe_flags);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

        return listener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onItemSwipe(viewHolder.getAdapterPosition());
    }

    //아이템을 터치하거나 스와이프하거나 뷰에 변화가 생길경우 불러오는 함수
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //아이템이 스와이프 됐을경우 버튼을 그려주기 위해서 스와이프가 됐는지 확인
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            View view = getView(viewHolder);
            Boolean isClamped = getTag(viewHolder);
            Float newX = clampViewPositionHorizontal(dX, isClamped, isCurrentlyActive);

            if(newX == -buttonWidth) {
                getView(viewHolder).animate().translationX(-buttonWidth).setDuration(100L).start();
                return;
            }

            currentdX = newX;
            getDefaultUIUtil().onDraw(c, recyclerView, view, newX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return defaultValue * 10;
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        setTag(viewHolder, currentdX <= buttonWidth);
        return 2f;
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if(swipeBack){
            swipeBack = false;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    private void setTag(RecyclerView.ViewHolder viewHolder, Boolean isClamped) {
        viewHolder.itemView.setTag(isClamped);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder == null) return;
        Log.d("!!","!!");
        if (deleteButton != null)
            deleteButton.setEnabled(false);
        currentPosition = viewHolder.getAdapterPosition();
        getDefaultUIUtil().clearView(viewHolder.itemView);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        currentdX = 0f;
        previousPostion = viewHolder.getAdapterPosition();
        getDefaultUIUtil().clearView(viewHolder.itemView);
    }

    private Boolean getTag(RecyclerView.ViewHolder viewHolder) {
        try {
            Boolean bool = (Boolean) viewHolder.itemView.getTag();
            return bool;
        }
        catch (ClassCastException e){
            e.printStackTrace();
            return false;
        }
    }

    private Float clampViewPositionHorizontal(float dX, Boolean isClamped, Boolean isCurrentlyActive) {
        float max = 0f;

        float newX;
        if (isClamped) {
            if(isCurrentlyActive) {
                if(dX < 0)
                    newX = dX/3 - buttonWidth;
                else
                    newX = dX - buttonWidth;
            }
            else
                newX = -buttonWidth;
        }
        else {
            newX = dX / 2;
            // 지우는 함수 가능하게 만들기
        }
        return Math.min(newX,max);
    }

    public void removePreviousClamp(RecyclerView recyclerView) {
        if (previousPostion == null) {
            Log.d("Helper", "previous Position is null");
            return;
        }

        Log.d("Helper", "StartRemovePreviousClamp");
        try {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(previousPostion);
            getView(viewHolder).animate().x(0f).setDuration(200L).start();
            setTag(viewHolder, false);
            if (deleteButton != null) {
                Log.d("!!!",deleteButton.toString());
                deleteButton.setEnabled(false);
            }
            //지우는 함수 불가능하게 만들기
            previousPostion = null;
        }
        catch (Exception e) {
            Log.d("EXCEPTION", "Can't find previous Component");
            return;
        }

    }

    private View getView(RecyclerView.ViewHolder viewHolder) {
        Log.d("!!","!!!");
        try {
            View swipeView = viewHolder.itemView.findViewById(R.id.reminderItem_swipe_view);
            deleteButton = viewHolder.itemView.findViewById(R.id.reminder_Item_delete);
            if(swipeView == null) {
                swipeView = viewHolder.itemView.findViewById(R.id.todoListItem_swipe_view);
                deleteButton = viewHolder.itemView.findViewById(R.id.todolist_delete);
            }
            deleteButton.setEnabled(true);
            return swipeView;
        }
        catch (Exception e) {
            deleteButton = viewHolder.itemView.findViewById(R.id.todolist_delete);
            deleteButton.setEnabled(true);
            return viewHolder.itemView.findViewById(R.id.todoListItem_swipe_view);
        }
    }
}
