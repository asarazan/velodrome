/*
 * Copyright 2015 Level Money, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A placeholder fragment containing a simple view.
 */
public class VeloMainFragment extends Fragment {

    private static final int CODE_ACTIVITY = 1234;
    private static final int CODE_DIALOG = 100;

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
        startActivityForResult(new Intent(getActivity(), EditTextActivity.class), CODE_ACTIVITY);
    }

    @HandleResult(CODE_ACTIVITY)
    void onActivityReturn(Intent data) {
        Toast.makeText(getActivity(), data.getStringExtra("text"), Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.dialog_button)
    public void onDialog() {
        VeloDialogFragment df = new VeloDialogFragment();
        df.setTargetFragment(this, CODE_DIALOG);
        df.show(getFragmentManager(), "velo");
    }

    @HandleResult(CODE_DIALOG)
    public void onDialogReturn(Intent data) {
        Toast.makeText(getActivity(), "Confirmed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Velodrome.handleResult(this, requestCode, resultCode, data);
    }
}
