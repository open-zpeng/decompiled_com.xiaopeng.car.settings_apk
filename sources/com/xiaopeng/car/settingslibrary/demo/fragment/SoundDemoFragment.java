package com.xiaopeng.car.settingslibrary.demo.fragment;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.car.settingslibrary.common.utils.Logs;
import com.xiaopeng.car.settingslibrary.ui.base.BaseFragment;
import com.xiaopeng.car.settingslibrary.utils.ToastUtils;
import com.xiaopeng.speech.speechwidget.ListWidget;
/* loaded from: classes.dex */
public class SoundDemoFragment extends BaseFragment implements View.OnClickListener {
    int doubleGroup1;
    int doubleGroup2;
    int hardBar;
    AudioManager mAudioManager;
    int originBar;
    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
    Button slientBtn;
    int softBar;
    int type;

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void init(Bundle bundle) {
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected int layoutId() {
        return R.layout.sound_demo;
    }

    @Override // com.xiaopeng.car.settingslibrary.ui.base.BaseFragment
    protected void initView(View view) {
        this.mAudioManager = (AudioManager) getActivity().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
        view.findViewById(R.id.single_seat1).setOnClickListener(this);
        view.findViewById(R.id.single_seat2).setOnClickListener(this);
        view.findViewById(R.id.single_seat3).setOnClickListener(this);
        view.findViewById(R.id.single_seat4).setOnClickListener(this);
        view.findViewById(R.id.single_seat5).setOnClickListener(this);
        view.findViewById(R.id.single_seat6).setOnClickListener(this);
        view.findViewById(R.id.single_seat7).setOnClickListener(this);
        this.slientBtn = (Button) view.findViewById(R.id.slient);
        this.slientBtn.setOnClickListener(this);
        if (this.mAudioManager.isStreamMute(3)) {
            this.slientBtn.setText("取消静音");
        } else {
            this.slientBtn.setText("静音");
        }
        this.radioGroup1 = (RadioGroup) view.findViewById(R.id.double_group1);
        this.radioGroup2 = (RadioGroup) view.findViewById(R.id.double_group2);
        this.radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.SoundDemoFragment.1
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.double_seat1) {
                    SoundDemoFragment.this.doubleGroup1 = 1;
                } else if (checkedRadioButtonId == R.id.double_seat2) {
                    SoundDemoFragment.this.doubleGroup1 = 2;
                } else if (checkedRadioButtonId == R.id.double_seat3) {
                    SoundDemoFragment.this.doubleGroup1 = 3;
                }
                Logs.d("xpdemo radioGroup1 i:" + SoundDemoFragment.this.doubleGroup1);
            }
        });
        this.radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.SoundDemoFragment.2
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.double_seat5) {
                    SoundDemoFragment.this.doubleGroup2 = 5;
                } else if (checkedRadioButtonId == R.id.double_seat6) {
                    SoundDemoFragment.this.doubleGroup2 = 6;
                } else if (checkedRadioButtonId == R.id.double_seat7) {
                    SoundDemoFragment.this.doubleGroup2 = 7;
                }
                Logs.d("xpdemo radioGroup2 i:" + SoundDemoFragment.this.doubleGroup2);
            }
        });
        view.findViewById(R.id.double_ok).setOnClickListener(this);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.origin);
        SeekBar seekBar2 = (SeekBar) view.findViewById(R.id.soft);
        SeekBar seekBar3 = (SeekBar) view.findViewById(R.id.hard);
        seekBar.setMax(30);
        seekBar2.setMax(30);
        seekBar3.setMax(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.SoundDemoFragment.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar4) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar4) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar4, int i, boolean z) {
                if (z) {
                    SoundDemoFragment.this.originBar = i;
                }
            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.SoundDemoFragment.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar4) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar4) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar4, int i, boolean z) {
                if (z) {
                    SoundDemoFragment.this.softBar = i;
                }
            }
        });
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.SoundDemoFragment.5
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar4) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar4) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar4, int i, boolean z) {
                if (z) {
                    SoundDemoFragment.this.hardBar = i;
                }
            }
        });
        view.findViewById(R.id.effect_ok).setOnClickListener(this);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.effecttype_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.SoundDemoFragment.6
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup2, int i) {
                int checkedRadioButtonId = radioGroup2.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.effcttype1) {
                    SoundDemoFragment.this.type = 1;
                } else if (checkedRadioButtonId == R.id.effcttype2) {
                    SoundDemoFragment.this.type = 2;
                }
            }
        });
        radioGroup.check(R.id.effcttype1);
        ((RadioGroup) view.findViewById(R.id.effectmode_group)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.xiaopeng.car.settingslibrary.demo.fragment.SoundDemoFragment.7
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup2, int i) {
                int checkedRadioButtonId = radioGroup2.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.effctmode0) {
                    SoundDemoFragment soundDemoFragment = SoundDemoFragment.this;
                    soundDemoFragment.chooseEffectMode(soundDemoFragment.type, -1);
                } else if (checkedRadioButtonId == R.id.effctmode1) {
                    SoundDemoFragment soundDemoFragment2 = SoundDemoFragment.this;
                    soundDemoFragment2.chooseEffectMode(soundDemoFragment2.type, 0);
                } else if (checkedRadioButtonId == R.id.effctmode2) {
                    SoundDemoFragment soundDemoFragment3 = SoundDemoFragment.this;
                    soundDemoFragment3.chooseEffectMode(soundDemoFragment3.type, 1);
                } else if (checkedRadioButtonId == R.id.effctmode3) {
                    SoundDemoFragment soundDemoFragment4 = SoundDemoFragment.this;
                    soundDemoFragment4.chooseEffectMode(soundDemoFragment4.type, 2);
                } else if (checkedRadioButtonId == R.id.effctmode4) {
                    SoundDemoFragment soundDemoFragment5 = SoundDemoFragment.this;
                    soundDemoFragment5.chooseEffectMode(soundDemoFragment5.type, 3);
                }
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.single_seat1 || id == R.id.single_seat2 || id == R.id.single_seat3 || id == R.id.single_seat4 || id == R.id.single_seat5 || id == R.id.single_seat6 || id == R.id.single_seat7 || id == R.id.double_ok) {
            return;
        }
        if (id == R.id.effect_ok) {
            effectChoose(this.type, this.originBar, this.softBar, this.hardBar);
        } else if (id == R.id.slient) {
            if (this.slientBtn.getText().equals("静音")) {
                this.mAudioManager.setStreamMute(3, true);
                this.slientBtn.setText("取消静音");
                return;
            }
            this.mAudioManager.setStreamMute(3, false);
            this.slientBtn.setText("静音");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void chooseEffectMode(int i, int i2) {
        if (i == 0) {
            ToastUtils.get().showText("请选择音效类型");
            return;
        }
        String str = i2 != -1 ? i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? "" : "安静" : "摇滚" : "爵士" : "智能EQ" : "自定义";
        ToastUtils toastUtils = ToastUtils.get();
        toastUtils.showText(str + i);
    }

    private void effectChoose(int i, int i2, int i3, int i4) {
        if (i == 0) {
            ToastUtils.get().showText("请选择音效类型");
            return;
        }
        ToastUtils toastUtils = ToastUtils.get();
        toastUtils.showText("音效类型:" + i + " 对应值:(" + i2 + "," + i3 + "," + i4 + ")");
    }
}
