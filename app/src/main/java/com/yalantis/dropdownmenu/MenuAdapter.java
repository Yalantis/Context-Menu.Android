package com.yalantis.dropdownmenu;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill-Penzykov on 23.12.2014.
 */
public class MenuAdapter {

    private static final int ANIMATION_DURATION_MILLIS = 150;

    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private LinearLayout mMenuWrapper;
    private List<MenuObject> mMenuObjects;

    private boolean mIsMenuOpen = false;
    private boolean mIsAnimationRun = false;

    private AnimatorSet mAnimatorSetHideMenu;
    private AnimatorSet mAnimatorSetShowMenu;
    private View mClickedView;


    private int mActionBarSize;

    public interface OnItemClickListener {
        public void onClick(View v);
    }

    public MenuAdapter(Context context, LinearLayout menuWrapper, List<MenuObject> menuObjects,
                       OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.mMenuWrapper = menuWrapper;
        this.mMenuObjects = menuObjects;
        this.mOnItemClickListener = onItemClickListener;

        mActionBarSize = Utils.getDefaultActionBarSize(mContext);

        setViews();
        setAnimations();
        setClosingAnimation();
        setOpeningAnimation();
    }

    public int getItemCount() {
        return mMenuObjects.size();
    }

    /**
     * Creating views and filling to wrapper
     */
    private void setViews() {
        for (MenuObject menuObject : mMenuObjects) {
            ImageButton imageButton = new ImageButton(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    mActionBarSize, mActionBarSize);
            imageButton.setLayoutParams(lp);
            imageButton.setBackgroundColor(mContext.getResources().getColor(android.R.color.darker_gray));
            imageButton.setPadding((int) mContext.getResources().getDimension(R.dimen.menu_item_padding),
                    (int) mContext.getResources().getDimension(R.dimen.menu_item_padding),
                    (int) mContext.getResources().getDimension(R.dimen.menu_item_padding),
                    (int) mContext.getResources().getDimension(R.dimen.menu_item_padding));
            imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageButton.setImageDrawable(menuObject.getDrawable());
            imageButton.setOnClickListener(clickItem);
            mMenuWrapper.addView(imageButton);
        }
    }

    private void resetAnimation(View view) {
        ViewHelper.setRotationX(view, -90);
    }

    private void resetFirstItemAnimation(View view) {
        ViewHelper.setRotationY(view, -90);
    }

    private void resetVerticalAnimation(View view) {
        if (!mIsMenuOpen) {
            ViewHelper.setRotation(view, 0);
            ViewHelper.setRotationY(view, 0);
            ViewHelper.setRotationX(view, -90);
        }
        ViewHelper.setPivotX(view, view.getMeasuredHeight() / 2);
        ViewHelper.setPivotY(view, 0);
    }

    private void resetVerticalAnimationToBottom(View view) {
        ViewHelper.setPivotX(view, view.getMeasuredHeight() / 2);
        ViewHelper.setPivotY(view, view.getMeasuredHeight());
    }

    private void resetSideAnimation(View view) {
        if (!mIsMenuOpen) {
            ViewHelper.setRotation(view, 0);
            ViewHelper.setRotationY(view, -90);
            ViewHelper.setRotationX(view, 0);
        }
        ViewHelper.setPivotX(view, view.getMeasuredHeight());
        ViewHelper.setPivotY(view, view.getMeasuredHeight() / 2);

    }

    private void setAnimations() {
        for (int i = 0; i < getItemCount(); i++) {
            if (i == 0) {
                resetFirstItemAnimation(mMenuWrapper.getChildAt(i));
            } else {
                resetAnimation(mMenuWrapper.getChildAt(i));
            }
        }
    }

    private void setClosingAnimation() {

        List<Animator> closingAnimations = new ArrayList<>();
        for (int i = getItemCount() - 1; i >= 0; i--) {
            if (i == 0) {
                closingAnimations.add(rotationCloseToRight(mMenuWrapper.getChildAt(i)));
            } else {
                closingAnimations.add(rotationCloseVertical(mMenuWrapper.getChildAt(i)));
            }
        }

        mAnimatorSetHideMenu = new AnimatorSet();
        mAnimatorSetHideMenu.playSequentially(closingAnimations);
        mAnimatorSetHideMenu.setDuration(ANIMATION_DURATION_MILLIS);
        mAnimatorSetHideMenu.addListener(mCloseOpenAnimatorListener);

    }

