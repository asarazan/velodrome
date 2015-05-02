package com.levelmoney.velodrome.sample;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.levelmoney.velodrome.ResultHandler;
import com.levelmoney.velodrome.Velodrome;
import com.levelmoney.velodrome.velos.ActivityVelo;
import com.levelmoney.velodrome.velos.DialogFragmentVelo;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A placeholder fragment containing a simple view.
 */
public class VeloMainActivityFragment extends Fragment {

    private final int CODE_ACTIVITY = 1234;
    private final int CODE_DIALOG = 100;

    @ResultHandler(CODE_ACTIVITY)
    private final ActivityVelo mActivity = new ActivityVelo(this, CODE_ACTIVITY, EditTextActivity.class) {
        @Override
        public boolean handleResult(int resultCode, Intent data) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getActivity(), data.getStringExtra("text"), Toast.LENGTH_LONG).show();
                return true;
            }
            return false;
        }
    };

    @ResultHandler(CODE_DIALOG)
    private final DialogFragmentVelo mDialog = new DialogFragmentVelo(this, CODE_DIALOG, VeloDialogFragment.class) {
        @Override
        public boolean handleResult(int resultCode, Intent data) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getActivity(), "Confirmed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_LONG).show();
            }
            return true;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
    }

    @OnClick(R.id.activity_button)
    public void onActivity() {
        mActivity.go(null);
    }

    @OnClick(R.id.dialog_button)
    public void onDialog() {
        mDialog.go(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Velodrome.handleResult(this, requestCode, resultCode, data);
    }
}
