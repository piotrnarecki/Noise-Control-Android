package com.example.noisecontrol.model;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SoundProcessor {

    final int bpp = 16;
    int sampleRate = 44100;
    String invertedWavFile = Environment.getExternalStorageDirectory().getPath() + "/inverted_record.wav";


    public void processFile(String inputFileName) throws IOException {


        byte[] audioBytes = fileToBytes(inputFileName);

        byte[] invertedBytes = invertPhase(audioBytes);

        bytesToFile(invertedBytes, inputFileName, invertedWavFile);

    }


    private byte[] fileToBytes(String fileName) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));

        int read;
        byte[] buff = new byte[1024];
        while ((read = in.read(buff)) > 0) {
            out.write(buff, 0, read);
        }
        out.flush();

        byte[] audioBytes = out.toByteArray();


        return audioBytes;
    }

    private void bytesToFile(byte[] bytes, String tempPath, String wavPath) {


        try {
            FileInputStream fileInputStream = new FileInputStream(tempPath);
            FileOutputStream fileOutputStream = new FileOutputStream(wavPath);
            int channels = 2;
            long byteRate = bpp * sampleRate * channels / 8;
            long totalAudioLen = fileInputStream.getChannel().size();
            long totalDataLen = totalAudioLen + 36;
            wavHeader(fileOutputStream, totalAudioLen, totalDataLen, channels, byteRate);
            while (fileInputStream.read(bytes) != -1) {
                fileOutputStream.write(bytes);
            }
            fileInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private byte[] invertPhase(byte[] bytes) {

        if (bytes == null) {
            return null;
        }

        byte temp;

        for (int i = 0; i < bytes.length - 1; i++) {

            temp = bytes[i];

            bytes[i] = (byte) (temp * -1);

        }
        return bytes;
    }


    private void wavHeader(FileOutputStream fileOutputStream, long totalAudioLen, long totalDataLen, int channels, long byteRate) {
        try {
            byte[] header = new byte[44];
            header[0] = 'R'; // RIFF/WAVE header
            header[1] = 'I';
            header[2] = 'F';
            header[3] = 'F';
            header[4] = (byte) (totalDataLen & 0xff);
            header[5] = (byte) ((totalDataLen >> 8) & 0xff);
            header[6] = (byte) ((totalDataLen >> 16) & 0xff);
            header[7] = (byte) ((totalDataLen >> 24) & 0xff);
            header[8] = 'W';
            header[9] = 'A';
            header[10] = 'V';
            header[11] = 'E';
            header[12] = 'f'; // 'fmt ' chunk
            header[13] = 'm';
            header[14] = 't';
            header[15] = ' ';
            header[16] = 16; // 4 bytes: size of 'fmt ' chunk
            header[17] = 0;
            header[18] = 0;
            header[19] = 0;
            header[20] = 1; // format = 1
            header[21] = 0;
            header[22] = (byte) channels;
            header[23] = 0;
            header[24] = (byte) ((long) sampleRate & 0xff);
            header[25] = (byte) (((long) sampleRate >> 8) & 0xff);
            header[26] = (byte) (((long) sampleRate >> 16) & 0xff);
            header[27] = (byte) (((long) sampleRate >> 24) & 0xff);
            header[28] = (byte) (byteRate & 0xff);
            header[29] = (byte) ((byteRate >> 8) & 0xff);
            header[30] = (byte) ((byteRate >> 16) & 0xff);
            header[31] = (byte) ((byteRate >> 24) & 0xff);
            header[32] = (byte) (2 * 16 / 8); // block align
            header[33] = 0;
            header[34] = bpp; // bits per sample
            header[35] = 0;
            header[36] = 'd';
            header[37] = 'a';
            header[38] = 't';
            header[39] = 'a';
            header[40] = (byte) (totalAudioLen & 0xff);
            header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
            header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
            header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
            fileOutputStream.write(header, 0, 44);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
