package com.example.audio_demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.audio.AudioRecorder;
import com.example.audio.PlayConfig;
import com.example.audio.RxAmplitude;
import com.example.audio.RxAudioPlayer;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

class AudioRecordActivity extends RxAppCompatActivity {


    private RxPermissions mPermissions;
    public static final int MIN_AUDIO_LENGTH_SECONDS = 2;

    private List<ImageView> mIvVoiceIndicators;
    private AudioRecorder mAudioRecorder;
    private RxAudioPlayer mRxAudioPlayer;
    public static final String TAG  = AudioRecordActivity.class.getSimpleName();
    private TextView mTvPressToSay;
    private TextView mTvRecordingHint;
    private Disposable mRecordDisposable;
    private File mAudioFile;
    private FrameLayout mFlIndicator;
    private Queue<File> mAudioFiles = new LinkedList<>();
    private TextView mTvLog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);

        mPermissions = new RxPermissions(this);

        mPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });
        initView();
    }

    private void initView() {
        mIvVoiceIndicators = new ArrayList<>();
        mIvVoiceIndicators.add(findViewById(R.id.mIvVoiceIndicator1));
        mIvVoiceIndicators.add(findViewById(R.id.mIvVoiceIndicator2));
        mIvVoiceIndicators.add(findViewById(R.id.mIvVoiceIndicator3));
        mIvVoiceIndicators.add(findViewById(R.id.mIvVoiceIndicator4));
        mIvVoiceIndicators.add(findViewById(R.id.mIvVoiceIndicator5));
        mIvVoiceIndicators.add(findViewById(R.id.mIvVoiceIndicator6));
        mIvVoiceIndicators.add(findViewById(R.id.mIvVoiceIndicator7));

        mFlIndicator = findViewById(R.id.mFlIndicator);
        mTvPressToSay = findViewById(R.id.mTvPressToSay);
        mTvRecordingHint = findViewById(R.id.mTvRecordingHint);
        mTvLog = findViewById(R.id.mTvLog);
        Button mBtnPlay = findViewById(R.id.mBtnPlay);
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAudioFiles.isEmpty()) {
                    startPlayAudio();
                }
            }

            private void startPlayAudio() {
                File audioFile = mAudioFiles.poll();
                Disposable subscribe = mRxAudioPlayer.play(
                        PlayConfig.file(audioFile)
                                .streamType(AudioManager.MODE_IN_CALL)
                                .build())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(Functions.emptyConsumer(), Throwable::printStackTrace,
                                this::startPlayAudio);
            }
        });

        mAudioRecorder = AudioRecorder.getInstance();
        mRxAudioPlayer = RxAudioPlayer.getInstance();
        mAudioRecorder.setOnErrorListener(new AudioRecorder.OnErrorListener() {
            @Override
            public void onError(int error) {
                Log.e(TAG,"异常错误   "+error);
            }
        });
        mTvPressToSay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG,"ACTION_DOWN");
                        press2Record();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG,"ACTION_UP");
                        release2Send();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.d(TAG,"ACTION_CANCEL");
                        release2Send();
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
    }

    private void press2Record() {
        mTvPressToSay.setBackgroundResource(R.drawable.button_press_to_say_pressed_bg);
        mTvRecordingHint.setText(R.string.voice_msg_input_hint_speaking);

        boolean isPermissionsGranted
                = mPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && mPermissions.isGranted(Manifest.permission.RECORD_AUDIO);
        if (!isPermissionsGranted) {
            mPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO)
                    .subscribe(granted -> {
                        // not record first time to request permission
                        if (granted) {
                            Toast.makeText(getApplicationContext(), "Permission granted",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Permission not granted",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }, Throwable::printStackTrace);
        } else {
            recordAfterPermissionGranted();
        }
    }
    private void release2Send() {
        mTvPressToSay.setBackgroundResource(R.drawable.button_press_to_say_bg);
        mFlIndicator.setVisibility(View.GONE);

        if (mRecordDisposable != null && !mRecordDisposable.isDisposed()) {
            mRecordDisposable.dispose();
            mRecordDisposable = null;
        }

        Observable
                .fromCallable(() -> {
                    int seconds = mAudioRecorder.stopRecord();
                    Log.d(TAG, "stopRecord: " + seconds);
                    if (seconds >= MIN_AUDIO_LENGTH_SECONDS) {
                        mAudioFiles.offer(mAudioFile);
                        return true;
                    }
                    return false;
                })
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(added -> {
                    if (added) {
                        mTvLog.setText(mTvLog.getText() + "\n"
                                + "audio file " + mAudioFile.getName() + " added");
                    }
                }, Throwable::printStackTrace);
    }

    private void recordAfterPermissionGranted() {
        mRecordDisposable = Observable
                .fromCallable(() -> {
                    mAudioFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + System.nanoTime() + ".mp3");
                    Log.d(TAG, "to prepare record");
                    return mAudioRecorder.prepareRecord(MediaRecorder.AudioSource.MIC,
                            MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.AudioEncoder.AAC,
                            192000, 192000, mAudioFile);
                })
                .flatMap(b -> {
                    Log.d(TAG, "prepareRecord success");
                    Log.d(TAG, "to play audio_record_ready: " + R.raw.audio_record_ready);
                    return mRxAudioPlayer.play(
                            PlayConfig.res(getApplicationContext(), R.raw.audio_record_ready)
                                    .build());
                })
                .doOnComplete(() -> {
                    Log.d(TAG, "audio_record_ready play finished");
                    mFlIndicator.post(() -> mFlIndicator.setVisibility(View.VISIBLE));
                    mAudioRecorder.startRecord();
                })
                .doOnNext(b -> Log.d(TAG, "startRecord success"))
                .flatMap(o -> RxAmplitude.from(mAudioRecorder))
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(level -> {
                    int progress = mAudioRecorder.progress();
                    Log.d(TAG, "amplitude: " + level + ", progress: " + progress);

                    refreshAudioAmplitudeView(level);

                    if (progress >= 12) {
                        mTvRecordingHint.setText(String.format(
                                getString(R.string.voice_msg_input_hint_time_limited_formatter),
                                15 - progress));
                        if (progress == 15) {
                            release2Send();
                        }
                    }
                }, Throwable::printStackTrace);
    }

    private void refreshAudioAmplitudeView(int level) {
        int end = level < mIvVoiceIndicators.size() ? level : mIvVoiceIndicators.size();
        for (int i = 0; i < end; i++) {
            mIvVoiceIndicators.get(i).setVisibility(View.VISIBLE);
        }
        for (int i = end; i < mIvVoiceIndicators.size(); i++) {
            mIvVoiceIndicators.get(i).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRxAudioPlayer != null) {
            mRxAudioPlayer.stopPlay();
        }
        if (mAudioRecorder != null) {
            mAudioRecorder.stopRecord();
        }
    }
}
