package com.example.noisecontrol.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class SoundProcessor {


    public void processFile(String inputFileName) throws IOException {

        //File outputFile = inputFile;



        // to można narazie zrobic bez Threadow

        byte[] audioBytes= fileToBytes(inputFileName);

       // byte[]  invertedBites = invertPhase(audioBytes);

      // File outputFile = bytesToFile(invertedBites);

        System.out.println("TEST !!!");
        System.out.println(audioBytes.toString());
        System.out.println("Koniec");

       // return outputFile;
    }


    private byte[] fileToBytes(String fileName) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));

        int read;
        byte[] buff = new byte[1024];
        while ((read = in.read(buff)) > 0)
        {
            out.write(buff, 0, read);
        }
        out.flush();

        byte[] audioBytes = out.toByteArray();




        return audioBytes;
    }

    private File bytesToFile(byte[] bytes) {






        File processedAudioFile = new File("path");

        return  processedAudioFile;

    }


    private byte[] invertPhase(byte[] bytes) {




        /// Tutaj obracanie





        return bytes;
    }


}
