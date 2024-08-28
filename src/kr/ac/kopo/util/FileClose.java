package kr.ac.kopo.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class FileClose {
	public static void close(InputStream is) {
		if(is != null) {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(InputStream is, InputStream bis) {
		close(bis);
		close(is);
	}
	
	public static void close(OutputStream os, OutputStream bos) {
		close(bos);
		close(os);
	}
	
	public static void close(OutputStream os) {
		if(os != null) {
			try {
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Writer w) {
		if(w != null) {
			try {
				w.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Reader r) {
		if(r != null) {
			try {
				r.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Reader r, BufferedReader br) {
		if(r != null) {
			try {
				br.close();
				r.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Writer w, BufferedWriter bw) {
		if(w != null) {
			try {
				bw.close();
				w.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
