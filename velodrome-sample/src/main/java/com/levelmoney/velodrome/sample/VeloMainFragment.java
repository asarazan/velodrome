package com.levelmoney.velodrome.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.levelmoney.velodrome.Velodrome;
import com.levelmoney.velodrome.annotations.HandleResult;
import com.levelmoney.velodrome.handlers.ActivityResultHandler;
import com.levelmoney.velodrome.handlers.DialogFragmentResultHandler;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A placeholder fragment containing a simple view.
 */
public class VeloMainFragment extends Fragment {

    private final int CODE_ACTIVITY = 1234;
    private final int CODE_DIALOG = 100;

    @HandleResult
    private final ActivityResultHandler mActivity =
            new ActivityResultHandler(this, CODE_ACTIVITY, EditTextActivity.class) {
                @Override
                public void handleResult(Intent data) {
                    Toast.makeText(getActivity(), data.getStringExtra("text"), Toast.LENGTH_LONG).show();
                }
            };

    @HandleResult
    private final DialogFragmentResultHandler mDialog =
            new DialogFragmentResultHandler(this, CODE_DIALOG, VeloDialogFragment.class) {
                @Override
                public void handleResult(Intent data) {
                    Toast.makeText(getActivity(), "Confirmed", Toast.LENGTH_LONG).show();
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
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