    private void setOpeningAnimation() {

        List<Animator> openingAnimations = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            if (i == 0) {
                openingAnimations.add(rotationOpenFromRight(mMenuWrapper.getChildAt(i)));
            } else {
                openingAnimations.add(rotationOpenVertical(mMenuWrapper.getChildAt(i)));
            }
        }

        mAnimatorSetShowMenu = new AnimatorSet();
        mAnimatorSetShowMenu.playSequentially(openingAnimations);
        mAnimatorSetShowMenu.setDuration(ANIMATION_DURATION_MILLIS);
        mAnimatorSetShowMenu.addListener(mCloseOpenAnimatorListener);

    }

    private void resetAnimationBeforeToggle() {
        for (int i = 0; i < getItemCount(); i++) {
            if (i == 0) {
                resetSideAnimation(mMenuWrapper.getChildAt(i));
            } else {
                resetVerticalAnimation(mMenuWrapper.getChildAt(i));
            }
        }
    }

    private View.OnClickListener clickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mIsMenuOpen && !mIsAnimationRun) {
                mClickedView = v;
                toggleIsAnimationRun();
                int childIndex = mMenuWrapper.indexOfChild(v);
                if (childIndex == -1) {
                    return;
                }

                List<Animator> closeToBottomAnimatorObjects = new ArrayList<>();
                for (int i = 0; i < childIndex; i++) {
                    View view = mMenuWrapper.getChildAt(i);
                    resetVerticalAnimationToBottom(view);
                    closeToBottomAnimatorObjects.add(rotationCloseVertical(view));
                }
                AnimatorSet closeToBottom = new AnimatorSet();
                closeToBottom.playSequentially(closeToBottomAnimatorObjects);

                List<Animator> closeToTopAnimatorObjects = new ArrayList<>();
                for (int i = getItemCount() - 1; i > childIndex; i--) {
                    View view = mMenuWrapper.getChildAt(i);
                    resetVerticalAnimation(view);
                    closeToTopAnimatorObjects.add(rotationCloseVertical(view));
                }
                AnimatorSet closeToTop = new AnimatorSet();
                closeToTop.playSequentially(closeToTopAnimatorObjects);

                resetSideAnimation(mMenuWrapper.getChildAt(childIndex));
                ObjectAnimator closeToRight = rotationCloseToRight(mMenuWrapper.getChildAt(childIndex));
                closeToRight.addListener(mChosenItemFinishAnimatorListener);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(closeToBottom).with(closeToTop);
                if (closeToBottomAnimatorObjects.size() >= closeToTopAnimatorObjects.size()) {
                    animatorSet.play(closeToBottom).before(closeToRight);
                } else {
                    animatorSet.play(closeToTop).before(closeToRight);
                }
                animatorSet.setDuration(ANIMATION_DURATION_MILLIS);
                animatorSet.start();
                toggleIsMenuOpen();
            }
        }
    };

    public void menuToggle() {

        if (!mIsAnimationRun) {
            resetAnimationBeforeToggle();
            mIsAnimationRun = true;
            if (mIsMenuOpen) {
                mAnimatorSetHideMenu.start();
            } else {
                mAnimatorSetShowMenu.start();
            }
            toggleIsMenuOpen();
        }
    }

    private void toggleIsAnimationRun() {
        mIsAnimationRun = !mIsAnimationRun;
    }

    private void toggleIsMenuOpen() {
        mIsMenuOpen = !mIsMenuOpen;
    }

    private ObjectAnimator rotationCloseToRight(View v) {
        return ObjectAnimator.ofFloat(v, "rotationY", 0, -90);
    }

    private ObjectAnimator rotationOpenFromRight(View v) {
        return ObjectAnimator.ofFloat(v, "rotationY", -90, 0);
    }

    private ObjectAnimator rotationCloseVertical(View v) {
        return ObjectAnimator.ofFloat(v, "rotationX", 0, -90);
    }

    private ObjectAnimator rotationOpenVertical(View v) {
        return ObjectAnimator.ofFloat(v, "rotationX", -90, 0);
    }

    private Animator.AnimatorListener mCloseOpenAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            toggleIsAnimationRun();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private Animator.AnimatorListener mChosenItemFinishAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            toggleIsAnimationRun();
            mOnItemClickListener.onClick(mClickedView);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

}
