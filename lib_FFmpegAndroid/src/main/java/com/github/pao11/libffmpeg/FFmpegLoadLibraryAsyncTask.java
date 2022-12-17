package com.github.pao11.libffmpeg;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;

@Deprecated
class FFmpegLoadLibraryAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private final String cpuArchNameFromAssets;
    private final FFmpegLoadBinaryResponseHandler ffmpegLoadBinaryResponseHandler;
    private final Context context;

    FFmpegLoadLibraryAsyncTask(Context context, String cpuArchNameFromAssets, FFmpegLoadBinaryResponseHandler ffmpegLoadBinaryResponseHandler) {
        this.context = context;
        this.cpuArchNameFromAssets = cpuArchNameFromAssets;
        this.ffmpegLoadBinaryResponseHandler = ffmpegLoadBinaryResponseHandler;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        File ffmpegFile = new File(FileUtils.getFFmpeg(context));
        //2022-11-23
//        if (ffmpegFile.exists() && isDeviceFFmpegVersionOld() && !ffmpegFile.delete()) {
//            return false;
//        }
//        if (!ffmpegFile.exists()) {
//            boolean isFileCopied = FileUtils.copyBinaryFromAssetsToData(context,
//                    cpuArchNameFromAssets + File.separator + FileUtils.ffmpegFileName,
//                    FileUtils.ffmpegFileName);
//
//            // make file executable
//            if (isFileCopied) {
//                if(!ffmpegFile.canExecute()) {
//                    Log.d("FFmpeg is not executable, trying to make it executable ...");
//                    if (ffmpegFile.setExecutable(true)) {
//                        return true;
//                    }
//                } else {
//                    Log.d("FFmpeg is executable");
//                    return true;
//                }
//            }
//        }
//        return ffmpegFile.exists() && ffmpegFile.canExecute();
        return ffmpegFile.exists();
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
        if (ffmpegLoadBinaryResponseHandler != null) {
            if (isSuccess) {
                ffmpegLoadBinaryResponseHandler.onSuccess();
            } else {
                ffmpegLoadBinaryResponseHandler.onFailure();
            }
            ffmpegLoadBinaryResponseHandler.onFinish();
        }
    }

    private boolean isDeviceFFmpegVersionOld() {
        return CpuArch.fromString(FileUtils.SHA1(FileUtils.getFFmpeg(context))).equals(CpuArch.NONE);
    }
}
