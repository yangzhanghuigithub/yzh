package com.learn.yzh.common.utils.file;

import java.io.IOException;

/**
 * 视频转码 to MP4
 * @author licr
 *
 */
public class VideoTranscoding {
	
	public static void AnyToMP4(String ffpmegAddress,String imputFile,String outputFile){
		String flvtomp4 = ffpmegAddress+" -loglevel quiet -i " + imputFile + " -s 320x240 -r 29.97 -b 100k -vcodec h264 -acodec libvorbis -ac 2 -ar 48000 -ab 192k " + outputFile;
		Process proc = null; 
		try {
		        proc = Runtime.getRuntime().exec(flvtomp4);
		        proc.waitFor();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }

		}
}
