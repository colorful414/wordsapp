package cn.itfxq.wordsapp.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class VoiceUtils {

    private Context context;
    private static final String TAG = "VoiceUtils";
    private static VoiceUtils singleton;
    // 创建TTS对象
    private TextToSpeech textToSpeech;

    public static VoiceUtils getInstance(Context context) {
        if (singleton == null) {
            synchronized (VoiceUtils.class) {
                if (singleton == null) {
                    singleton = new VoiceUtils(context);
                }
            }
        }
        return singleton;
    }

    public VoiceUtils(Context context) {
        this.context = context;
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                // 设置音调，值越大声音越尖（女生）
                // 值越小则变成男声,1.0是常规
                if (i == TextToSpeech.SUCCESS) {
                    //设置语言
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setPitch(1.0f);
                    textToSpeech.setSpeechRate(1.0f);
                }
            }
        });
    }

    public void speakText(String text) {
        if (textToSpeech != null) {
            textToSpeech.speak(text,
                    TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}