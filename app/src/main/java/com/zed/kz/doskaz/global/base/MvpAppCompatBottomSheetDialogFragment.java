package com.zed.kz.doskaz.global.base;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment;
import com.arellomobile.mvp.MvpDelegate;

public abstract class MvpAppCompatBottomSheetDialogFragment extends SuperBottomSheetFragment {

    private boolean mIsStateSaved;

    private MvpDelegate<? extends MvpAppCompatBottomSheetDialogFragment> mMvpDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        mIsStateSaved = false;

        getMvpDelegate().onAttach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mIsStateSaved = true;

        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();

        getMvpDelegate().onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        getMvpDelegate().onDetach();
        getMvpDelegate().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //We leave the screen and respectively all fragments will be destroyed
        if (getActivity().isFinishing()) {
            getMvpDelegate().onDestroy();
            return;
        }

        // When we rotate device isRemoving() return true for fragment placed in backstack
        // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
        if (mIsStateSaved) {
            mIsStateSaved = false;
            return;
        }

        // See https://github.com/Arello-Mobile/Moxy/issues/24
        boolean anyParentIsRemoving = false;
        Fragment parent = getParentFragment();
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving();
            parent = parent.getParentFragment();
        }

        if (isRemoving() || anyParentIsRemoving) {
            getMvpDelegate().onDestroy();
        }
    }

    /**
     * @return The {@link MvpDelegate} being used by this Fragment.
     */
    public MvpDelegate getMvpDelegate() {
        if (mMvpDelegate == null) {
            mMvpDelegate = new MvpDelegate<>(this);
        }

        return mMvpDelegate;
    }
}